package com.feizi.lucene.index;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 搜索索引
 * @author ljj
 * @time 2015年10月29日 下午9:42:07
 * TODO
 */
public class IndexSearch {

	/**
	 * 测试用例
	 * @param args
	 */
	public static void main(String[] args) {
		new IndexSearch().searchIndex();
	}
	
	/**
	 * 搜索索引
	 */
	public void searchIndex(){
		Directory directory = null;
		try {
			//获取索引的硬盘存储路径
			directory = FSDirectory.open(new File("/Users/ljj/Downloads/LuceneIndex/testIndex"));
			
			//读取索引
			DirectoryReader dReader = DirectoryReader.open(directory);
			
			//创建索引检索对象
			IndexSearcher searcher = new IndexSearcher(dReader);
			
			//指定分词技术，这里采用的语言出来模块要和创建索引的时候一致，否则检索的结果很不理想
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
			
			//创建查询query,搜索词为“空间向量”
			QueryParser parser = new QueryParser(Version.LUCENE_43, "content", analyzer);
			Query query = parser.parse("内");
			
			//检索索引，获取复合条件的前10条记录
			TopDocs topDocs = searcher.search(query, 10);
			if(null != topDocs && topDocs.scoreDocs.length > 0){
				System.out.println("总共查找到：" + topDocs.totalHits + "条符合条件的记录");
				//循环输出记录内容
				for (int i = 0; i < topDocs.scoreDocs.length; i++) {
					Document doc = searcher.doc(topDocs.scoreDocs[i].doc);
					System.out.println("=====第" + (i + 1) + "条内容为=====\n【name】：" + doc.get("name") + "\n【content】：" + doc.get("content")); 
				}
			}
			
			//关闭资源
			dReader.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
