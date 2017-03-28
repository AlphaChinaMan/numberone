package com.example.socialapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TimeUtils;

import com.example.socialapp.R;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialapp.act.HomepageActivity;
import com.example.socialapp.act.RegisterActivity;
import com.example.socialapp.view.TestDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * +
 * 登陆页
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText account_edit, password_edit;
    private Button land_main, register_main;
    private CheckBox checkBox_main;
    private TestDialog cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        //
//        ProgressDialog  progressDialog=new ProgressDialog(this);
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setMessage("加载中");
        cd = new TestDialog(this, R.style.CustomDialog);


    }

    private void init() {
        checkBox_main = (CheckBox) findViewById(R.id.checkBox_main);
        account_edit = (EditText) findViewById(R.id.account_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        land_main = (Button) findViewById(R.id.land_main);
        register_main = (Button) findViewById(R.id.register_main);
        land_main.setOnClickListener(this);
        register_main.setOnClickListener(this);
        checkBox_main.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox_main.setChecked(true);
                    password_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    checkBox_main.setChecked(false);
                    password_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

//    private void code(String name, String password) {
//
//
//    }

    private void Login(String name, String password) {
        EMClient.getInstance().login(name, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent);
                cd.cancel();
            }

            @Override
            public void onProgress(int progress, String status) {
                //     Log.d("main", "成功！");
            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                cd.cancel();
            }


        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.land_main:
                cd.show();
                //拿到输入框中的的输入内容
                String name = account_edit.getText().toString();
                String password = password_edit.getText().toString();
                int reCode;
                //根据校验结果  选择登陆  还是提示登录失败
                reCode = code(name, password);
                switch (reCode) {
                    case 0:
                        Login(name, password);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "账号不能为空", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        Toast.makeText(MainActivity.this, "账号密码不能为空", Toast.LENGTH_LONG).show();
                        //提示
                        break;
                }

                break;

            case R.id.register_main:
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                break;

        }
    }

    private int code(String name, String password) {
        if (TextUtils.isEmpty(name)) {
            return 1;
        }
        if (TextUtils.isEmpty(password)) {
            return 2;
        }
        return 0;
    }

}
