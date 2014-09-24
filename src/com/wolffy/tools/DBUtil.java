package com.wolffy.tools;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.wolffy.model.Table;

/**
 * 数据库操作工具类
 * @author wolffy
 *
 */
public class DBUtil {
	
	private static Logger logger = LogUtil.getLogger(DBUtil.class.getName());  
	
	private static String url = PropsUtil.getValue("db.url");
	private static String driver = PropsUtil.getValue("db.driver");
	private static String username = PropsUtil.getValue("db.username");
	private static String password = PropsUtil.getValue("db.password");
	
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	public Table query(String sql){
//		connection(sql);
//		try {
//			rs = ps.executeQuery();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		int row = 0;
//		int columnCount;
//		Table table = new Table();
//		
//		try {
//			columnCount = rs.getMetaData().getColumnCount();
//			//ResultSet由1开始
//			while(rs.next()){
//				for (int column=0; column<columnCount; column++) {
//					table.put(row, column, rs.getString(column+1));
//	            } 
//				row++;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		close();
//		return table;
//	}
	
	public ResultSet query(String sql){
		connection(sql);
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int update(String sql){
		int r = -1;
		connection(sql);
		try {
			r = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return r;
	}
	
	private static void connection(String sql){
		try {
			conn = DriverManager.getConnection(url, username, password);
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void close(){
        // 关闭记录集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭声明
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭链接对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
}
