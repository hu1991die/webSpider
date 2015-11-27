package com.feizi.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json数据处理工具类
 * @author ljj
 * @time 2015年11月2日 下午9:20:16
 * TODO
 */
public final class JsonUtils {

	public static final String NO_DATA = "{\"data\":null}";
	public static final String NO_RESULT = "{\"result\":false}";
	private static ObjectMapper mapper;
	
	static{
		mapper = new ObjectMapper();
		//转换json时，如果对象中的属性值为null，则不生成该属性
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	/**
	 * 防止实例化
	 */
	private JsonUtils(){
		
	}
	
	/**
	 * 将json格式字符串转换成对象
	 * @param jsonStr
	 * @return 当解析失败时返回null
	 */
	public static JsonNode jsonToObject(String jsonStr){
		try {
			return mapper.readTree(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将java对象转成json格式字符串
	 * @param object
	 * @return 当解析失败时返回当解析失败返回{data:null}
	 */
	public static String objectToJson(Object object){
		if(null == object){
			return NO_DATA;
		}
		
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return NO_DATA;
		}
	}
	
	/**
	 * 将java对象转换成json格式的字符串，并且可以指定一个json格式的root名
	 * @param object
	 * @param root
	 * @return 当解析失败返回{data:null}
	 */
	public static String objectToJson(Object object, String root){
		if(null == object){
			return NO_DATA;
		}
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"");
			sb.append(root);
			sb.append("\":");
			sb.append(mapper.writeValueAsString(object));
			sb.append("}");
			return sb.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return NO_DATA;
		}
	}
	
	/**
	 * 将json字符串包装成jsonp，例如var data={}方式
	 * @param json
	 * @param var
	 * @return 若传入的var值为null，则默认的变量名为datas
	 */
	public static String wrapperJsonp(String json, String var){
		if(null == var){
			var = "datas";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("var ");
		sb.append(var);
		sb.append("=");
		sb.append(json);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("key1", 1);
		map.put("key2", 2);
		map.put("key3", 3);
		
		System.out.println(JsonUtils.objectToJson(map));
	}
}
