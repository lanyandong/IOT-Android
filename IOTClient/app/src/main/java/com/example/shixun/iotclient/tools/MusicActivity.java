package com.example.shixun.iotclient.tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shixun.iotclient.R;
import com.example.shixun.iotclient.net.NetSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{

    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        initView();  //初始化界面

        NetSocket netSocket = (NetSocket)  MusicActivity.this.getApplication();
        socket = netSocket.getSocket();
        dos = netSocket.getOut();
        dis = netSocket.getIn();
    }


    private void initView() {
        // TODO Auto-generated method stub

        textView1 = (TextView)findViewById(R.id.textView1);

        ImageButton bt1 = (ImageButton)findViewById(R.id.imageButton11);
        bt1.setOnClickListener(this);
        ImageButton bt2 = (ImageButton)findViewById(R.id.imageButton12);
        bt2.setOnClickListener(this);
        ImageButton bt3 = (ImageButton)findViewById(R.id.imageButton13);
        bt3.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg) {
        // TODO Auto-generated method stub
        switch (arg.getId()) {

            // music on
            case R.id.imageButton12:
                new Thread() {
                    public void run() {
                        String str = "07010000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    ;
                }.start();
                break;

            // music off
            case R.id.imageButton11:

                new Thread() {
                    public void run() {
                        String str = "07000000000D";
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    };

                }.start();
                break;

            // music puse
            case R.id.button3:
                new Thread() {
                    public void run() {
                        String str = "07000000000D";
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
