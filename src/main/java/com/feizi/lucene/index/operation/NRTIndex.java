package com.feizi.lucene.index.operation;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.Query;

import com.feizi.lucene.index.manager.IndexManager;

/**
 * 索引管理操作类，增删改三种操作
 * @author ljj
 * @time 2015年11月15日 下午8:53:23
 * TODO
 */
public class NRTIndex {

	private TrackingIndexWriter indexWriter;
	private String indexName;
	
	/**
	 * 直接使用IndexManager中的IndexWriter，将索引的增删改操作都委托给TrackingIndexWriter来实现
	 * @param indexName
	 */
	public NRTIndex(String indexName){
		this.indexName = indexName;
		indexWriter = IndexManager.getIndexManager(indexName).getTrackingIndexWriter();
	}
	
	/**
	 * 增加Document到索引中
	 * @param doc
	 * @return
	 */
	public boolean addDocument(Document doc){
		try {
			indexWriter.addDocument(doc);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 按照Query条件从索引中删除Document
	 * @param query
	 * @return
	 */
	public boolean deleteDocument(Query query){
		try {
			indexWriter.deleteDocuments(query);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 清空索引
	 * @return
	 */
	public boolean deleteAll(){
		try {
			indexWriter.deleteAll();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 按照Term条件修改索引中的Document
	 * @param term
	 * @param doc
	 * @return
	 */
	public boolean updateDocument(Term term, Document doc){
		try {
			indexWriter.updateDocument(term, doc);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 合并索引
	 * @throws IOException
	 */
	public void commit() throws IOException{
		IndexManager.getIndexManager(indexName).getIndexWriter().commit();
	}
}
