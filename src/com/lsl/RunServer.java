package com.lsl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// 创建serversocket
			ServerSocket socket_server = new ServerSocket(12345);
			while(true) {
				Socket client = socket_server.accept();
				System.out.println("accept");
				try {
					// 接收客户端信息
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
					String read = in.readLine();
					System.out.println("收到数据：" + read);
				
					
					// 向客户端发送信息
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(client.getOutputStream(),"UTF-8")),true);
					out.println(doNext(read));
					
					//DbUtils.getInstance();
					//DbUtils.SaveData("haha", "12345");
//					System.out.println("读取的数据：" + DbUtils.QueryData(read));;
//					System.out.println("返回数据：" + doNext(read));
					
					//关闭流
					out.close();
					in.close();
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				} finally {
					// 关闭流
					client.close();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String doNext(String str) {
		String[] rstr = str.split("\\|");
		String result = "";
//		System.out.print(rstr[0] + ",");
//		System.out.print(rstr[1] + ",");
//		System.out.println(rstr[2]);
		if (rstr[0].equals("register")) {
			result = DbUtils.SaveData(rstr[1], rstr[2]);
		} else if (rstr[0].equals("login")) {
			result = DbUtils.QueryData(rstr[1]);
		}
		System.out.println("返回数据：" + result);
		return result;
	}

}
