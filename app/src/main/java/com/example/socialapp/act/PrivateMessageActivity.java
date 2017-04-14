package com.example.socialapp.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.socialapp.fragme.ImageSelectFragment;
import com.example.socialapp.fragme.SetFragment;
import com.example.socialapp.manager.MessageManager;
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
    private Button sendbtn, button11, button22, button33;
    private TextView message_title_right, titleName;
    private String username;
    private List<EMMessage> message;
    private MessAdapter messageAdapter;
    private String groupId;
    private FragmentTransaction transaction;
    private ImageSelectFragment imgFragment;
    private FragmentManager fragmentManager;
    private GoogleApiClient client;

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        button11 = (Button) findViewById(R.id.button11);
        titleName = (TextView) findViewById(R.id.message_title_name);
        textedit = (EditText) findViewById(R.id.private_message_edittext);
        msgShowlist = (ListView) findViewById(R.id.private_message_listview);
        sendbtn = (Button) findViewById(R.id.private_message_send_btn);
        message_title_right = (TextView) findViewById(R.id.message_title_right);
        imgFragment = new ImageSelectFragment();
        msgShowlist.setSelection(msgShowlist.getBottom());// 设置最后一条可见
        sendbtn.setOnClickListener(this);
        message_title_right.setOnClickListener(this);
        button11.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
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
//        EMConversation conversation =
//                EMClient.getInstance().chatManager().getConversation(username);
//        //获取此回话的所有消息
//        message = conversation.getAllMessages();
    }

    //  EMCallBack
    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onProgress(int i, String s) {

    }


    //EMConversationListener
    @Override
    public void onCoversationUpdate() {

    }


    //  EMMessageListener
    @Override
    public void onMessageReceived(final List<EMMessage> list) {
        //收到消息
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
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
    }

    @Override
    public void onMessageReadAckReceived(final List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        //消息状态变动

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
            case R.id.button11:
                if (imgFragment.isAdded()) {
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(imgFragment);
                    transaction.commit();
                    //从fragment的返回栈中移除fragment
                    fragmentManager.popBackStack("message_bottom_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.message_bottom_fragment, imgFragment);
                    transaction.addToBackStack("message_bottom_fragment");
                    transaction.commit();
                }
                break;
            default:
                break;
        }
    }

    /**
     * +
     * 发送图片
     *
     * @param imgPath     图片路径
     * @param isThumbnail 是否发送原图  true 原图  false 缩略图
     */
    public void sendImage(String imgPath, boolean isThumbnail) {
        EMMessage message = EMMessage.createImageSendMessage(imgPath, isThumbnail, username);
        sendMessage(message);

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

    private void sendMessage(EMMessage message1) {
        //如果是群聊，设置chattype，默认是单聊
//                if (chatType == CHATTYPE_GROUP)
        message1.setChatType(EMMessage.ChatType.Chat);
        message1.setMessageStatusCallback(this);

        //发送消息
        EMClient.getInstance()
                .chatManager()
                .sendMessage(message1);
        //图片发送之后 关闭图片选择fragment
        if (imgFragment.isAdded()) {
            closeImgFragment();
        }
        message.add(message1);

        //调用刷新消息列表的方法
        messageAdapter.notifyDataSetChanged();
    }

    public void addSendMsg2list(EMMessage messages) {
        message.add(messages);
        messageAdapter.notifyDataSetChanged();
        msgShowlist.setSelection(msgShowlist.getBottom());
    }

    private void closeImgFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.remove(imgFragment);
        transaction.commit();
        //从fragment的返回栈中移除fragment
        fragmentManager.popBackStackImmediate("message_bottom_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PrivateMessage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
