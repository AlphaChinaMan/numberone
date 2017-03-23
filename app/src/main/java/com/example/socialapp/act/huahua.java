package com.example.socialapp.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.socialapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 会话页
 * Created by 陈梦轩 on 2017/3/23.
 */

public class huahua extends AppCompatActivity{
    private ListView listView;
private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_liaotian);
        init();
    }
    private  void init(){
        listView= (ListView) findViewById(R.id.listview_liao);
    }
}
