package com.feizi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * 数据库操作
 * @author ljj
 * @time 2015年11月6日 下午10:18:36
 * TODO
 */
public class DBOperation {

	private static Logger log = Logger.getLogger(DBOperation.class);
	private Connection conn = null;
	private String dbPoolName;
	
	public DBOperation(String dbPoolName){
		this.dbPoolName = dbPoolName;
	}
	
	/**
	 * 获取Connection数据库连接
	 * @throws SQLException
	 */
	private void open() throws SQLException{
		this.conn = DBManager.getDBManager().getConnection(dbPoolName);
	}
	
	/**
	 * 关闭Connection数据库连接
	 */
	public void close(){
		try {
			if(null != this.conn){
				this.conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 组装PreparedStatement
	 * @param sql 组装的sql字符串
	 * @param params 传入的参数
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private PreparedStatement setPst(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException{
		if(null != params){
			if(0 < params.size()){
				PreparedStatement pst = this.conn.prepareStatement(sql);
				for (int i = 1; i <= params.size(); i++) {
					if(params.get(i).getClass() == Class.forName("java.lang.String")){
						pst.setString(i, params.get(i).toString());
					}else if(params.get(i).getClass() == Class.forName("java.lang.Integer")){
						pst.setInt(i, (Integer) params.get(i));
					}else if(params.get(i).getClass() == Class.forName("java.lang.Boolean")){
						pst.setBoolean(i, (Boolean) params.get(i));
					}else if(params.get(i).getClass() == Class.forName("java.lang.Float")){
						pst.setFloat(i, (Float)params.get(i));
					}else if(params.get(i).getClass() == Class.forName("java.lang.Double")){
						pst.setDouble(i, (Double)params.get(i));
					}else if(params.get(i).getClass() == Class.forName("java.lang.Long")){
						pst.setLong(i, (Long)params.get(i));
					}else if(params.get(i).getClass() == Class.forName("java.sql.Date")){
						pst.setDate(i, Date.valueOf(params.get(i).toString()));
					}else{
						log.info("not found class : " + params.get(i).getClass().toString());
						return null;
					}
				}
				return pst;
			}
		}
		return null;
	}
	
	/**
	 * executeUpdate 执行修改
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected int executeUpdate(String sql) throws SQLException{
		this.open();
		Statement st = this.conn.createStatement();
		int result = st.executeUpdate(sql);
		return result;
	}
	
	/**
	 * executeUpdate
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	protected int executeUpdate(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException{
		this.open();
		PreparedStatement pst = setPst(sql, params);
		int result = 0;
		if(null != pst){
			result = pst.executeUpdate();
		}
		return result;
	}
	
	/**
	 * getGeneratedKeys
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet getGeneratedKeys(String sql) throws SQLException{
		this.open();
		Statement st = this.conn.createStatement();
		st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = st.getGeneratedKeys();
		return rs;
	}
	
	/**
	 * getGeneratedKeys
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	protected ResultSet getGeneratedKeys(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException{
		this.open();
		PreparedStatement pst = setPst(sql, params);
		
		if(null != pst){
			pst.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = pst.getGeneratedKeys();
			return rs;
		}
		return null;
	}
	
	/**
	 * executeQuery
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet executeQuery(String sql) throws SQLException{
		this.open();
		Statement st = this.conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	/**
	 * executeQuery
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	protected ResultSet executeQuery(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException{
		this.open();
		PreparedStatement pst = setPst(sql, params);
		if(null != pst){
			ResultSet rs = pst.executeQuery();
			return rs;
		}
		return null;
	}
}
