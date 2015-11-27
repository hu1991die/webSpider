package com.feizi.lucene.index;


import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 索引创建
 * @author ljj
 * @time 2015年10月27日 下午10:56:55
 * TODO
 */
public class IndexCreate {
	
	public static void main(String[] args) {
		new IndexCreate().createIndex();
	}

	/**
	 * 创建索引
	 */
	public void createIndex(){
		//指定索引分词技术，这里使用的是标准分词
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
		
		//IndexWriter配置信息
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		
		//索引的打开方式，没有索引文件就新建，有就直接打开
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		Directory directory = null;
		IndexWriter indexWriter = null;
		
		try {
			//指定索引硬盘存储路径
			directory = FSDirectory.open(new File("/Users/ljj/Downloads/LuceneIndex/testIndex"));
			
			//如果索引处于锁定状态，则解锁
			if(IndexWriter.isLocked(directory)){
				IndexWriter.unlock(directory);
			}
			
			//指定操作对象
			indexWriter = new IndexWriter(directory, indexWriterConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//创建文档一
		Document docOne = new Document();
		//对name域赋值“测试标题”，存储域值信息
		docOne.add(new TextField("name", "测试标题", Store.YES));
		//对content域赋值“测试内容”，存储域值信息
		docOne.add(new TextField("content", "测试内容", Store.YES));
		
		try {
			//将文档写进到索引当中
			indexWriter.addDocument(docOne);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//创建文档二
		Document docTwo = new Document();
		docTwo.add(new TextField("name", "基于lucene的案例开发：索引数学模型", Store.YES));
		docTwo.add(new TextField("content", "lucene将一篇文档分成若干个域，每个域又分成若干个词元，通过词元在文档中的重要程度，将文档转化为N维的空间向量，通过计算两个向量之间的夹角余弦值来计算两个文档的相似程度", Store.YES));
		
		try {
			//将文档写进到索引当中去
			indexWriter.addDocument(docTwo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//将indexWrite操作提交，如果不提交，之前的操作将不会保存到硬盘中
		try {
			//这一步很消耗系统资源，所以commit操作需要有一定的策略
			indexWriter.commit();
			//关闭资源
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改索引
	 * @param indexWriter
	 * @param term
	 * @param document
	 * @return
	 */
	public boolean updateIndex(IndexWriter indexWriter,Term term, Document document){
		try {
			indexWriter.updateDocument(term, document);
			
			//增、删、改操作都需要执行commit操作之后才有效
			indexWriter.commit();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除索引
	 * @param indexWriter
	 * @param query
	 * @return
	 */
	public boolean deleteIndex(IndexWriter indexWriter, Query query){
		try {
			indexWriter.deleteDocuments(query);
			indexWriter.commit();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 清空索引
	 * @param indexWriter
	 * @return
	 */
	public boolean deleteAll(IndexWriter indexWriter){
		try {
			indexWriter.deleteAll();
			indexWriter.commit();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
