/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.feizi.crawl.novel.duokan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类别模块列表数据信息Model类
 * 
 * @Author: feizi
 * @Date: 2015年12月1日 下午5:02:08
 * @Version:V6.0
 */
public class TypeModelListModel implements Serializable {

	private static final long serialVersionUID = -1255964264506611282L;

	private List<NovelInfoModel> novelList = new ArrayList<NovelInfoModel>();
	
	// 顶级根路径
	private String rootDomain;
	//模块数量
	private int modelCount = 0;
	//模块URL
	private String modelUrl;
	//模块名称
	private String modelName;

	public String getRootDomain() {
		return rootDomain;
	}

	public void setRootDomain(String rootDomain) {
		this.rootDomain = rootDomain;
	}
	
	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setNovelList(List<NovelInfoModel> novelList) {
		this.novelList = novelList;
	}

	public List<NovelInfoModel> getNovelList() {
		return novelList;
	}

	public void setNoveLlist(List<NovelInfoModel> novelList) {
		this.novelList = novelList;
	}

	public int getModelCount() {
		return modelCount;
	}

	public void setModelCount(int modelCount) {
		this.modelCount = modelCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
