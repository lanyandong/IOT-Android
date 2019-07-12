package com.example.shixun.serialportdemo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shixun.com.ComManager;
import com.example.shixun.message.ComEvent;
import com.example.shixun.net.NetManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private TextView tvMsg;
    private EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComManager.getInstance().open();
        // 串口接受
        tvMsg = (TextView)findViewById(R.id.textView3);

        // 串口发送
        editText1 = (EditText)findViewById(R.id.editText1);
        Button bt = (Button)findViewById(R.id.button1);
        bt.setOnClickListener(this);

        NetManager.start();

    }

    //表示无论事件是在哪个线程发布出来的，该事件订阅方法onEvent都会在UI线程中执行，
    //这个在Android中是非常有用的，因为在Android中只能在UI线程中更新UI

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ComEvent event) {

        tvMsg.setText(event.getMessage());

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
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId()) {
            case R.id.button1:
                // 第一个子线程
                new Thread(){
                    public void run() {
                        String msg = editText1.getEditableText().toString();
                        ComManager.getInstance().sendToXtq(msg);
                        System.out.print("sendToXtq:" + msg);

                    };
                }.start();
                break;

            case R.id.button2:

                break;
        }
    }
}
