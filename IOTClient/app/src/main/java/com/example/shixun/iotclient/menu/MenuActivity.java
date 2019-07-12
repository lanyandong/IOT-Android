package com.example.shixun.iotclient.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shixun.iotclient.R;
import com.example.shixun.iotclient.message.EvenMsg;
import com.example.shixun.iotclient.message.RecvMsgThread;
import com.example.shixun.iotclient.net.NetSocket;
import com.example.shixun.iotclient.tools.LedActivity;
import com.example.shixun.iotclient.tools.MusicActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();  //初始化界面

        NetSocket netSocket = (NetSocket)  MenuActivity.this.getApplication();
        socket = netSocket.getSocket();
        dos = netSocket.getOut();
        dis = netSocket.getIn();
    }


    private void initView() {
        // TODO Auto-generated method stub

        Button b_led = (Button)findViewById(R.id.b_led);
        b_led.setOnClickListener(this);
        Button b_trum = (Button)findViewById(R.id.b_trum);
        b_trum.setOnClickListener(this);


        Button bt1 = (Button)findViewById(R.id.button1);
        bt1.setOnClickListener(this);
        Button bt2 = (Button)findViewById(R.id.button2);
        bt2.setOnClickListener(this);
        Button bt3 = (Button)findViewById(R.id.button3);
        bt3.setOnClickListener(this);
        Button bt4 = (Button)findViewById(R.id.button4);
        bt4.setOnClickListener(this);

        textView1 = (TextView)findViewById(R.id.t_temp);
        textView2 = (TextView)findViewById(R.id.t_hum);
        textView3 = (TextView)findViewById(R.id.t_light);
        textView4 = (TextView)findViewById(R.id.t_gas);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EvenMsg event) {

        String msg = event.getMessage();

        String recmsg = event.getMessage();
        String[] params = recmsg.split(" ");

        switch (params[0]) {

            case "03":
                textView1.setText(params[1]); // 温湿度
                textView2.setText(params[2]);
                break;

            case "04":
                textView3.setText(params[1] + params[2]); // 光照
                break;

            case "05":
                textView4.setText(params[1] + params[2]); // 气体
                break;

            default:
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

        switch(arg.getId()) {

            // 灯光控制
            case R.id.b_led:
                startActivity(new Intent(this, LedActivity.class));
                break;

            // 声音控制
            case R.id.b_trum:
                startActivity(new Intent(this, MusicActivity.class));
                break;

            // 温度
            case R.id.button1:
                new Thread(){
                    public void run() {
                        String str = "03000000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();
                break;


            // 湿度
            case R.id.button2:
                new Thread(){
                    public void run() {
                        String str = "03000000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();
                break;

            // 光照
            case R.id.button3:
                new Thread(){
                    public void run() {
                        String str = "04000000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();

                break;


            // 气体
            case R.id.button4:

                new Thread(){
                    public void run() {
                        String str = "05000000000D";
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
