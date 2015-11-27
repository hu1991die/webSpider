package com.feizi.utils;

/**
 * 解析转换工具类
 * 主要实现将字符串（数字）转换为数值，这个工具类在读取配置文件
 * 或数据转化过程中有很大的作用
 * @author ljj
 * @time 2015年11月4日 下午9:33:59
 * TODO
 */
public final class ParseUtils {

	private ParseUtils(){
		
	}
	
	/**
	 * 将字符串转换为double
	 * @param str
	 * @param defaultDouble
	 * @return
	 */
	public static double parseStringToDouble(String str, double defaultDouble){
		double val = defaultDouble;
		try {
			val = Double.parseDouble(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	/**
	 * 将字符串转换为int
	 * @param str
	 * @param defaultInt
	 * @return
	 */
	public static int parseStringToInt(String str, int defaultInt){
		int val = defaultInt;
		try {
			val = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	/**
	 * 将字符串转换为long
	 * @param str
	 * @param defaultLong
	 * @return
	 */
	public static long parseStringToLong(String str, long defaultLong){
		long val = defaultLong;
		try {
			val = Long.parseLong(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return val;
	}
}
