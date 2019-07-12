package com.example.shixun.com;

import com.example.shixun.message.ComEvent;
import com.example.shixun.net.NetManager;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 15741 on 2019/7/3.
 */

class ReadComThread extends Thread {

    private BufferedReader reader;

    public ReadComThread(InputStream is) {
        reader = new BufferedReader(new InputStreamReader(is));
    }

    public void run() {

        while(true) {
            try {
                String msg = reader.readLine();
                // 使用 EventBus 发布
                EventBus.getDefault().post(new ComEvent(msg));
                NetManager.sendToAllMobile(msg);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
    }
}
