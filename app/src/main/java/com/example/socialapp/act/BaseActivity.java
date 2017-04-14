package com.example.socialapp.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 陈梦轩 on 2017/3/22.
 */


public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


public void IntoPrivateMessage(String uesrName){
    Intent i=new Intent(this,PrivateMessageActivity.class);
    i.putExtra("uesrName",uesrName);
    startActivity(i);

}



}
