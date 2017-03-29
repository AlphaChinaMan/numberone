package com.example.socialapp.fragme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.act.PrivateMessageActivity;
import com.example.socialapp.adapte.CharlistAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * //聊天消息列表
 * Created by 陈梦轩 on 2017/3/22.
 */

public class NewssFragment extends Fragment implements EMCallBack, EMConversationListener, EMConnectionListener, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private Button send_news, exits_news;
    List<EMConversation> list = new ArrayList<EMConversation>();
    private EditText name_news, contents_news;
    private ListView listView_news;
    private CharlistAdapter cla;
    private View rootView;
    private View connection_lay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.layout_news_frgment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListView();

    }

    private void initView() {
        connection_lay = rootView.findViewById(R.id.Connection_lay);
        send_news = (Button) rootView.findViewById(R.id.send_news);
        exits_news = (Button) rootView.findViewById(R.id.exits_news);
        name_news = (EditText) rootView.findViewById(R.id.name_news);
        contents_news = (EditText) rootView.findViewById(R.id.contents_news);
        listView_news = (ListView) rootView.findViewById(R.id.list_news1);
        send_news.setOnClickListener(this);
        listView_news.setOnItemClickListener(this);
        listView_news.setOnItemLongClickListener(this);

    }

    private void initDate() {
        list.clear();
        // 获取所有会话
        Map<String, EMConversation> conversation = EMClient.getInstance().chatManager().getAllConversations();

        for (EMConversation emc : conversation.values()) {
            list.add(emc);
        }
        sort();

    }

    ;

    // 发送文本消息方法 只发送文本
    public void sendTxtMsg() {
        String zh = name_news.getText().toString();
        String wen = contents_news.getText().toString();
        // 创建一条文本消息，content为消息文字内容
        // toChatUderName 为对方用户或群聊Id
        EMMessage message = EMMessage.createTxtSendMessage(wen, zh);
        // 设置消息状态回调
        message.setMessageStatusCallback(this);
        // 发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    // 与环信服务器断开后 调用
    @Override
    public void onDisconnected(int arg0) {
        // TODO Auto-generated method stub
        // 运行UI线程
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // 提示 连接已断开
                Toast.makeText(getActivity(), "网络连接已断开", Toast.LENGTH_SHORT).show();
                // 给connection_lay控件 可见状态 设置 状态为可见
                connection_lay.setVisibility(View.VISIBLE);
            }
        });
    }    // 与环信服务器连接成功后 调用

    @Override
    public void onConnected() {
        // TODO Auto-generated method stub
        // 运行UI线程
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // 给connection_lay控件 可见状态 设置为 完全隐藏消失
                connection_lay.setVisibility(View.GONE);
            }
        });
    }

    private void intestTo(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 发送按钮
            case R.id.send_news:
                // 调用发送文本消息
                sendTxtMsg();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), PrivateMessageActivity.class);
        EMConversation emc = (EMConversation) cla.getItem(position);
        if (emc.getType() == EMConversation.EMConversationType.GroupChat) {
            i.putExtra("groupId", emc.getUserName());

        } else {
            i.putExtra("username", emc.getUserName());
        }
        startActivity(i);
    }

    public void notifyList() {
        // TODO Auto-generated method stub
        // 调用加载方法
        initDate();
        // 刷新ListView
        if (cla != null) {
            cla.refAll(list);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        EMConversation msg = (EMConversation) cla.getItem((int) id);
        EMClient.getInstance().chatManager().deleteConversation(msg.getUserName(), true);
        cla.refAll(list);
        return false;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onProgress(int i, String s) {

    }


    private void initListView() {
        initDate();
        cla = new CharlistAdapter(getActivity(), list);
        listView_news.setAdapter(cla);
    }

    ;

    @Override
    public void onCoversationUpdate() {
        // TODO Auto-generated method stub
        // 调用加载方法
        initDate();
        // 运行UI线程
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "接收消息", Toast.LENGTH_SHORT).show();
                // 刷新ListView
                cla.refAll(list);
            }
        });
    }

    private void sort() {
        // 给list集合排序的规则 依据 （接口）
        Comparator<EMConversation> comp = new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {

                if (o1.getLastMessage().getMsgTime() < o2.getLastMessage().getMsgTime()) {
                    return 1;
                } else if (o1.getLastMessage().getMsgTime() == o2.getLastMessage().getMsgTime()) {
                    return 0;
                } else if (o1.getLastMessage().getMsgTime() > o2.getLastMessage().getMsgTime()) {
                    return -1;
                }
                return 0;
            }
        };
        Collections.sort(list, comp);
    }
}
