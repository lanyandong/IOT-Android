package com.example.shixun.iotclient.net;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shixun.iotclient.R;
import com.example.shixun.iotclient.menu.MenuActivity;
import com.example.shixun.iotclient.message.RecvMsgThread;
import com.example.shixun.iotclient.user.UserActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText1;
    private EditText editText2;
    private DataOutputStream dos;
    private DataInputStream dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText1 = (EditText)findViewById(R.id.ip);
        editText2 = (EditText)findViewById(R.id.port);

        Button bt1 = (Button)findViewById(R.id.link);
        bt1.setOnClickListener(this);
        Button bt2 = (Button)findViewById(R.id.lgout);
        bt2.setOnClickListener(this);

        Button bt3 = (Button)findViewById(R.id.help);
        bt3.setOnClickListener(this);
    }


    /// 退出程序提示按钮
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
    {

        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            //当按钮click1被按下时执行结束进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };

    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            //当按钮click2被按下时则取消操作
            arg0.cancel();
        }
    };

    public void quit(){

        //定义一个新的对话框对象
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        //设置对话框提示内容
        alertdialogbuilder.setMessage("确定要退出程序吗？");

        //定义对话框2个按钮标题及接受事件的函数
        alertdialogbuilder.setPositiveButton("确定",click1);
        alertdialogbuilder.setNegativeButton("取消",click2);
        //创建并显示对话框
        AlertDialog alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }

    @Override
    public void onClick(View arg) {
        // TODO Auto-generated method stub
        switch(arg.getId()) {
            // 第一个子线程，连接服务器
            case R.id.link:
                // 第一个子线程
                new Thread() {
                    public void run() {

                        // 根据ip和端口生成流
                        String ip = editText1.getText().toString();
                        int port = Integer.parseInt(editText2.getText().toString());
                        NetSocket netSocket = (NetSocket) NetActivity.this.getApplication();

                        try {

                            netSocket.init(ip, port);

                            Socket socket = netSocket.getSocket();
                            dos = netSocket.getOut();
                            dis = netSocket.getIn();

                            // 注册一个用户
                            String str = "login user";
                            dos.writeUTF(str);

                            // 读消息的线程
                            new RecvMsgThread(socket).start();

                            // 跳转功能界面
                            Intent intent = new Intent();
                            intent.setClass(NetActivity.this, MenuActivity.class);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

                break;

            case R.id.lgout:

                quit();
                break;

            case R.id.help:
                startActivity(new Intent(this, UserActivity.class));

        }
    }
}
