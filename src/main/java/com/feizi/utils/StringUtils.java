/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.feizi.utils;

/**  
 * 字符串处理工具类
 * @Author: feizi
 * @Date: 2015年11月12日 上午11:23:57 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年11月12日 上午11:23:57 
 * @Version:V6.0
 */
public final class StringUtils {

	private StringUtils(){
		
	}
	
	/**
	 * 判断该字符串是否为空
	  * @Discription:扩展说明
	  * @param sourceStr
	  * @return
	  * @return boolean
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:25:37
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:25:37
	 */
	public static boolean isBlank(String sourceStr){
		if(null == sourceStr || sourceStr.trim().length() == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断该字符串非空
	  * @Discription:扩展说明
	  * @param sourceStr
	  * @return
	  * @return boolean
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:26:31
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:26:31
	 */
	public static boolean isNotBlank(String sourceStr){
		if(null == sourceStr || sourceStr.trim().length() == 0){
			return false;
		}
		return true;
	}
}
