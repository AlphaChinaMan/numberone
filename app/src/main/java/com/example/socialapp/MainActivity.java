package com.example.socialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialapp.act.HomepageActivity;
import com.example.socialapp.act.RegisterActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * +
 * 登陆页
 */
public class MainActivity extends AppCompatActivity {
    private EditText account_edit, password_edit;
    private Button land_main, register_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


    }

    private void init() {
        account_edit = (EditText) findViewById(R.id.account_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        land_main = (Button) findViewById(R.id.land_main);
        register_main = (Button) findViewById(R.id.register_main);
        land_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = account_edit.getText().toString();
                String password = password_edit.getText().toString();

                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });
        register_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void code(String name, String password) {


    }
}
