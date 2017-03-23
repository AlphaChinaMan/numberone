package com.example.socialapp.fragme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialapp.R;
import com.example.socialapp.adapte.NewsAdapter;
import com.example.socialapp.ben.info;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表
 * Created by 陈梦轩 on 2017/3/22.
 */

public class FramnewsActivity extends Fragment {
    LinearLayoutManager llm;
    private List<info> list = new ArrayList<>();
private NewsAdapter adapter;
    private RecyclerView recyclerView;

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
        list.add(new info("bsdaozi", "nihdfdsao", "12.00", "5"));
        list.add(new info("baozi", "nihsdfdao", "12.00", "56"));
        list.add(new info("basdsdffsozi", "nihao", "12.00", "57"));
        list.add(new info("badfsdozi", "nihsdfsdao", "12.00", "58"));
        list.add(new info("bafsdfozi", "nihsfsdfao", "12.00", "57"));
        list.add(new info("baosdfszi", "nifsfshao", "12.00", "54"));
        list.add(new info("baosdfsdfzi", "nfsfihao", "12.00", "51"));
        list.add(new info("basfozi", "nihasfso", "12.sfss00", "52"));
        list.add(new info("basfdozi", "nihsfsdao", "1fs2.00", "50"));
        adapter=new NewsAdapter(getActivity(),list);
        llm=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


    }
}
