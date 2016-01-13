/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.feizi.crawl.qqzone.crawl;

import java.util.HashMap;
import java.util.List;

import com.feizi.crawl.base.CrawlBase;
import com.feizi.utils.RegexUtils;

/**  
 * 扣扣空间列表数据采集
 * @Author: feizi
 * @Date: 2015年11月30日 上午11:06:04 
 * @Version:V6.0
 */
public class QQZoneListPage extends CrawlBase{

	private static String QQ_ZONE_TRENDS = "<ul[^>]*id=\"feed_friend_list\"[^>]*>(.*?)</ul>";
	private static String QQ_ZONE_URL = "<a[^>]*href=\"([^\"]+)\"[^>]*class=\".*?\"[^>]*>([^<]+)</a>";
	private static String QQ_ZONE_TERMINAL = "<a[^>]*href=\"[^\"]+\"[^>]*class=\" phone-style state\"[^>]*>(.*?)</a>";
	private static String QQ_ZONE_VISIT = "<a[^>]*href=\"[^\"]+\"[^>]*data-role=\"Visitor\"[^>]*>.*?\\((\\d*)\\)</a>";
	private static String QQ_ZONE_DETAIL = "<div[^>]*class=\"f-info\"[^>]*>(.*?)</div>";
	private static String QQ_ZONE_PUBLISH_TIME = "<span[^>]*class=\" ui-mr8 state\"[^>]*>\\s(\\d{2}:\\d{2})</span>";
	private static HashMap<String, String> params;
	
	/**
	 * 对相关请求头信息进行伪装
	 */
	static{
		params = new HashMap<String, String>();
		params.put("Referer", "http://user.qzone.qq.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
		params.put("Cookie", "hasShowWeiyun347431996=1; RK=mbuK4CcNvG; pgv_pvi=1027422208; pgv_pvid=9788275770; __Q_w_s__QZN_TodoMsgCnt=1; pt2gguin=o0347431996; uin=o0347431996; skey=@w3b8iedO2; ptisp=ctc; qzone_check=347431996_1448931861; ptcz=10544e47d744c8c6baed9413e39c9ef39469da61e29b7f680371526eded858e6; p_skey=sF3R6mpZAPcX20t3hvMoEYsMvlWbZ7cW6zBh4wN6lt8_; p_uin=o0347431996; pt4_token=F6PdGLM6S3LaP5pR87V7Ug__; pgv_info=ssid=s6993246864; cpu_performance_v8=3; QZ_FE_WEBP_SUPPORT=1; Loading=Yes; qzspeedup=sdch; qz_screen=1366x768");
	}
	
	/**
	 * 构造器
	 * @Title:
	 * @Description:
	 * @param urlStr
	 * @param charsetName
	 */
	public QQZoneListPage(String urlStr) {
		readPageByGet(urlStr, "utf-8", params);
	}
	
	/**
	 * 分两步获取内容，
	 * 第一步：获取空间的总的动态内容
	 * 第二步：分别获取
	  * @Discription:扩展说明
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年11月30日 下午4:58:20
	 */
	private String getZoneTrendsContent(){
		return RegexUtils.getFirstString(getPageSourceCode(), QQ_ZONE_TRENDS, 1);
	}
 
	/**
	 * 获取扣扣空间列表URL
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年11月30日 下午3:46:21
	 */
	private String getZoneUrlList(){
		return RegexUtils.getFirstString(getZoneTrendsContent(), QQ_ZONE_URL, 1);
	}
	
	/**
	 * 获取发布说说的好友名称
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年11月30日 下午4:02:46
	 */
	private String getZoneFriendNameList(){
		return RegexUtils.getFirstString(getZoneTrendsContent(), QQ_ZONE_URL, 2);
	}
	
	/**
	 * 获取说说的发布终端
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年11月30日 下午4:02:34
	 */
	private String getZoneTerminal(){
		return RegexUtils.getFirstString(getZoneTrendsContent(), QQ_ZONE_TERMINAL, 1);
	}
	
	/**
	 * 获取说说点赞的数量
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年11月30日 下午4:02:16
	 */
	private String getZoneVisitCount(){
		return RegexUtils.getFirstString(getZoneTrendsContent(), QQ_ZONE_VISIT, 1);
	}
	
	/**
	 * 获取说说内容
	  * @Discription:扩展说明
	  * @return
	  * @return List<String>
	  * @Author: feizi
	  * @Date: 2015年11月30日 下午4:02:06
	 */
	private String getZoneDetail(){
		return RegexUtils.getFirstString(getZoneTrendsContent(), QQ_ZONE_DETAIL, 1);
	}
	
	/**
	 * 获取说说发表时间
	  * @Discription:扩展说明
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年12月1日 上午11:24:04
	 */
	private String getZonePublishTime(){
		return RegexUtils.getFirstString(getZoneTrendsContent(), QQ_ZONE_PUBLISH_TIME, 1);
	}
	
	public static void main(String[] args) {
		QQZoneListPage qqZoneListPage = new QQZoneListPage("http://user.qzone.qq.com/347431996");
		System.out.println("==说说列表URL：" + qqZoneListPage.getZoneUrlList());
		System.out.println("==说说好友名称：" + qqZoneListPage.getZoneFriendNameList());
		System.out.println("==说说发布终端：" + qqZoneListPage.getZoneTerminal());
		System.out.println("==说说浏览数：" + qqZoneListPage.getZoneVisitCount());
		System.out.println("==说说内容：" + qqZoneListPage.getZoneDetail());
		System.out.println("==说说发表时间：" + qqZoneListPage.getZonePublishTime());
	}
}
