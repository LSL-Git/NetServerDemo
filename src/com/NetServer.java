package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetServer implements Runnable{

	@Override
	public void run() {
		try {
			// ����ServerSocket
			ServerSocket serverSocket = new ServerSocket(54321);
			
			while (true) {
				// ���տͻ�������
				Socket client = serverSocket.accept();
				System.out.println("accept");
				try {
					// ���տͻ�����Ϣ
					BufferedReader in = 
						new BufferedReader(new InputStreamReader(client.getInputStream()));
					String str = in.readLine();
					System.out.println("read:" + str);
					// �������������Ϣ
					PrintWriter out = new 
					PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
					out.println("server message");
					//�ر���
					out.close();
					in.close();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally {
					// �ر�
					client.close();
					System.out.println("close");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
