package com.feizi.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.feizi.utils.XmlUtils;

/**
 * 测试Xml数据处理工具类
 * 
 * @author ljj
 * @time 2015年11月2日 下午10:20:49 TODO
 */
@XmlRootElement(name = "root")
public class TestXmlUtils {
	private int count;
	private List<String> result;

	public TestXmlUtils() {
		count = 3;
		result = new ArrayList<String>();
		result.add("test1");
		result.add("test2");
		result.add("test3");
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

	public static void main(String[] args) {
		System.out.println(XmlUtils.objectToXml(new TestXmlUtils()));
	}
}
