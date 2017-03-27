package com.example.socialapp.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.socialapp.MainActivity;
import com.example.socialapp.R;
import com.hyphenate.chat.EMClient;

import java.util.Date;

/**
 * //开屏页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class PeacockActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_peacock);
        init();
    }

    //    private void init() {
//
//        new Thread(new Runnable() {
//            Intent intent;
//
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                intent = new Intent(PeacockActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }).start();
//
//    }
    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                //判断帐号之前是否登录过
                if (EMClient.getInstance().isLoggedInBefore()) {
                    //拿到当前时间
                    long start = new Date().getTime();
                    //耗时操作
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    //拿到执行到此行的时间 并肩去开始时间  的到家再所耗时间
                    long cose = new Date().getTime() - start;
                    //登陆过（SDK 已实现自动登录），跳转到主页面
                    try {
                        //还需要睡眠=总睡眠-加载时间
                        Thread.sleep(2000 - cose);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    intent = new Intent(PeacockActivity.this, HomepageActivity.class);
                } else {
                    //没登陆过；跳转到登陆页面
                    try {
                        //休眠2秒
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    intent = new Intent(PeacockActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }).start();
    }
    ///android中的后退键——onBackPressed()  直接获取按钮按下事件onBackPressed() : 当手机按下back键时，执行此方法。
    /*    用此方法时 ：有super.onBackPressed() 时 不能把在此方法中设置的 intent 传回上一个Activity ，
     *  因此 去掉super.onBackPressed()  在末尾加上finish（）；
     *   在上一个activity中用onActivityResult() 方法接受上此Activity的onBackPressed()方法传回去的intent，
     *   在onBackPressed（）设置resultCode  在上一个Activity 的OnActivityResult（）中验证；
     *
     * */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
