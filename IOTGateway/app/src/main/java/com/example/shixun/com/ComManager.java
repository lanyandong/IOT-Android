package com.example.shixun.com;

import android.serialport.SerialPort;

import java.io.DataOutputStream;
import java.io.IOException;


public class ComManager {

    private static ComManager instance=null;
    private SerialPort port;
    private Thread readThread;
    private DataOutputStream dos;

    // 打开串口
    public void open() {
        try {

            // 使用串口api，打开串口
            port = new SerialPort("/dev/ttySAC3", 115200);

            // 防止重启多余线程
            if(readThread!=null) {
                readThread.interrupt();
                readThread = null;
            }

            dos = new DataOutputStream(port.getOutputStream());    // 输出流，给协调器发送消息流
            readThread = new ReadComThread(port.getInputStream()); // 输入流，启动线程，读取客户端发来的消息
            readThread.start();


        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 单例模式
    // 不能用new来实例化对象，只能调用getInstance方法来得到对象，
    // 而getInstance保证了每次调用都返回相同的对象。
    public static ComManager getInstance() {
        // TODO Auto-generated method stub
        if(instance==null) {
            instance = new ComManager();
        }
        return instance;
    }

    // 给协调器发送消息
    public void sendToXtq(String s) {
        // TODO Auto-generated method stub
        try {
            dos.writeBytes(s + "\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
