package com.example.socialapp.fragme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.socialapp.R;
import com.example.socialapp.act.PrivateMessageActivity;
import com.example.socialapp.adapte.imageSelectAdapter;
import com.example.socialapp.util.FileUtilss;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by 陈梦轩 on 2017/4/13.
 */

public class ImageSelectFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button send;
    private ArrayList<String> list = new ArrayList<>();
    private imageSelectAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_image_select, container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_imageselect_list);
        send = (Button) view.findViewById(R.id.fragment_imageselect_send_btn);
        list = FileUtilss.getAllImg(getActivity());
        adapter = new imageSelectAdapter(getActivity(), list);
        //线性布局管理器  是指 水平滑动
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        send.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发送图片按钮 点击事件
            case R.id.fragment_imageselect_send_btn:
                //获取选中的所有图片途径
                HashSet<String> checkList = adapter.getCheckList();
                //获取容器activity对象
                PrivateMessageActivity ma = (PrivateMessageActivity) getActivity();
                for (String str : checkList) {
                    Log.e("checkList", str);
                    ma.sendImage(str,false);
                }
                break;
        }
    }
}
