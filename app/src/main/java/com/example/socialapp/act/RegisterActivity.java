package com.example.socialapp.act;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.socialapp.R;

/**
 * 注册页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText account_edit_register, password_edit_register, password_edit1_register;
    private Button register_main_register;
    private Handler mHander;
private ImageView chat_register_password_code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        init();
        mHander = new Handler() {
            public void handleleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1000:
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 1001:
                        Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1002:
                        Toast.makeText(getApplicationContext(), "用户已存在！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1003:
                        Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1004:
                        Toast.makeText(getApplicationContext(), "注册失败：" + (String) msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void init() {
chat_register_password_code= (ImageView) findViewById(R.id.chat_register_password_code);
        password_edit1_register = (EditText) findViewById(R.id.password_edit1_register);
        password_edit_register = (EditText) findViewById(R.id.password_edit_register);
        register_main_register = (Button) findViewById(R.id.register_main_register);
        account_edit_register = (EditText) findViewById(R.id.account_edit_register);
        chat_register_password_code.setImageBitmap(IdentifyCode.);

        }
        }
