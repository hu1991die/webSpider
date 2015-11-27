package com.feizi.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * 分词器学习
 * @author ljj
 * @time 2015年10月29日 下午10:28:30
 * TODO
 */
public class AnalyzerStudy {
	
	public static void main(String[] args) {
		String content = "这是一个分词器测试程序，希望大家继续关注我的个人系列博客：基于Lucene的案例开发，这里加一点带空格的标签 LUCENE java 分词器";
		new AnalyzerStudy().createAnalyzer(content);
	}

	/**
	 * 构建分词器
	 */
	public void createAnalyzer(String content){
		//使用分词器工厂创建一个标准分词器
		Analyzer analyzer = new AnalyzerFactory().createAnalyzer(AnalyzerType.KEYWORD_ANALYZER, Version.LUCENE_43);
		
		//使用分词器处理测试字符串
		StringReader reader = new StringReader(content);
		try {
			TokenStream tokenStream = analyzer.tokenStream("", reader);
			tokenStream.reset();
			CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
			
			int line = 0;
			//输出分词器和处理结果
			System.out.println(analyzer.getClass());
			while(tokenStream.incrementToken()){
				System.out.println(term.toString() + "||");
				line += term.toString().length();
				
				//如果一行输出地字数大于30，就换行输出
				if(line > 30){
					System.out.println();
					line = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
