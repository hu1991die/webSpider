/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.feizi.utils;

/**  
 * Lucene工具类
 * @Author: feizi
 * @Date: 2015年11月18日 下午7:08:53 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年11月18日 下午7:08:53 
 * @Version:V6.0
 */
public final class LuceneUtils {

	//Lucene特殊字符
	private static final String LUCENE_KEY = "+-!&|(){}[]^\"~*?:\\/";
	
	/**
	 * 处理输入字符串中的Lucene特殊字符
	  * @Discription:扩展说明
	  * @param key 
	  * @param removelSpace 是否移除空格
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年11月18日 下午7:16:54
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月18日 下午7:16:54
	 */
	public static String escapeLuceneKey(String key, boolean removelSpace){
		if(null == key){
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < key.length(); i++) {
			char ch = key.charAt(i);
			if(removelSpace && ch == ' '){
				continue;
			}
			sb.append(escapeLuceneKey(ch));
		}
		return sb.toString();
	}
	
	/**
	 * 处理输入字符串中的lucene特殊字符，不移除空格
	  * @Discription:扩展说明
	  * @param key
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年11月18日 下午7:16:19
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月18日 下午7:16:19
	 */
	public static String escapeLuceneKey(String key){
		return escapeLuceneKey(key, false);
	}
	
	/**
	 * 转义字符
	  * @Discription:扩展说明
	  * @param ch
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年11月18日 下午7:16:10
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月18日 下午7:16:10
	 */
	private static String escapeLuceneKey(char ch){
		if(LUCENE_KEY.indexOf(ch) < 0){
			return ch + "";
		}
		return "\\" + ch;
	}
}
