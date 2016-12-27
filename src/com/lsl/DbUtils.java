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
	// ����ģʽ
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
				System.out.println("���ݿ����ӳɹ�������");
			}
			
			statement = (Statement) conn.createStatement();
			System.out.println("����" + !statement.execute(NetServer.create_table)); // ִ�н������
		
		} catch (Exception e) {
			System.out.println("table status:" + e.getMessage());
			System.out.println("�ȴ���������...");
		}
	}
	
	// ��������
	public static String SaveData(String name,String psw) {
		String result = "";
		String insert_sql = "insert into mdb values (0,'" + name + "', '" + psw + "')";
		try {
			result = String.valueOf(statement.executeUpdate(insert_sql));
			System.out.println("�ѱ��棺" + result + "��");
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
	// ��ȡ���ݲ�����
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
