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
      //����ServerSocket 
      ServerSocket serverSocket = new ServerSocket(54320); 
      while (true) { 
        //���ܿͻ������� 
        Socket client = serverSocket.accept(); 
        System.out.println("accept"); 
        try{ 
          //���տͻ�����Ϣ 
          BufferedReader in = new BufferedReader(new InputStreamReader
        		  (client.getInputStream(),"UTF-8")); 
          String str = in.readLine(); 
          System.out.println("read:" + str);
          
          int requestNum = Integer.parseInt(str);
          System.out.println("������Ϊ��" + requestNum);
          //��ͻ��˷�����Ϣ 
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
				out.println("���Է�����������");
				break;
			default:
				break;
		}
//          out.println("server message");
          
          //�ر��� 
          out.close(); 
          in.close(); 
        } catch (Exception e){ 
        	
          System.out.println(e.getMessage()); 
          e.printStackTrace(); 
          
        } finally { 
          //�ر� 
          client.close(); 
          System.out.println("close"); 
        } 
      } 
    } catch (Exception e) { 
      System.out.println(e.getMessage()); 
    } 
  } 
  // ?characterEncoding=utf8 ��������д�����ݿ�����
  public static final String url = "jdbc:mysql://127.0.0.1/connect_sql?characterEncoding=utf8";  
  public static final String name = "com.mysql.jdbc.Driver";  
  public static final String password = "123";
  public static Connection connection; 
  public static String sql1 = "create table table1 (id integer " +
  		"primary key not null, name text not null, tel text)";
  public static String sql = "insert into mtable (no,name) values (0,'sql')";
  public static String sql2 = "select * from mtable";
  public static String sql3 = "insert into table1 values (5,'����','18877543725')";
  public static java.sql.PreparedStatement pps;
  
  //main���������������� 
  public static void main(String a[]) { 
	System.out.println("���Ƿ���ˣ��ȴ��������ݣ�");
    Thread desktopServerThread = new Thread(new Server()); 
    desktopServerThread.start();
    
    try {
		Class.forName(name);
    	connection = DriverManager.getConnection(url, name, password);
    	pps = connection.prepareStatement(sql);
		if (!connection.isClosed()) {
			System.out.println("���ݿ����ӳɹ�");
		}
		
		//System.out.println(.toString() + "\n");
		
//		System.out.println("����:" + pps.executeUpdate() + "��");// ִ��sql���  ���в������
		
		//System.out.println("����" + pps.execute(sql1)); �½���
		
		// ��ȡ�������ݿ�����
		Statement statement = (Statement) connection.createStatement();
		System.out.println("����������" + statement.executeUpdate(sql3));;
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










