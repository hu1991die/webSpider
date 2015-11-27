package com.feizi.lucene.index.model;

import java.util.HashSet;

/**
 * 索引相关的配置参数
 * @author ljj
 * @time 2015年11月14日 下午4:19:22
 * TODO
 */
public class IndexConfig {

	//配置参数
	private static HashSet<ConfigBean> configBean = null;
	
	//默认的配置
	private static class LazyLoadIndexConfig{
		private static final HashSet<ConfigBean> configBeanDefault = new HashSet<ConfigBean>();
		static{
			ConfigBean configBean = new ConfigBean();
			configBeanDefault.add(configBean);
		}
	}
	
	public static HashSet<ConfigBean> getConfigBean(){
		// 如果未对IndexConfig初始化，则使用默认配置
		if(configBean == null){
			configBean = LazyLoadIndexConfig.configBeanDefault;
		}
		return configBean;
	}
	
	public static void setConfigBean(HashSet<ConfigBean> configBean){
		IndexConfig.configBean = configBean;
	}
}
