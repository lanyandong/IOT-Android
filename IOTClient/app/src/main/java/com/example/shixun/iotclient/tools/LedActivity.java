package com.example.shixun.iotclient.tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shixun.iotclient.R;
import com.example.shixun.iotclient.message.RecvMsgThread;
import com.example.shixun.iotclient.net.NetSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LedActivity extends AppCompatActivity implements View.OnClickListener {

    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        initView();  //初始化界面

        NetSocket netSocket = (NetSocket)  LedActivity.this.getApplication();
        socket = netSocket.getSocket();
        dos = netSocket.getOut();
        dis = netSocket.getIn();
    }


    private void initView() {
        // TODO Auto-generated method stub

        Button bt1 = (Button)findViewById(R.id.button1);
        bt1.setOnClickListener(this);
        Button bt2 = (Button)findViewById(R.id.button2);
        bt2.setOnClickListener(this);
        Button bt3 = (Button)findViewById(R.id.button3);
        bt3.setOnClickListener(this);
        Button bt4 = (Button)findViewById(R.id.button4);
        bt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg) {
        // TODO Auto-generated method stub
        switch(arg.getId()) {

            // led 模式1 on
            case R.id.button1:
                new Thread(){
                    public void run() {
                        String str = "06010000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();

                break;

            // led 模式1 off
            case R.id.button2:
                new Thread(){
                    public void run() {
                        String str = "06000000000D ";;
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();
                break;

            // led 模式二 on
            case R.id.button3:

                new Thread(){
                    public void run() {
                        String str = "06020000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();
                break;

            // led 模式二 Off
            case R.id.button4:

                new Thread(){
                    public void run() {
                        String str = "06000000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();
                break;
        }
    }
}
