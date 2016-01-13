/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.feizi.crawl.novel.duokan.crawl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.feizi.crawl.base.CrawlBase;
import com.feizi.crawl.novel.duokan.model.TypeModelListModel;
import com.feizi.utils.ParseUtils;
import com.feizi.utils.RegexUtils;

/**  
 * 小说分类模块列表页信息抓取（全部详细分类列表）
 * @Author: feizi
 * @Date: 2015年12月1日 下午7:27:38 
 * @Version:V6.0
 */
public class NovelModelListPage extends CrawlBase {

	private static String NOVEL_ROOT_DOMAIN = "<a[^>]*href=\"([^\"]+)\"[^>]*class=\"crt\"[^>]*>";
	private static String NOVEL_MODEL_CONTENT_HTML = "<ul[^>]*class=\"level2\"[^>]*>(.*?)</ul>";
	private static String NOVEL_MODEL_LIST_HTML = "<div[^>]*class=\"wrap\\s\"[^>]*>(.*?)</div>";
	private static String NOVEL_MODEL_URL = "<a[^>]*href=\"([^\"]+)\"[^>]*hidefocus=\"hidefocus\"[^>]*>";
	private static String NOVEL_MODEL_NAME = "<span[^>]*>(.*?)</span>";
	private static String NOVEL_MODEL_COUNT = "<em[^>]*class=\"num\">(.*?)</em>";
	
	private static HashMap<String, String> params;
	
	/**
	 * 添加请求头信息，对请求进行伪装
	 */
	static{
		params = new HashMap<String, String>();
		params.put("Referer", "http://www.duokan.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
		params.put("Cookie", "bdshare_firstime=1448961816167; reader_preference=horizontal; store_noob=check; channel=49PXVQ; device_id=D900RJNWRDPQGWWF; app_id=web; Hm_lvt_1c932f22da573f2870e8ab565f4ff9cb=1448956725,1448956729,1448956921,1448963496; Hm_lpvt_1c932f22da573f2870e8ab565f4ff9cb=1448969212");
	}
	
	/**
	 * 构造器
	 * @Title:
	 * @Description:
	 */
	public NovelModelListPage(String urlStr) {
		readPageByGet(urlStr, "utf-8", params);
	}
	
	/**
	 * 获取小说分类模块的整体框架html代码
	  * @Discription:扩展说明
	  * @param urlStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午4:34:47
	 */
	private String getNovelModelContentHtml(){
		return RegexUtils.getFirstString(getPageSourceCode(), NOVEL_MODEL_CONTENT_HTML, 1);
	}
	
	/**
	 * 获取分类列表的li列表
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午6:47:32
	 */
	private List<String> getNovelModelLiListHtml(){
		return RegexUtils.getList(getNovelModelContentHtml(), NOVEL_MODEL_LIST_HTML, 1);
	}
	
	/**
	 * 获取小说分类模块URL
	  * @Discription:扩展说明
	  * @param dealStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:06:06
	 */
	private String getNovelModelUrl(String dealStr){
		return RegexUtils.getFirstString(dealStr, NOVEL_MODEL_URL, 1);
	}
	
	/**
	 * 获取小说分类模块名称
	  * @Discription:扩展说明
	  * @param dealStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:06:18
	 */
	private String getNovelModelName(String dealStr){
		return RegexUtils.getFirstString(dealStr, NOVEL_MODEL_NAME, 1);
	}
	
	/**
	 * 获取小说分类模块统计数量
	  * @Discription:扩展说明
	  * @param dealStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:06:28
	 */
	private String getNovelModelCount(String dealStr){
		return RegexUtils.getFirstString(dealStr, NOVEL_MODEL_COUNT, 1);
	}
	
	/**
	 * 获取顶级根域名URL
	  * @Discription:扩展说明
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:02:15
	 */
	public String getRootDomain(){
		return RegexUtils.getFirstString(getPageSourceCode(), NOVEL_ROOT_DOMAIN, 1);
	}
	
	/**
	 * 采集小说分类列表信息
	  * @Discription:扩展说明
	  * @return
	  * @return List<NovelTypeListModel>
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:11:35
	 */
	public List<TypeModelListModel> getNovelModelList(){
		//抓取小说分类模块列表html代码列表
		List<String> liList = getNovelModelLiListHtml();
		
		List<TypeModelListModel> typeModelList = new ArrayList<TypeModelListModel>();
		TypeModelListModel typeModelListModel = null;
		if(null != liList && liList.size() > 0){
			String rootDomain = (getRootDomain() == null) ? "" : getRootDomain().substring(0, getRootDomain().lastIndexOf("/"));
			//循环小说分类列表html代码列表
			for (String liHtml : liList) {
				//获取分类URL
				String modelUrl = getNovelModelUrl(liHtml);
				//获取分类名称
				String modelName = getNovelModelName(liHtml);
				//获取分类统计数量
				int typeCount = ParseUtils.parseStringToInt(getNovelModelCount(liHtml), 0);
				
				typeModelListModel = new TypeModelListModel();
				typeModelListModel.setRootDomain(rootDomain);
				typeModelListModel.setModelUrl(modelUrl);
				typeModelListModel.setModelName(modelName);
				typeModelListModel.setModelCount(typeCount);
				typeModelList.add(typeModelListModel);
			}
		}
		return typeModelList;
	}
	
	public static void main(String[] args) {
		NovelModelListPage novelModelListPage = new NovelModelListPage("http://www.duokan.com/list/14-1");
		List<TypeModelListModel> typeModelList = novelModelListPage.getNovelModelList();
		if(null != typeModelList && typeModelList.size() > 0){
			for (TypeModelListModel model : typeModelList) {
				System.out.println("小说的分类模块顶级根域地址：" + model.getRootDomain());
				System.out.println("小说的分类模块URL地址：" + model.getModelUrl());
				System.out.println("小说的分类模块名称：" + model.getModelName());
				System.out.println("小说的分类统计模块数量：" + model.getModelCount());
			}
		}
	}
}
