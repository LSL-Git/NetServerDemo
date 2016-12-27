package com;

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.InputStreamReader; 
import java.io.OutputStreamWriter; 
import java.io.PrintWriter; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
public class Server implements Runnable { 
	
  public void run() { 
	  
    try { 
      //创建ServerSocket 
      ServerSocket serverSocket = new ServerSocket(54320); 
      while (true) { 
        //接受客户端请求 
        Socket client = serverSocket.accept(); 
        System.out.println("accept"); 
        try{ 
          //接收客户端消息 
          BufferedReader in = new BufferedReader(new InputStreamReader
        		  (client.getInputStream(),"UTF-8")); 
          String str = in.readLine(); 
          System.out.println("read:" + str);
          
          int requestNum = Integer.parseInt(str);
          System.out.println("请求码为：" + requestNum);
          //向客户端发送消息 
          PrintWriter out = new PrintWriter( new BufferedWriter
        		  ( new OutputStreamWriter(client.getOutputStream(),"UTF-8")),true);
        switch (requestNum) {
			case 0:
				out.println("baidu");
				break;
			case 1:
				out.println("aiqiyi");
				break;
			case 2:
				out.println("来自服务器的数据");
				break;
			default:
				break;
		}
//          out.println("server message");
          
          //关闭流 
          out.close(); 
          in.close(); 
        } catch (Exception e){ 
        	
          System.out.println(e.getMessage()); 
          e.printStackTrace(); 
          
        } finally { 
          //关闭 
          client.close(); 
          System.out.println("close"); 
        } 
      } 
    } catch (Exception e) { 
      System.out.println(e.getMessage()); 
    } 
  } 
  // ?characterEncoding=utf8 避免中文写入数据库乱码
  public static final String url = "jdbc:mysql://127.0.0.1/connect_sql?characterEncoding=utf8";  
  public static final String name = "com.mysql.jdbc.Driver";  
  public static final String password = "123";
  public static Connection connection; 
  public static String sql1 = "create table table1 (id integer " +
  		"primary key not null, name text not null, tel text)";
  public static String sql = "insert into mtable (no,name) values (0,'sql')";
  public static String sql2 = "select * from mtable";
  public static String sql3 = "insert into table1 values (5,'中文','18877543725')";
  public static java.sql.PreparedStatement pps;
  
  //main函数，开启服务器 
  public static void main(String a[]) { 
	System.out.println("我是服务端，等待接收数据！");
    Thread desktopServerThread = new Thread(new Server()); 
    desktopServerThread.start();
    
    try {
		Class.forName(name);
    	connection = DriverManager.getConnection(url, name, password);
    	pps = connection.prepareStatement(sql);
		if (!connection.isClosed()) {
			System.out.println("数据库链接成功");
		}
		
		//System.out.println(.toString() + "\n");
		
//		System.out.println("插入:" + pps.executeUpdate() + "行");// 执行sql语句  进行插入操作
		
		//System.out.println("建表：" + pps.execute(sql1)); 新建表
		
		// 读取本地数据库内容
		Statement statement = (Statement) connection.createStatement();
		System.out.println("插入条数：" + statement.executeUpdate(sql3));;
		ResultSet rs = statement.executeQuery(sql2);
		while (rs.next()) {
			System.out.println(rs.getInt("no") + ":" + rs.getString("name"));
		}
		
		
		/*ResultSet rss = pps.executeQuery(sql2);
		while (rss.next()) {
			System.out.println(rss.getInt("no") + ":" + rss.getString("name"));
		}*/

		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			pps.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  } 
}










