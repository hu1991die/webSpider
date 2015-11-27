package com.feizi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 * 数据库连接池管理类
 * 
 * @author ljj
 * @time 2015年11月6日 下午10:06:32 TODO
 */
public final class DBManager {

	private Logger log = Logger.getLogger(DBManager.class);

	private static DBManager dbManager = null;
	private static int activeCount = 0;

	private DBManager() {
		try {
			log.info("=========数据库连接池的配置文件路径：" + DBPool.getDBPool().getPoolPath());
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			JAXPConfigurator.configure(DBPool.getDBPool().getPoolPath(), false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接池管理对象
	 * 单例模式
	 * @return
	 */
	protected static DBManager getDBManager() {
		if (null == dbManager) {
			synchronized (DBManager.class) {
				if (null == dbManager) {
					dbManager = new DBManager();
				}
			}
		}
		return dbManager;
	}

	/**
	 * 获取数据库链接
	 * 
	 * @param dbPoolName
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection(String dbPoolName) throws SQLException {
		Connection conn = DriverManager.getConnection(dbPoolName);
		showSnapshotInfo();
		return conn;
	}

	public static void main(String[] args) {
		try {
			System.out.println(new DBManager().getConnection("proxool.noveldb"));
			showSnapshotInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示连接池的信息
	  * @Discription:扩展说明
	  * @return void
	  * @Author: feizi
	  * @Date: 2015年11月26日 下午4:55:50
	 */
	private static void showSnapshotInfo() {
		try {
			SnapshotIF snapshot = ProxoolFacade.getSnapshot("noveldb", true);
			// 获得当前活动连接数
			int curActiveCount = snapshot.getActiveConnectionCount();
			// 获得可得到的连接数（可用连接数）
			int availableCount = snapshot.getAvailableConnectionCount();
			// 获得总连接数
			int maxCount = snapshot.getMaximumConnectionCount();
			
			// 当活动连接数变化时输出的信息
			if (curActiveCount != activeCount){
				System.out.println("活动连接数:" + curActiveCount
						+ "(active)  可得到的连接数:" + availableCount
						+ "(available)  总连接数:" + maxCount + "(max)");
				activeCount = curActiveCount;
			}
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}
}
