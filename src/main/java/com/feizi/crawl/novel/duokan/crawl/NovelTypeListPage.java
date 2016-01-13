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
import com.feizi.crawl.novel.duokan.model.NovelTypeListModel;
import com.feizi.utils.ParseUtils;
import com.feizi.utils.RegexUtils;

/**  
 * 多看阅读小说分类列表页数据信息采集类（抓取全部图书分类列表左侧导航栏列表）
 * @Author: feizi
 * @Date: 2015年12月1日 下午3:59:31 
 * @Version:V6.0
 */
public class NovelTypeListPage extends CrawlBase {

	private static String NOVEL_ROOT_DOMAIN = "<a[^>]*href=\"([^\"]+)\"[^>]*class=\"crt\"[^>]*>";
	private static String NOVEL_TYPE_CONTENT_HTML = "<div[^>]*class=\"m-directory\">(.*?)</div>";
	private static String NOVEL_TYPE_LI_LIST_HTML = "<li[^>]*class=\"itm.*?\">(.*?)</li>";
	private static String NOVEL_TYPE_URL = "<a[^>]*href=\"([^\"]+)\"[^>]*hidefocus=\"hidefocus\"[^>]*>";
	private static String NOVEL_TYPE_NAME = "<span[^>]*class=\"label\"[^>]*>(.*?)</span>";
	private static String NOVEL_TYPE_COUNT = "<span[^>]*class=\"num\"[^>]*>(.*?)</span>";
	
	private static HashMap<String, String> params;
	
	/**
	 * 添加相关头信息，对请求进行伪装
	 */
	static{
		params = new HashMap<String, String>();
		params.put("Referer", "http://www.duokan.com/");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
		params.put("Cookie", "channel=49PXVQ; device_id=D900RJNWRDPQGWWF; app_id=web; Hm_lvt_1c932f22da573f2870e8ab565f4ff9cb=1448956672,1448956680,1448956725,1448956729; Hm_lpvt_1c932f22da573f2870e8ab565f4ff9cb=1448956729");
	}
	
	/**
	 * 构造方法
	 * @Title:
	 * @Description:
	 */
	public NovelTypeListPage(String urlStr) {
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
	private String getNovelTypeContentHtml(){
		return RegexUtils.getFirstString(getPageSourceCode(), NOVEL_TYPE_CONTENT_HTML, 1);
	}
	
	/**
	 * 获取分类列表的li列表
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午6:47:32
	 */
	private List<String> getNovelTypeLiListHtml(){
		return RegexUtils.getList(getNovelTypeContentHtml(), NOVEL_TYPE_LI_LIST_HTML, 1);
	}
	
	/**
	 * 获取小说分类URL
	  * @Discription:扩展说明
	  * @param dealStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:06:06
	 */
	private String getNovelTypeUrl(String dealStr){
		return RegexUtils.getFirstString(dealStr, NOVEL_TYPE_URL, 1);
	}
	
	/**
	 * 获取小说分类名称
	  * @Discription:扩展说明
	  * @param dealStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:06:18
	 */
	private String getNovelTypeName(String dealStr){
		return RegexUtils.getFirstString(dealStr, NOVEL_TYPE_NAME, 1);
	}
	
	/**
	 * 获取小说分类统计数量
	  * @Discription:扩展说明
	  * @param dealStr
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 下午7:06:28
	 */
	private String getNovelTypeCount(String dealStr){
		return RegexUtils.getFirstString(dealStr, NOVEL_TYPE_COUNT, 1);
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
	public List<NovelTypeListModel> getNovelTypeList(){
		//抓取小说分类列表html代码列表
		List<String> liList = getNovelTypeLiListHtml();
		
		List<NovelTypeListModel> novelTypeList = new ArrayList<NovelTypeListModel>();
		NovelTypeListModel novelTypeListModel = null;
		if(null != liList && liList.size() > 0){
			String rootDomain = (getRootDomain() == null) ? "" : getRootDomain().substring(0, getRootDomain().lastIndexOf("/"));
			//循环小说分类列表html代码列表
			for (String liHtml : liList) {
				//获取分类URL
				String typeUrl = getNovelTypeUrl(liHtml);
				//获取分类名称
				String typename = getNovelTypeName(liHtml);
				//获取分类统计数量
				int typeCount = ParseUtils.parseStringToInt(getNovelTypeCount(liHtml), 0);
				
				novelTypeListModel = new NovelTypeListModel();
				novelTypeListModel.setRootDomain(rootDomain);
				novelTypeListModel.setTypeUrl(typeUrl);
				novelTypeListModel.setTypeName(typename);
				novelTypeListModel.setTypeCount(typeCount);
				novelTypeList.add(novelTypeListModel);
			}
		}
		return novelTypeList;
	}
	
	public static void main(String[] args) {
		NovelTypeListPage novelTypeListPage = new NovelTypeListPage("http://www.duokan.com/");
		List<NovelTypeListModel> novelTypeList = novelTypeListPage.getNovelTypeList();
		if(null != novelTypeList && novelTypeList.size() > 0){
			for (NovelTypeListModel typeModel : novelTypeList) {
				System.out.println("小说的分类顶级根域地址：" + typeModel.getRootDomain());
				System.out.println("小说的分类URL地址：" + typeModel.getTypeUrl());
				System.out.println("小说的分类名称：" + typeModel.getTypeName());
				System.out.println("小说的分类统计数量：" + typeModel.getTypeCount());
			}
		}
	}
}
