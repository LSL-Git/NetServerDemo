package com;

public class MyServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("���Ƿ���ˣ��ȴ��������ݣ�");
		// ��������
		Thread desktopServerThread = new Thread(new NetServer());
		desktopServerThread.start();
	}

}
