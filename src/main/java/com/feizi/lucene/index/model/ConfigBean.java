/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.feizi.lucene.index.model;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * 索引基础属性配置
 * 
 * @Author: feizi
 * @Date: 2015年11月12日 下午1:47:54
 * @ModifyUser: feizi
 * @ModifyDate: 2015年11月12日 下午1:47:54
 * @Version:V6.0
 */
public class ConfigBean {

	// 分词器
	private Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
	// 索引地址
	private String indexPath = "/Users/ljj/Downloads/LuceneIndex/";
	private double indexReopenMaxStaleSec = 10;
	private double indexReopenMinStaleSec = 0.025;

	// 索引commit的时间
	private int indexCommitSeconds = 60;
	// 索引名称
	private String indexName = "index";
	// commit时是否输出相关信息
	private boolean bprint = true;

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		if(!(indexPath.endsWith("\\") || indexPath.endsWith("/"))){
			indexPath += "/";
		}
		this.indexPath = indexPath;
	}

	public double getIndexReopenMaxStaleSec() {
		return indexReopenMaxStaleSec;
	}

	public void setIndexReopenMaxStaleSec(double indexReopenMaxStaleSec) {
		this.indexReopenMaxStaleSec = indexReopenMaxStaleSec;
	}

	public double getIndexReopenMinStaleSec() {
		return indexReopenMinStaleSec;
	}

	public void setIndexReopenMinStaleSec(double indexReopenMinStaleSec) {
		this.indexReopenMinStaleSec = indexReopenMinStaleSec;
	}

	public int getIndexCommitSeconds() {
		return indexCommitSeconds;
	}

	public void setIndexCommitSeconds(int indexCommitSeconds) {
		this.indexCommitSeconds = indexCommitSeconds;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public boolean isBprint() {
		return bprint;
	}

	public void setBprint(boolean bprint) {
		this.bprint = bprint;
	}
}
