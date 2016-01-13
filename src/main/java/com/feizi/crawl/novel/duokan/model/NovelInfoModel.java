/**
 * @Description:扩展说明
 * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
 * @Version: V6.0
 */
package com.feizi.crawl.novel.duokan.model;

import java.io.Serializable;

/**
 * 小说详情实体类Model
 * 
 * @Author: feizi
 * @Date: 2015年12月1日 下午5:19:41
 * @Version:V6.0
 */
public class NovelInfoModel implements Serializable {

	private static final long serialVersionUID = 7861739000468080024L;

	private String name;
	private int grade;
	private int ratingValue;
	private String country;
	private String author;
	private String desc;
	private float price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
