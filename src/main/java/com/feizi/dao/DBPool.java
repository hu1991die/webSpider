package com.feizi.dao;

import org.apache.log4j.Logger;

import com.feizi.utils.ClassUtils;


/**
 * 数据库连接池配置
 * @author ljj
 * @time 2015年11月6日 下午9:39:28
 * TODO
 */
public final class DBPool {

	private static DBPool dbPool = null;
	private String poolPath = null;
	
	private Logger log = Logger.getLogger(DBPool.class);
	private static String path = ClassUtils.getClassRootPath(DBPool.class);
	
	private DBPool(){
		
	}
	
	/**
	 * 从数据库连接池中获取数据库连接
	 * @return
	 */
	public static DBPool getDBPool(){
		if(null == dbPool){
			synchronized (DBPool.class) {
				if(null == dbPool){
					dbPool = new DBPool();
				}
			}
		}
		return dbPool;
	}
	
	/**
	 * 设置数据库连接池的配置文件路径
	 * @param poolPath
	 */
	public void setPoolPath(String poolPath){
		this.poolPath = poolPath;
	}
	
	/**
	 * 返回数据库连接池配置文件路径
	 * @return
	 */
	protected String getPoolPath(){
		//如果没有指定配置文件，则使用默认的配置文件
		if(null == poolPath){
			poolPath = path + "proxool.xml";
			log.info("DataBase's poolPath is null, use default path:" + poolPath);
		}
		return poolPath;
	}
}
