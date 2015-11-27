/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.feizi.lucene.query;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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

import com.feizi.lucene.index.manager.IndexManager;

/**  
 * 封装查询query
 * @Author: feizi
 * @Date: 2015年11月16日 下午3:49:15 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年11月16日 下午3:49:15 
 * @Version:V6.0
 */
public class PackQuery {

	//分词器
	private Analyzer analyzer;
	
	/**
	 * 使用索引中的分词器
	 * @Title:
	 * @Description:
	 * @param indexName
	 */
	public PackQuery(String indexName){
		analyzer = IndexManager.getIndexManager(indexName).getAnalyzer();
	}
	
	/**
	 * 使用自定义的分词器
	 * @Title:
	 * @Description:
	 * @param analyzer
	 */
	public PackQuery(Analyzer analyzer){
		this.analyzer = analyzer;
	}
	
	/**
	 * 查询字符串匹配多个查询域
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @return
	  * @return Query
	 * @throws ParseException 
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午3:55:58
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午3:55:58
	 */
	public Query getMultiFieldQuery(String key, String[] fields) throws ParseException{
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_43, fields, analyzer);
		Query query = parser.parse(key);
		return query;
	}
	
	/**
	 * 查询字符串匹配单个查询域
	  * @Discription:扩展说明
	  * @param key
	  * @param field
	  * @return
	  * @return Query
	 * @throws ParseException 
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午3:59:52
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午3:59:52
	 */
	public Query getOneFieldQuery(String key, String field) throws ParseException{
		if(null == key || key.length() < 1){
			return null;
		}
		QueryParser parser = new QueryParser(Version.LUCENE_43, field, analyzer);
		Query query = parser.parse(key);
		return query;
	}
	
	/**
	 * 查询字符串，多个查询域以及查询域在查询语句中的关系
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @param occur
	  * @return
	  * @throws IOException
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:15:19
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:15:19
	 */
	public Query getBooleanQuery(String key, String[] fields, Occur[] occur) throws IOException{
		if(null == fields || null == occur || (fields.length != occur.length)){
			System.out.println("fields.length isn't equals occur.length, please check the params...");
			return null;
		}
		
		BooleanQuery query = new BooleanQuery();
		TokenStream tokenStream = analyzer.tokenStream("", new StringReader(key));
		List<String> analyzerKeys = new ArrayList<String>();
		if(null != tokenStream){
			while(tokenStream.incrementToken()){
				CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
				analyzerKeys.add(term.toString());
			}
		}
		
		for (int i = 0; i < fields.length; i++) {
			BooleanQuery queryField = new BooleanQuery();
			for (String analyzerKey : analyzerKeys) {
				TermQuery termQuery = new TermQuery(new Term(fields[i], analyzerKey));
				queryField.add(termQuery, Occur.SHOULD);
			}
			query.add(queryField, occur[i]);
		}
		return query;
	}
	
	/**
	 * 组合多个查询，之间的关系由occur确定
	  * @Discription:扩展说明
	  * @param querys
	  * @param occurs
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:19:19
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:19:19
	 */
	public Query getBooleanQuery(ArrayList<Query> querys, ArrayList<Occur> occurs){
		if(querys.size() != occurs.size()){
			 System.out.println("querys.size() isn't equals occurs.size(), please check params!");  
	         return null;  
		}
		
		BooleanQuery query = new BooleanQuery();
		for (int i = 0; i < querys.size(); i++) {
			query.add(querys.get(i), occurs.get(i));
		}
		return query;
	}
	
	/**
	 * 根据StringField属性进行检索
	  * @Discription:扩展说明
	  * @param fieldName
	  * @param value
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:20:59
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:20:59
	 */
	public Query getStringFieldQuery(String fieldName, String value){
		Query query = new TermQuery(new Term(fieldName, value));
		return query;
	}
	
	/**
	 * 多个StringField属性的检索
	  * @Discription:扩展说明
	  * @param values
	  * @param fields
	  * @param occur
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:25:40
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:25:40
	 */
	public Query getStringFieldQuery(String[] values, String[] fields, Occur occur){
		if(null == fields || null == values || (fields.length != values.length)){
			return null;
		}
		ArrayList<Query> querys = new ArrayList<Query>();
		ArrayList<Occur> occurs = new ArrayList<Occur>();
		for (int i = 0; i < fields.length; i++) {
			querys.add(getStringFieldQuery(fields[i], values[i]));
			occurs.add(occur);
		}
		return getBooleanQuery(querys, occurs);
	}
	
	/**
	 * 查询字符串和单个查询域 QueryParser是否使用4.3
	  * @Discription:扩展说明
	  * @param key
	  * @param field
	  * @param lucene43
	  * @return
	  * @throws ParseException
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:28:43
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:28:43
	 */
	public Query getOneFieldQuery(String key, String field, boolean lucene43) throws ParseException{
		if(null == key || key.length() < 1){
			return null;
		}
		
		if(lucene43){
			return getOneFieldQuery(key, field);
		}
		
		@SuppressWarnings("deprecation")
		QueryParser parser = new QueryParser(Version.LUCENE_30, field, analyzer);
		Query query = parser.parse(key);
		return query;
	}
	
	/**
	 * key开头的查询字符串，和单个域匹配
	  * @Discription:扩展说明
	  * @param key
	  * @param field
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:31:03
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:31:03
	 */
	public Query getStartQuery(String key, String field){
		if(null == key || key.length() < 1){
			return null;
		}
		
		Query query = new PrefixQuery(new Term(field, key));
		return query;
	}
	
	/**
	 * key开头的查询字符串，和多个域进行匹配，每个域之间的关系由occur确定
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @param occur
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:35:23
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:35:23
	 */
	public Query getStartQuery(String key, String[] fields, Occur occur){
		if(null == key || key.length() < 1){
			return null;
		}
		ArrayList<Query> querys = new ArrayList<Query>();
		ArrayList<Occur> occurs = new ArrayList<Occur>();
		for (String field : fields) {
			querys.add(getStartQuery(key, field));
			occurs.add(occur);
		}
		return getBooleanQuery(querys, occurs);
	}
	
	/**
	 * key开头的查询字符串，和多个域匹配，每个域之间的关系Occur.SHOULD
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:37:11
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:37:11
	 */
	public Query getStartQuery(String key, String[] fields){
		return getStartQuery(key, fields, Occur.SHOULD);
	}
	
	/**
	 * 自定义每个词元之间的最大距离
	  * @Discription:扩展说明
	  * @param key
	  * @param field
	  * @param slop
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午4:42:29
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午4:42:29
	 */
	public Query getPhraseQuery(String key, String field, int slop){
		if(null == key || key.length() < 1){
			return null;
		}
		StringReader reader = new StringReader(key);
		PhraseQuery query = new PhraseQuery();
		query.setSlop(slop);
		
		try {
			TokenStream tokenStream = this.analyzer.tokenStream(field, reader);
			tokenStream.reset();
			CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
			
			while(tokenStream.incrementToken()){
				query.add(new Term(field, term.toString()));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return query;
	}
	
	/**
	 * 自定义每个词元之间的最大距离，查询多个域，每个域之间的关系由occur确定
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @param slop
	  * @param occur
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午6:45:02
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午6:45:02
	 */
	public Query getPhraseQuery(String key, String[] fields, int slop, Occur occur){
		if(null == key || key.length() < 1){
			return null;
		}
		ArrayList<Query> querys = new ArrayList<Query>();
		ArrayList<Occur> occurs = new ArrayList<Occur>();
		for (String field : fields) {
			querys.add(getPhraseQuery(key, field, slop));
			occurs.add(occur);
		}
		return getBooleanQuery(querys, occurs);
	}
	
	/**
	 * 自定义每个词元之间的最大距离，查询多个域，每个域之间的关系是Occur.SHOULD
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @param slop
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午6:47:14
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午6:47:14
	 */
	public Query getPhraseQuery(String key, String[] fields, int slop){
		return getPhraseQuery(key, fields, slop, Occur.SHOULD);
	}
	
	/**
	 * 通配符检索 eg:getWildcardQuery("a*thor","field")
	  * @Discription:扩展说明
	  * @param key
	  * @param field
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午6:49:52
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午6:49:52
	 */
	public Query getWildcardQuery(String key, String field){
		if(null == key || key.length() < 1){
			return null;
		}
		return new WildcardQuery(new Term(field, key));
	}
	
	/**
	 * 通配符检索，域之间的关系为occur
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @param occur
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:27:49
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:27:49
	 */
	public Query getWildcardQuery(String key, String[] fields, Occur occur){
		if(null == key || key.length() < 1){
			return null;
		}
		ArrayList<Query> querys = new ArrayList<Query>();
		ArrayList<Occur> occurs = new ArrayList<Occur>();
		for (String field : fields) {
			querys.add(getWildcardQuery(key, field));
			occurs.add(occur);
		}
		return getBooleanQuery(querys, occurs);
	}
	
	/**
	 * 通配符检索，域之间的关系为Occur.SHOULD
	  * @Discription:扩展说明
	  * @param key
	  * @param fields
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:30:01
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:30:01
	 */
	public Query getWildcardQuery(String key, String[] fields){
		return getWildcardQuery(key, fields, Occur.SHOULD);
	}
	
	/**
	 * String范围搜索
	  * @Discription:扩展说明
	  * @param keyStart
	  * @param keyEnd
	  * @param field
	  * @param includeStart
	  * @param includeEnd
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:35:29
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:35:29
	 */
	public Query getRangeQuery(String keyStart, String keyEnd, String field, 
			boolean includeStart, boolean includeEnd){
		return TermRangeQuery.newStringRange(field, keyStart, keyEnd, includeStart, includeEnd);
	}
	
	/**
	 * Integer范围搜索
	  * @Discription:扩展说明
	  * @param min
	  * @param max
	  * @param field
	  * @param includeMin
	  * @param includeMax
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:37:27
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:37:27
	 */
	public Query getRangeQuery(int min, int max, String field, boolean includeMin, boolean includeMax){
		return NumericRangeQuery.newIntRange(field, min, max, includeMin, includeMax);
	}
	
	/**
	 * Float范围搜索
	  * @Discription:扩展说明
	  * @param min
	  * @param max
	  * @param field
	  * @param includeMin
	  * @param includeMax
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:39:54
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:39:54
	 */
	public Query getRangeQuery(float min, float max, String field, boolean includeMin, boolean includeMax){
		return NumericRangeQuery.newFloatRange(field, min, max, includeMin, includeMax);
	}
	
	/**
	 * Double范围搜索
	  * @Discription:扩展说明
	  * @param min
	  * @param max
	  * @param field
	  * @param includeMin
	  * @param includeMax
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:41:24
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:41:24
	 */
	public Query getRangeQuery(double min, double max, String field, boolean includeMin, boolean includeMax){
		return NumericRangeQuery.newDoubleRange(field, min, max, includeMin, includeMax);
	}
	
	/**
	 * Long范围搜索
	  * @Discription:扩展说明
	  * @param min
	  * @param max
	  * @param field
	  * @param includeMin
	  * @param includeMax
	  * @return
	  * @return Query
	  * @Author: feizi
	  * @Date: 2015年11月16日 下午7:46:47
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月16日 下午7:46:47
	 */
	public Query getRangeQuery(long min, long max, String field, boolean includeMin, boolean includeMax){
		return NumericRangeQuery.newLongRange(field, min, max, includeMin, includeMax);
	}
	
	public static void main(String[] args) {
		
	}
}
