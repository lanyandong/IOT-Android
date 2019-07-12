package com.example.shixun.iotclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shixun.iotclient.net.NetActivity;
import com.example.shixun.iotclient.user.UserActivity;

/*
为了更好的交互性，没有使用 MainActivity,但为了开发方便任然保留了。
可以在AndroidManifest.xml 中进行配置
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt1 = (Button)findViewById(R.id.button1);
        Button bt2 = (Button)findViewById(R.id.button2);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if(arg0.getId()==R.id.button1) {
            startActivity(new Intent(this, NetActivity.class));
        }
        else {
            startActivity(new Intent(this, UserActivity.class));
        }
    }
}
