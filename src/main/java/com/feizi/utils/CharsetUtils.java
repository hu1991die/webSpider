package com.feizi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

/**
 * 字符编码检测工具类
 * 基于cpdetector第三方jar包实现的编码检测工具类
 * @author ljj
 * @time 2015年11月3日 下午10:21:29
 * TODO
 */
public final class CharsetUtils {

	private static final CodepageDetectorProxy detector;
	
	static{
		//初始化探测器
		detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		detector.add(JChardetFacade.getInstance());
	}
	
	/**
	 * 防止被实例化
	 */
	private CharsetUtils(){
		
	}
	
	/**
	 * 获取文件的编码格式
	 * @param url
	 * @param defaultCharset
	 * @return
	 */
	public static String getStreamCharset(URL url, String defaultCharset){
		if(null == url){
			return defaultCharset;
		}
		
		try {
			//使用第三方jar包检测文件的编码格式
			Charset charset = detector.detectCodepage(url);
			if(null != charset){
				return charset.name();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultCharset;
	}
	
	/**
	 * 获取文件流的编码方式
	 * @param inputStream
	 * @param defaultCharset
	 * @return
	 */
	public static String getStreamCharset(InputStream inputStream, String defaultCharset){
		if(null == inputStream){
			return defaultCharset;
		}
		
		int count = 200;
		try {
			count = inputStream.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//使用第三方jar包检测文件的编码
			Charset charset = detector.detectCodepage(inputStream, count);
			if(null != charset){
				return charset.name();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return defaultCharset;
	}
	
	/**
	 * 测试用例
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		//UTF-8
		URL url = new URL("http://www.csdn.net");
		System.out.println(CharsetUtils.getStreamCharset(url, "default"));
	}
}
