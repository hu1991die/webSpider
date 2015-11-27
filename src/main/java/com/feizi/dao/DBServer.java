/**
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.feizi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.feizi.utils.StringUtils;


/**  
 * 增删改查四个数据库操作接口
 * @Author: feizi
 * @Date: 2015年11月12日 上午10:35:28 
 * @ModifyUser: feizi
 * @ModifyDate: 2015年11月12日 上午10:35:28 
 * @Version:V6.0
 */
public class DBServer {

	private DBOperation dbOperation;
	
	/**
	 * 在使用该类之前，请保证函数DNPool.getDBPool().setPoolPath()已经运行
	 * @Title:
	 * @Description:
	 * @param dbPoolName
	 */
	public DBServer(String dbPoolName){
		dbOperation = new DBOperation(dbPoolName);
	}
	
	/**
	 * 释放连接，只执行完数据库操作后，必须执行此命令
	  * @Discription:扩展说明
	  * @return void
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午10:40:19
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午10:40:19
	 */
	public void close(){
		dbOperation.close();
	}
	
	/**
	 * 执行完此insert命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param table
	  * @param columns
	  * @param params
	  * @return
	  * @return int
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:08:24
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:08:24
	 */
	public int insert(String table, String columns, HashMap<Integer, Object> params) throws ClassNotFoundException, SQLException{
		String sql = insertSql(columns, table);
		return dbOperation.executeUpdate(sql, params);
	}
	
	/**
	 * 执行完此insert命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param sql
	  * @return
	  * @throws SQLException
	  * @return int
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:01:00
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:01:00
	 */
	public int insert(String sql) throws SQLException{
		return dbOperation.executeUpdate(sql);
	}
	
	/**
	 * 执行完insertGetGenerateKeys命令之后，需执行close()操作释放资源
	  * @Discription:扩展说明
	  * @param table
	  * @param columns
	  * @param params
	  * @return
	  * @return ResultSet
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:07:33
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:07:33
	 */
	public ResultSet insertGetGenerateKeys(String table, String columns, HashMap<Integer, Object> params) throws ClassNotFoundException, SQLException{
		String sql = insertSql(columns, table);
		return dbOperation.getGeneratedKeys(sql, params);
	}
	
	/**
	 * 执行完此insertGetGenerateKeys命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param sql
	  * @return
	  * @return ResultSet
	 * @throws SQLException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:09:49
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:09:49
	 */
	public ResultSet insertGetGenerateKeys(String sql) throws SQLException{
		return dbOperation.getGeneratedKeys(sql);
	}
	
	/**
	 * 执行完此delete命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param table
	  * @param condition
	  * @return
	  * @return int
	 * @throws SQLException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:10:46
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:10:46
	 */
	public int delete(String table, String condition) throws SQLException{
		if(null == table){
			return 0;
		}
		String sql = "DELETE FROM " + table + " " + condition;
		return dbOperation.executeUpdate(sql);
	}
	
	/**
	 * 执行完此delete命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param sql
	  * @return
	  * @return int
	 * @throws SQLException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:15:54
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:15:54
	 */
	public int delete(String sql) throws SQLException{
		return dbOperation.executeUpdate(sql);
	}
	
	/**
	 * 执行完此select命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param columns
	  * @param table
	  * @param condition
	  * @return
	  * @return ResultSet
	 * @throws SQLException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:17:39
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:17:39
	 */
	public ResultSet select(String columns, String table, String condition) throws SQLException{
		String sql = "SELECT " + columns + " FROM " + table + " " + condition;
		return dbOperation.executeQuery(sql);
	}
	
	/**
	 * 执行完此select命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param sql
	  * @return
	  * @throws SQLException
	  * @return ResultSet
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:18:43
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:18:43
	 */
	public ResultSet select(String sql) throws SQLException{
		return dbOperation.executeQuery(sql);
	}
	
	/**
	 * 执行完此update命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param table
	  * @param columns
	  * @param condition
	  * @param params
	  * @return
	  * @return int
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:20:46
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:20:46
	 */
	public int update(String table, String columns, String condition, HashMap<Integer, Object> params) throws ClassNotFoundException, SQLException{
		String sql = updateSql(table, columns, condition);
		return dbOperation.executeUpdate(sql, params);
	}
	
	/**
	 * 执行完此update命令之后，需执行close()操作，释放资源
	  * @Discription:扩展说明
	  * @param sql
	  * @return
	  * @return int
	 * @throws SQLException 
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:21:36
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:21:36
	 */
	public int update(String sql) throws SQLException{
		return dbOperation.executeUpdate(sql);
	}
	
	/**
	 * 组装update的Sql语句
	  * @Discription:扩展说明
	  * @param table
	  * @param columns
	  * @param condition
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:35:58
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:35:58
	 */
	private String updateSql(String table, String columns, String condition){
		if(StringUtils.isBlank(columns) || StringUtils.isBlank(table)){
			return "";
		}
		String[] column = columns.split(",");
		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(table);
		sb.append(" SET ");
		/*sb.append(column[0]);
		sb.append("=?");
		for (int i = 1; i < column.length; i++) {
			sb.append(", ");
			sb.append(column[i]);
			sb.append("=?");
		}*/
		int colNum = column.length;
		for (int i = 0; i < colNum; i++) {
			sb.append(column[i]);
			sb.append(" = ?");
			if(i == colNum - 1){
				break;
			}
			sb.append(", ");
		}
		sb.append(" ");
		sb.append(condition);
		return sb.toString();
	}
	
	/**
	 * 组装insert的Sql语句
	  * @Discription:扩展说明
	  * @param columns
	  * @param table
	  * @return
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年11月12日 上午11:42:00
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 上午11:42:00
	 */
	private String insertSql(String columns, String table){
		if(StringUtils.isBlank(columns) || StringUtils.isBlank(table)){
			return "";
		}
		int colNum = columns.split(",").length;
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(table);
		sb.append(" (");
		sb.append(columns);
		/*sb.append(") VALUES (?");
		for (int i = 1; i < colNum; i++) {
			sb.append(",?");
		}*/
		sb.append(") VALUES (");
		for (int i = 0; i < colNum; i++) {
			sb.append("?");
			if(i == colNum -1){
				break;
			}
			sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 测试
	  * @Discription:扩展说明
	  * @param args
	  * @return void
	  * @Author: feizi
	  * @Date: 2015年11月12日 下午1:11:00
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年11月12日 下午1:11:00
	 */
	public static void main(String[] args) {
		DBServer dbServer = new DBServer(null);
		String table = "studentDB";
		String columns = "ID,NAME,SEX,EMAIL,ADDRESS";
		String condition = " WHERE ID = 3" ;
		String sql = dbServer.updateSql(table, columns, condition);
//		String sql = dbServer.insertSql(columns, table);
		System.out.println(sql);
	}
}
