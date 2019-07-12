package com.example.shixun.iotclient.test;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shixun.iotclient.R;
import com.example.shixun.iotclient.message.EvenMsg;
import com.example.shixun.iotclient.message.RecvMsgThread;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText1;
    private EditText editText2;


    private DataOutputStream dos;
    private DataOutputStream dis;

    private TextView textView13;
    private TextView textView14;
    private TextView textView15;

    private EditText editText3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        initView();  //初始化界面


    }

    private void initView() {
        // TODO Auto-generated method stub

        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);

        Button bt1 = (Button)findViewById(R.id.button1);
        bt1.setOnClickListener(this);
        Button bt2 = (Button)findViewById(R.id.button2);
        bt2.setOnClickListener(this);

        textView13 = (TextView)findViewById(R.id.textView13);
        textView14 = (TextView)findViewById(R.id.textView14);
        textView15 = (TextView)findViewById(R.id.textView15);


        Button bt8 = (Button)findViewById(R.id.button8);
        bt8.setOnClickListener(this);
        Button bt9 = (Button)findViewById(R.id.button9);
        bt9.setOnClickListener(this);
        Button bt11 = (Button)findViewById(R.id.button11);
        bt11.setOnClickListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EvenMsg event) {

        textView15.setText(event.getMessage());
        String msg = event.getMessage();
        String[] params = msg.split(" ");
        switch (params[1]) {
            case "01":
                textView13.setText(event.getMessage());
                break;

            case "02":
                textView14.setText(event.getMessage());
                break;

            default:
                textView15.setText(event.getMessage());
                break;
        }
    }

    // 在要接受消息的页面注册
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
    // 取消注册
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View arg) {
        // TODO Auto-generated method stub
        switch(arg.getId()) {
            // 第一个子线程，连接服务器
            case R.id.button1:
                // 第一个子线程
                new Thread(){
                    public void run() {

                        String ip = editText1.getEditableText().toString();
                        int port = Integer.parseInt(editText2.getEditableText().toString());

                        Socket client;
                        try {
                            System.out.println("socket client connecting");
                            client = new Socket(ip, port);
                            System.out.println("socket client connected");

                            dos = new DataOutputStream(client.getOutputStream());

                            new RecvMsgThread(client).start();

                            // 注册一个用户
                            String str = "login user";
                            dos.writeUTF(str);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                }.start();
                break;


            // 断开socket
            case R.id.button2:
                new Thread(){
                    public void run() {
                        try {
                            dos.close();
                            dis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                }.start();

                break;

            // led 控制
            case R.id.button8:
                new Thread(){
                    public void run() {
                        String str = "06010000000D ";
                        try {
                            dos.writeUTF(str);

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();

                break;

            // 温湿度
            case R.id.button9:

                new Thread(){
                    public void run() {
                        String str = "03000000000D";;
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();

                break;

            // 光敏控制
            case R.id.button11:

                new Thread(){
                    public void run() {
                        String str = editText3.getEditableText().toString();
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
