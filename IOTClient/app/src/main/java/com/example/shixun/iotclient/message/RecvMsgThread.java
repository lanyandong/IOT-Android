package com.example.shixun.iotclient.message;

import org.greenrobot.eventbus.EventBus;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by 15741 on 2019/7/6.
 */

public class RecvMsgThread extends Thread {
    private DataInputStream dis;

    public RecvMsgThread(Socket client) {
        try {
            dis = new DataInputStream(client.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {

                String msg = dis.readUTF();

                EventBus.getDefault().post(new EvenMsg(msg));

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

