/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.feizi.crawl.novel.duokan.model;

import java.io.Serializable;

/**
 * 小说分类列表数据信息model类
 * 
 * @Author: feizi
 * @Date: 2015年12月1日 下午4:56:04
 * @Version:V6.0
 */
public class NovelTypeListModel implements Serializable {

	private static final long serialVersionUID = -6325923606815286209L;

	// 顶级根路径
	private String rootDomain;
	// url地址
	private String typeUrl;
	// 分类名称
	private String typeName;
	// 每一个类别的统计数量
	private int typeCount;

	public String getRootDomain() {
		return rootDomain;
	}

	public void setRootDomain(String rootDomain) {
		this.rootDomain = rootDomain;
	}

	public String getTypeUrl() {
		return typeUrl;
	}

	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
