package com.example.socialapp.fragme;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.socialapp.R;
import com.example.socialapp.act.CreatGroupActivity;
import com.example.socialapp.adapte.ContactsAdapter;
import com.example.socialapp.ben.info;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友列表
 * Created by 陈梦轩 on 2017/3/22.
 */

public class ContactsFragment extends Fragment implements View.OnClickListener {
    private List<EMGroup> list ;
    private LinearLayoutManager llm;
    private ContactsAdapter adapter;
    private RecyclerView recyclerView;
    private EditText contentET;
    private TextView topNameTV, group_contact, Publicnumber_contact;
    private ImageView backTV, new_friends;
    private Button sendBtn;
    private View view;


    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.layout_contacts_fragment, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
    }

    private void init() {
         group_contact = (TextView) view.findViewById(R.id.contact_title_Groupchat);
        new_friends = (ImageView) view.findViewById(R.id.group_title_friends);
        Publicnumber_contact = (TextView) view.findViewById(R.id.contact_title_Publicnumber);
        group_contact.setOnClickListener(this);
    }

    private void initView() {
        //获取群组数据
        getDate();
        llm = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_contacts);
        recyclerView.setLayoutManager(llm);
        //实例化适配器
        adapter = new ContactsAdapter(getActivity(), list);
        //recyclerView设置适配器
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.group_title_friends:

                break;
            case  R.id.contact_title_Groupchat:
                intent2To(CreatGroupActivity.class);
                break;
        }
    }
    private void intent2To(Class<?> cls){
        Intent i=new Intent(getActivity(),cls);
        getActivity().startActivity(i);
    }
    private void getDate(){
        try {
            //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
            list= EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        //从本地数据库加载群组列表
      list = EMClient.getInstance().groupManager().getAllGroups();


    }
}
