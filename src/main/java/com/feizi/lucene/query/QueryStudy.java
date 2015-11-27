package com.feizi.lucene.query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;

import com.feizi.lucene.analyzer.AnalyzerFactory;
import com.feizi.lucene.analyzer.AnalyzerType;

/**
 * query查询学习
 * @author ljj
 * @time 2015年10月30日 下午9:15:33
 * TODO
 */
public class QueryStudy {

	public static void main(String[] args) {
		try {
			String keyValue = "基于lucene的案例开发";
			new QueryStudy().queryContent(keyValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询
	 * @param keyValue
	 * @throws ParseException
	 */
	public void queryContent(String keyValue) throws ParseException{
		//设定Query查询过程中使用的分词器
		Analyzer analyzer = new AnalyzerFactory().createAnalyzer(AnalyzerType.STANDARD_ANALYZER, Version.LUCENE_43);
		
		//单个域搜索
		String field = "content";
		//多个域搜索
		String[] fields = {"name", "content"};
		Query query = null;
		
		//对单个域构建查询语句
		QueryParser parser = new QueryParser(Version.LUCENE_43, field, analyzer);
		query = parser.parse(keyValue);
		System.out.println(QueryParser.class);
		System.out.println(query.toString());
		System.out.println("QueryParser:======================");
		
		//对多个域构建查询语句
		MultiFieldQueryParser mparser = new MultiFieldQueryParser(Version.LUCENE_43, fields, analyzer);
		query = mparser.parse(keyValue);
		System.out.println(MultiFieldQueryParser.class);
		System.out.println(query.toString());
		System.out.println("MultiFieldQueryParser:======================");
		
		//词条搜索
		query = new TermQuery(new Term(field, keyValue));
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("TermQuery:=====================");
		
		//前缀搜索
		query = new PrefixQuery(new Term(field, keyValue));
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("PrefixQuery:================");
		
		//短语搜索
		PhraseQuery pQuery = new PhraseQuery();
		//设置短语间允许的最大间隔
		pQuery.setSlop(2);
		pQuery.add(new Term("content", "基于"));
		pQuery.add(new Term("content", "案例"));
		System.out.println(pQuery.getClass());
		System.out.println(pQuery.toString());
		System.out.println("PhraseQuery:=================");
		
		//通配符搜索
		query = new WildcardQuery(new Term(field, "基于?"));
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("WildcardQuery:================");
		
		//字符串范围搜索
		query = TermRangeQuery.newStringRange(field, "abc", "azz", true, false);
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("TermRangeQuery:================");
		
		//int范围搜索
		query = NumericRangeQuery.newIntRange("star", 0, 3, false, false);
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("NumericRangeQuery:================int");
		
		//float范围搜索
		query = NumericRangeQuery.newFloatRange("star", 0.0f, 3.0f, false, false);
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("NumericRangeQuery:================float");
		
		//double范围搜索
		query = NumericRangeQuery.newDoubleRange("star", 0.0, 3d, false, false);
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("NumericRangeQuery:================double");
		
		//long范围搜索
		query = NumericRangeQuery.newLongRange("star", 0L, 3L, false, false);
		System.out.println(query.getClass());
		System.out.println(query.toString());
		System.out.println("NumericRangeQuery:================long");
		
		//BooleanQuery搜索
		BooleanQuery bQuery = new BooleanQuery();
		bQuery.add(new TermQuery(new Term("content", "基于")), Occur.SHOULD);
		bQuery.add(new TermQuery(new Term("name", "lucene")),Occur.MUST);
		bQuery.add(new TermQuery(new Term("star", "3")), Occur.MUST_NOT);
		System.out.println(bQuery.getClass());
		System.out.println(bQuery.toString());
		System.out.println("BooleanQuery:===================");
	}
}
