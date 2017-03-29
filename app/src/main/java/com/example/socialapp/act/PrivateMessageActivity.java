package com.example.socialapp.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.adapte.MessAdapter;
import com.example.socialapp.fragme.SetFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by 陈梦轩 on 2017/3/28.
 */

public class PrivateMessageActivity extends AppCompatActivity implements EMCallBack, EMMessageListener, EMConversationListener, View.OnClickListener {
    private EditText textedit;
    private ListView msgShowlist;
    private Button sendbtn;
    private TextView message_title_right, titleName;
    private String username;
    private List<EMMessage> message;
    private MessAdapter messageAdapter;
    private String groupId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_private_message);
        EMClient.getInstance().chatManager().addConversationListener(this);
        username = getIntent().getStringExtra("username");
        groupId = getIntent().getStringExtra("groupId");
        initView();
        setTitleName();
        initlistview();
    }

    //设置用户名
    private void setTitleName() {

        if (TextUtils.isEmpty(groupId)) {
            titleName.setText(username);
            //则隐藏群信息按钮
            message_title_right.setVisibility(View.GONE);
        } else {
            titleName.setText(groupId);
            //则显示群信息按钮
            message_title_right.setVisibility(View.VISIBLE);
        }
    }


    private void initView() {
        titleName = (TextView) findViewById(R.id.message_title_name);
        textedit = (EditText) findViewById(R.id.private_message_edittext);
        msgShowlist = (ListView) findViewById(R.id.private_message_listview);
        sendbtn = (Button) findViewById(R.id.private_message_send_btn);
        message_title_right = (TextView) findViewById(R.id.message_title_right);
        msgShowlist.setSelection(msgShowlist.getBottom());// 设置最后一条可见
        sendbtn.setOnClickListener(this);
        message_title_right.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


    private void initlistview() {
        initDate();
        messageAdapter = new MessAdapter(this, message);
        msgShowlist.setAdapter(messageAdapter);
    }


    private void initDate() {
        // 获取聊天记录
        if (TextUtils.isEmpty(groupId)) {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
            message = conversation.getAllMessages();
        } else {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupId);
            if (conversation != null) {
                message = conversation.getAllMessages();
            } else {
                message = new ArrayList<EMMessage>();
            }
        }
        // EMConversation conversation =
        // EMClient.getInstance().chatManager().getConversation(username);
        // 获取此回话的所有消息
        // message = conversation.getAllMessages();
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

    @Override
    public void onCoversationUpdate() {

    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(final List<EMMessage> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PrivateMessageActivity.this, "onCmdMessageReceived", Toast.LENGTH_SHORT).show();
                for (EMMessage message : list) {
                    addSendMsg2list(message);
                }
            }
        });
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
//实现发送文本
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.private_message_send_btn:
                String str = textedit.getText().toString();
                try {
                    sendTxt(str);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                textedit.setText("");
                break;
            case R.id.message_title_right:
                if (!TextUtils.isEmpty(groupId)) {
                    Intent in = new Intent(PrivateMessageActivity.this, SetFragment.class);
                    in.putExtra("groupId", groupId);
                    startActivity(in);
                }
                break;
            default:
                break;
        }
    }

    private void sendTxt(String str) {
        EMMessage message;
        if (TextUtils.isEmpty(username)) {
            // 创建一条文本消息，context为消息文字内容，toChatUsername
            // 为对方用户或群聊的id
            message = EMMessage.createTxtSendMessage(str, groupId);
        } else {
            message = EMMessage.createTxtSendMessage(str, username);
        }

        // 如果是群聊，设置chattype,默认是单聊
        if (TextUtils.isEmpty(username)) {
            message.setChatType(EMMessage.ChatType.GroupChat);
        }
        // 设置聊天类型
        // 设置消息状态回调；
        message.setMessageStatusCallback(this);
        // 发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        addSendMsg2list(message);

    }

    public void addSendMsg2list(EMMessage messages) {
        message.add(messages);
        messageAdapter.notifyDataSetChanged();
        msgShowlist.setSelection(msgShowlist.getBottom());
    }


}
