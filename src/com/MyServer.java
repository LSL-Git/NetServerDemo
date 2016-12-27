package com;

public class MyServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("我是服务端，等待就收数据！");
		// 开启服务
		Thread desktopServerThread = new Thread(new NetServer());
		desktopServerThread.start();
	}

}
