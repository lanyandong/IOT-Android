package com.example.shixun.iotclient.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shixun.iotclient.test.ClientActivity;
import com.example.shixun.iotclient.R;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button bt2 = (Button)findViewById(R.id.button5);
        Button bt3 = (Button)findViewById(R.id.button6);
        Button bt4 = (Button)findViewById(R.id.button7);
        Button bt5 = (Button)findViewById(R.id.button8);

        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg) {
        // TODO Auto-generated method stub
        if(arg.getId()==R.id.button8) {
            Intent intent = new Intent();
            intent.setClass(UserActivity.this, ClientActivity.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show(); //2s

        }
    }
}
