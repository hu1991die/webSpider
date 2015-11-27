package com.feizi.lucene.index.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;

import com.feizi.lucene.index.manager.IndexManager;
import com.feizi.lucene.index.model.SearchResultBean;

/**
 * 索引的查询操作
 * @author ljj
 * @time 2015年11月15日 下午8:29:21
 * TODO
 */
public class NRTSearch {

	private IndexManager indexManager;
	
	/**
	 * 实例化NRTSearch索引查询对象
	 * @param indexName 索引名称
	 */
	public NRTSearch(String indexName){
		indexManager = IndexManager.getIndexManager(indexName);
	}
	
	/**
	 * 索引中的记录数量
	 * @return
	 */
	public int getIndexNum(){
		return indexManager.getIndexNum();
	}
	
	/**
	 * 索引查询
	 * @param query 查询字符串
	 * @param start 起始位置
	 * @param end 结束位置
	 * @return 查询结果
	 */
	public SearchResultBean search(Query query, int start, int end){
		start = start < 0 ? 0 : start;
		end = end < 0 ? 0 : end;
		if(indexManager == null || query == null || start >= end){
			return null;
		}
		
		SearchResultBean result = new SearchResultBean();
		List<Document> datas = new ArrayList<Document>();
		result.setDatas(datas);
		
		IndexSearcher searcher = indexManager.getIndexSearcher();
		try {
			TopDocs docs = searcher.search(query, end);
			result.setCount(docs.totalHits);
			end = end > docs.totalHits ? docs.totalHits : end;
			for (int i = start; i < end; i++) {
				datas.add(searcher.doc(docs.scoreDocs[i].doc));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			indexManager.release(searcher);
		}
		return result;
	}
	
	/**
	 * 查询索引
	 * @param query 查询字符串
	 * @param start 起始位置
	 * @param end 结束位置
	 * @param sort 排序条件
	 * @return 查询结果
	 */
	public SearchResultBean search(Query query, int start, int end, Sort sort){
		start = start < 0 ? 0 : start;
		end = end < 0 ? 0 : end;
		if(null == indexManager || query == null || start >= end){
			return null;
		}
		
		SearchResultBean result = new SearchResultBean();
		List<Document> datas = new ArrayList<Document>();
		result.setDatas(datas);
		
		IndexSearcher searcher = indexManager.getIndexSearcher();
		try {
			TopDocs docs = searcher.search(query, end, sort);
			result.setCount(docs.totalHits);
			end = end > docs.totalHits ? docs.totalHits : end;
			for (int i = start; i < end; i++) {
				datas.add(searcher.doc(docs.scoreDocs[i].doc));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			indexManager.release(searcher);
		}
		return result;
	}
	
	/**
	 * 按照序号检索
	 * @param start 起始位置
	 * @param count 记录条数
	 * @return
	 */
	public SearchResultBean search(int start, int count){
		start = start < 0 ? 0 : start;
		count = count < 0 ? 0 : count;
		if(null == indexManager){
			return null;
		}
		
		SearchResultBean result = new SearchResultBean();
		List<Document> datas = new ArrayList<Document>();
		result.setDatas(datas);
		IndexSearcher searcher = indexManager.getIndexSearcher();
		result.setCount(count);
		
		try {
			for (int i = 0; i < count; i++) {
				datas.add(searcher.doc((start + i) % getIndexNum()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			indexManager.release(searcher);
		}
		return result;
	}
}
