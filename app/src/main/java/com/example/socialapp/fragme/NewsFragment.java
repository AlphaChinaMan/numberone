package com.example.socialapp.fragme;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.adapte.NewsAdapter;
import com.example.socialapp.ben.info;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表
 * Created by 陈梦轩 on 2017/3/22.
 */

public class NewsFragment extends Fragment {
    LinearLayoutManager llm;
    private List<info> list = new ArrayList<>();
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private EditText contentET;
    private TextView topNameTV;
    private ImageView backTV;
    private Button sendBtn;
    private InputMethodManager imm;
private String receiveName=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.layout_news_fragment, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_news);
        adapter = new NewsAdapter(getActivity(), list);
        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

    }
}
