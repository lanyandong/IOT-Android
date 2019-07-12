package com.example.shixun.net;

import com.example.shixun.com.ComManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


public class ReadNetThread extends Thread {
	public static HashMap<String, ReadNetThread> mobiles = new HashMap<String, ReadNetThread>();
	
	public DataInputStream dis;
	public DataOutputStream dos;
	
	public ReadNetThread(Socket s) {
		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				String s = dis.readUTF();
				String[] param = s.split(" "); // 识别客户端发来的指令

				if(param[0].equals("login")) {
					mobiles.put(param[1], this);

				}else if(param[0].equals("logout")) {
					mobiles.remove(param[1]);
				}

				else {
					// 将收到消息发给串口
					ComManager.getInstance().sendToXtq(s);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
