package com.feizi.lucene.index.manager;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import com.feizi.lucene.index.model.ConfigBean;
import com.feizi.lucene.index.model.IndexConfig;

/**
 * 索引管理类
 * @author ljj
 * @time 2015年11月14日 下午6:48:27
 * TODO
 */
public class IndexManager {

	private IndexWriter indexWriter;
	//更新索引文件的IndexWriter
	private TrackingIndexWriter trackingIndexWriter;
	//索引文件采用的分词器
	private Analyzer analyzer;
	//索引管理对象
	private NRTManager nrtManager;
	//索引重读线程
	private NRTManagerReopenThread nrtManagerReopenThread;
	//索引写入磁盘线程
	private IndexCommitThread indexCommitThread;
	
	//索引地址
	private String indexPath;
	//索引重读的最大、最小时间间隔
	private double indexReopenMaxStaleSec;
	private double indexReopenMinStaleSec;
	//索引commit时间
	private int indexCommitSeconds;
	//索引名称
	private String indexName;
	//commit时是否输出相关信息
	private boolean bprint = true;
	
	private static class LazyLoadIndexManager{
		private static final HashMap<String, IndexManager> indexManager = new HashMap<String, IndexManager>();
		static{
			for (ConfigBean configBean : IndexConfig.getConfigBean()) {
				indexManager.put(configBean.getIndexName(), new IndexManager(configBean));
			}
		}
	}
	
	private IndexManager(ConfigBean configBean){
		//设置相关属性
		analyzer = configBean.getAnalyzer();
		indexPath = configBean.getIndexPath();
		indexName = configBean.getIndexName();
		indexReopenMaxStaleSec = configBean.getIndexReopenMaxStaleSec();
		indexReopenMinStaleSec = configBean.getIndexReopenMinStaleSec();
		indexCommitSeconds = configBean.getIndexCommitSeconds();
		bprint = configBean.isBprint();
		
		String indexFile = indexPath + indexName + "/";
		//创建或打开索引
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		Directory directory = null;
		try {
			directory = NIOFSDirectory.open(new File(indexFile));
			if(IndexWriter.isLocked(directory)){
				IndexWriter.unlock(directory);
			}
			this.indexWriter = new IndexWriter(directory, indexWriterConfig);
			this.trackingIndexWriter = new TrackingIndexWriter(this.indexWriter);
			this.nrtManager = new NRTManager(trackingIndexWriter, new SearcherFactory());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//开启守护进程
		this.setThread();
	}
	
	/**
	 * 创建索引管理线程
	 */
	private void setThread(){
		this.nrtManagerReopenThread = new NRTManagerReopenThread(nrtManager, indexReopenMaxStaleSec, indexReopenMinStaleSec);
		this.nrtManagerReopenThread.setName("NRTManager Reopen Thread");
		this.nrtManagerReopenThread.setPriority(Math.min(Thread.currentThread().getPriority() + 2, Thread.MAX_PRIORITY));
		this.nrtManagerReopenThread.setDaemon(true);
		this.nrtManagerReopenThread.start();
		
		this.indexCommitThread = new IndexCommitThread(indexName + "Index Commit Thread");
		this.indexCommitThread.start();
	}
	
	/**
	 * 重启索引commit线程
	 * @return
	 */
	public String setCommitThread(){
		try {
			if(this.indexCommitThread.isAlive()){
				return "is alive";
			}
			this.indexCommitThread = new IndexCommitThread(indexName + "Index Commit Thread");
			this.indexCommitThread.setDaemon(true);
			this.indexCommitThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			return "failed......";
		}
		return "reload......";
	}
	
	/**
	 * 索引commit线程
	 * @author ljj
	 * @time 2015年11月14日 下午7:26:25
	 * TODO
	 */
	private class IndexCommitThread extends Thread{
		private boolean flag;
		public IndexCommitThread(String name){
			super(name);
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			flag = true;
			while(flag){
				try {
					indexWriter.commit();
					if(bprint){
						System.out.println(new Date().toLocaleString() + "\t" + indexName + "\tcommit");
					}
					TimeUnit.SECONDS.sleep(indexCommitSeconds);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取索引管理类
	 * @param indexName
	 * @return
	 */
	public static IndexManager getIndexManager(String indexName){
		return LazyLoadIndexManager.indexManager.get(indexName);
	}
	
	/**
	 * 释放IndexSearcher资源
	 * @param searcher
	 */
	public void release(IndexSearcher searcher){
		try {
			nrtManager.release(searcher);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回IndexSearcher对象，使用完之后，调用release方法进行释放
	 * @return
	 */
	public IndexSearcher getIndexSearcher(){
		try {
			return this.nrtManager.acquire();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public NRTManager getNRTManager(){
		return this.nrtManager;
	}
	
	public IndexWriter getIndexWriter(){
		return this.indexWriter;
	}
	
	public TrackingIndexWriter getTrackingIndexWriter(){
		return this.trackingIndexWriter;
	}
	
	public Analyzer getAnalyzer(){
		return this.analyzer;
	}
	
	/**
	 * 获取索引中的记录条数
	 * @return
	 */
	public int getIndexNum(){
		return this.indexWriter.numDocs();
	}
}
