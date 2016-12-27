package com.lsl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DbUtils {

	private static DbUtils instance = null;
	private static Connection conn;
	private static Statement statement;
	// 单例模式
	public static DbUtils getInstance() {
		if(instance == null) {
			instance = new DbUtils();
		}
		return instance;
	}
	
	private DbUtils() {
		try {
			Class.forName(NetServer.name);
			conn = DriverManager.getConnection(
					NetServer.url, NetServer.name, NetServer.password);
			if (!conn.isClosed()) {
				System.out.println("数据库链接成功！！！");
			}
			
			statement = (Statement) conn.createStatement();
			System.out.println("建表：" + !statement.execute(NetServer.create_table)); // 执行建表语句
		
		} catch (Exception e) {
			System.out.println("table status:" + e.getMessage());
			System.out.println("等待接收数据...");
		}
	}
	
	// 保存数据
	public static String SaveData(String name,String psw) {
		String result = "";
		String insert_sql = "insert into mdb values (0,'" + name + "', '" + psw + "')";
		try {
			result = String.valueOf(statement.executeUpdate(insert_sql));
			System.out.println("已保存：" + result + "条");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			try {
				statement.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return result;
	}
	// 读取数据并返回
	public static String QueryData(String name) {
		String rpsw = null;
		String query_sql = "select psw from mdb where name='" + name + "'";
		try {
			ResultSet res = statement.executeQuery(query_sql);
			if (res.next()) {
				rpsw = res.getString("psw");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			try {
				statement.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return rpsw;
	}
	
}
