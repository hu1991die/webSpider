package com.feizi.lucene.index.model;

import java.util.List;

import org.apache.lucene.document.Document;

/**
 * 索引搜索结果数据结构
 * 
 * @author ljj
 * @time 2015年11月15日 下午8:26:50 TODO
 */
public class SearchResultBean {

	private int count;
	private List<Document> datas;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Document> getDatas() {
		return datas;
	}

	public void setDatas(List<Document> datas) {
		this.datas = datas;
	}
}
