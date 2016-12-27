package com.lsl;

public class NetServer {

	/**
	 * @param args
	 */
	// 连接数据库
	public static final String url = "jdbc:mysql://127.0.0.1/connect_sql?characterEncoding=utf8";  
	// 加载数据库驱动
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String password = "123";
	public static final String create_table = "create table mdb (id int primary key " +
			"auto_increment,name varchar(10) not null, psw varchar(10) not null)";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbUtils.getInstance();
		
		new Thread(new RunServer()).start();// 启动服务
	}

}
