package com.feizi.lucene.analyzer;

/**
 * 分词器类型枚举类
 * @author ljj
 * @time 2015年10月29日 下午10:49:58
 * TODO
 */
public enum AnalyzerType {

	//标准分词器
	STANDARD_ANALYZER,
	
	//中文分词器
	IK_ANALYZER,
	
	//空格分词器
	WHITE_SPACE_ANALYZER,
	
	//简单分词器
	SIMPLE_ANALYZER,
	
	//二分法分词器
	CJK_ANALYZER,
	
	//关键词分词器
	KEYWORD_ANALYZER,
	
	//被忽略的词分词器
	STOP_ANALYZER;
}
