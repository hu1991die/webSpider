package com.feizi.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 分词器工厂类，负责创建分词器
 * @author ljj
 * @time 2015年10月29日 下午10:47:25
 * TODO
 */
public class AnalyzerFactory {

	//定义一个分词器实例
	private Analyzer analyzer;
	
	public Analyzer createAnalyzer(AnalyzerType analyzerType, Version version) {
		switch (analyzerType) {
			case IK_ANALYZER:
				//第三方中文分词器
				analyzer = new IKAnalyzer(true);
				break;
			case WHITE_SPACE_ANALYZER:
				//空格分词器，对字符串不做任何处理
				analyzer = new WhitespaceAnalyzer(version);
				break;
			case SIMPLE_ANALYZER:
				//简单分词器，一段一段话进行分词
				analyzer = new SimpleAnalyzer(version);
				break;
			case CJK_ANALYZER:
				//二分法分词器，这个分词方式是正向退一分词（二分法分词），同一个字会和它的左边和右边组成一个词，每个字出现两次，除了首字和末字
				analyzer = new CJKAnalyzer(version);
				break;
			case KEYWORD_ANALYZER:
				//关键字分词器，把处理的字符串当做一个整体
				analyzer = new KeywordAnalyzer();
				break;
			case STOP_ANALYZER:
				//被忽略的词分词器
				analyzer = new StopAnalyzer(version);
				break;
			default:
				//标准分词器，如果用来处理中文，和ChineseAnalyzer有一样的效果
				analyzer = new StandardAnalyzer(version);
				break;
			}
		return analyzer;
	}
}
