package com.feizi.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

/**
 * 该类主要用户获取request参数值，
 * 并进行简单的数据转换。
 * @author ljj
 * @time 2015年11月4日 下午9:43:46
 * TODO
 */
public final class ParseRequestUtils {

	private ParseRequestUtils(){
		
	}
	
	/**
	 * 获取参数值，返回字符串，参数采用UTF-8编码，去除前后多余的空格
	 * @param request
	 * @param paramName
	 * @param defaultStr
	 * @return
	 */
	public static String getStringUTF8(HttpServletRequest request, String paramName, String defaultStr){
		String param = request.getParameter(paramName);
		if(null == param){
			return defaultStr;
		}
		try {
			return new String(param.getBytes("iso-8859-1"),"utf-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return defaultStr;
		}
	}
	
	/**
	 * 获取参数值，返回字符串，参数采用gbk编码，去除前后多余的空格
	 * @param request
	 * @param paramName
	 * @param defaultStr
	 * @return
	 */
	public static String getStringGBK(HttpServletRequest request, String paramName,String defaultStr){
		String param = request.getParameter(paramName);
		if(null == param){
			return defaultStr;
		}
		try {
			return new String(param.getBytes("iso-8859-1"),"gbk").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return defaultStr;
		}
	}
	
	/**
	 * 获取参数值，返回int型整数
	 * @param request
	 * @param paramName
	 * @param defaultInt
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String paramName, int defaultInt){
		String param = request.getParameter(paramName);
		if(null == param){
			return defaultInt;
		}
		try {
			int val = Integer.parseInt(param);
			return val;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultInt;
		}
	}
	
	/**
	 * 获取参数值，返回long型数值
	 * @param request
	 * @param paramName
	 * @param defaultLong
	 * @return
	 */
	public static long getLong(HttpServletRequest request, String paramName, long defaultLong){
		String param = request.getParameter(paramName);
		if(null == param){
			return defaultLong;
		}
		try {
			long val = Long.parseLong(param);
			return val;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultLong;
		}
	}
}
