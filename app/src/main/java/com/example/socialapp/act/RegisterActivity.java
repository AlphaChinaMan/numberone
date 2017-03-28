package com.example.socialapp.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.util.IdentifyCode;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 注册页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText account_edit_register, password_edit_register, password_edit1_register;
    private Button register_main_register;
    private Handler mHander;
    private ImageView chat_register_password_code, falsh_register;
    private String currCode;

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
        chat_register_password_code = (ImageView) findViewById(R.id.chat_register_password_code);
        password_edit1_register = (EditText) findViewById(R.id.password_edit1_register);
        password_edit_register = (EditText) findViewById(R.id.password_edit_register);
        register_main_register = (Button) findViewById(R.id.register_main_register);
        account_edit_register = (EditText) findViewById(R.id.account_edit_register);
        falsh_register = (ImageView) findViewById(R.id.falsh_register);
        chat_register_password_code.setImageBitmap(IdentifyCode.getInstance().createBitmap());
        currCode = IdentifyCode.getInstance().getCode();
        chat_register_password_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_register_password_code.setImageBitmap(IdentifyCode.getInstance().createBitmap());
                currCode = IdentifyCode.getInstance().getCode();
                Log.i("TAG", "currCode==" + currCode);

            }
        });
        falsh_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register_main_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final String userName = account_edit_register.getText().toString().trim();
                final String password = password_edit_register.getText().toString().trim();
                final String code = password_edit1_register.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getApplicationContext(), "请输入用户名",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "请输入密码",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), "请输入验证码",
                            Toast.LENGTH_SHORT).show();
                } else if (!code.equals(currCode.toLowerCase())) {
                    Toast.makeText(getApplicationContext(), "验证码输入不正确",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                // 调用sdk注册方法
                                EMClient.getInstance()
                                        .createAccount(userName,
                                                password);
                                mHander.sendEmptyMessage(1000);
                            } catch (final HyphenateException e) {
                                // 注册失败
                                Log.i("TAG", "getErrorCode:" + e.getErrorCode());
                                int errorCode = e.getErrorCode();
                                if (errorCode == EMError.NETWORK_ERROR) {
                                    mHander.sendEmptyMessage(1001);
                                } else if (errorCode == EMError.USER_ALREADY_EXIST) {


                                } else if (errorCode == EMError.USER_NOT_FOUND) {
                                    mHander.sendEmptyMessage(1003);
                                } else {
                                    Message msg = Message.obtain();
                                    msg.what = 1004;
                                    msg.obj = e.getMessage();
                                    mHander.sendMessage(msg);
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }
}
