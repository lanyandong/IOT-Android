package com.example.shixun.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

public class NetManager {

	public static void sendToAllMobile(String msg) {


		// TODO Auto-generated method stub
		Collection<ReadNetThread> mm = ReadNetThread.mobiles.values();
		for(ReadNetThread m:mm){
			try {
				m.dos.writeUTF(msg);  // 给客户端发送数据
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 网关服务端，接受手机客户端发来的消息
	public static void start() {
		new Thread() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(1234);
					while(true) {
						Socket client = server.accept();
						new ReadNetThread(client).start();	// 线程里面启动线程
					}	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

}
