package com.feizi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

/**
 * xml数据处理工具类
 * @author ljj
 * @time 2015年11月2日 下午9:52:29
 * TODO
 */
public final class XmlUtils {

	private static final String NO_RESULT = "<root>no result</root>";
	
	/**
	 * 防止实例化
	 */
	private XmlUtils(){
		
	}
	
	/**
	 * 将Java对象转成XML格式的字符串
	 * @param object
	 * @return 解析失败返回"<root>no result</root>"
	 */
	public static String objectToXml(Object object){
		if(null == object){
			return NO_RESULT;
		}
		
		StringWriter sw = new StringWriter();
		JAXBContext jAXBContent = null;
		Marshaller marshaller = null;
		
		try {
			jAXBContent = JAXBContext.newInstance(object.getClass());
			marshaller = jAXBContent.createMarshaller();
			marshaller.marshal(object, sw);
			
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return NO_RESULT;
	}
	
	/**
	 * 将XML格式字符串转成XML对象
	 * @param xml
	 * @return 当解析失败时返回null
	 */
	public static Document createFromString(String xml){
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取指定xPath的文本
	 * @param xPath
	 * @param node
	 * @return 当解析失败返回null
	 */
	public static String getTextFromNode(String xPath, Node node){
		try {
			return node.selectSingleNode(xPath).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取XML文件
	 * @param path
	 * @return 返回文件对应的Document
	 */
	public static Document createFromPath(String path){
		return createFromString(readFile(path));
	}
	
	/**
	 * 读取文件
	 * @param path
	 * @return 返回文件内容字符串
	 */
	private static String readFile(String path){
		File file = new File(path);
		FileInputStream fileInputStream = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			fileInputStream = new FileInputStream(file);
			//使用UTF-8编码读取内容
			String charset = null;
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,charset);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String s;
			
			while((s = bufferedReader.readLine()) != null){
				s = s.replaceAll("\t", "").trim();
				if(null != s && s.length() > 0){
					sb.append(s);
				}
			}
			
			bufferedReader.close();
			inputStreamReader.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
