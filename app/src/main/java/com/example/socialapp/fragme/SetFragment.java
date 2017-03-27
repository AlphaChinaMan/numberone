package com.example.socialapp.fragme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.socialapp.R;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.adapter.EMACallManager;

/**
 * 设置页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class SetFragment extends Fragment {
    private LinearLayout LinearLayout;
    private TextView mUserTV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
   View view=   inflater.inflate(R.layout.layout_set_fragment, container, false);



        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
