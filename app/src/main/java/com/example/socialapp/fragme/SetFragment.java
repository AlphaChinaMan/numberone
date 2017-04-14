package com.example.socialapp.fragme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.MainActivity;
import com.example.socialapp.R;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.adapter.EMACallManager;

/**
 * 设置页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class SetFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout LinearLayout;
    private TextView mUserTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.layout_set_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mUserTV = (TextView) view.findViewById(R.id.text_set);
        mUserTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_set:
                AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
                a.setTitle("提示框");
                a.setMessage("确定退出吗？");
                a.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }
                });
                a.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "您已取消", Toast.LENGTH_SHORT).show();
                    }
                });
                a.show();

                break;


        }
    }
}
