package com.example.socialapp.act;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.adapte.MessAdapter;
import com.example.socialapp.fragme.ImageSelectFragment;
import com.example.socialapp.fragme.SetFragment;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;


/**
 * Created by 陈梦轩 on 2017/3/28.
 */

public class PrivateMessageActivity extends AppCompatActivity implements EMCallBack, EMMessageListener, View.OnClickListener, OnFileDownloadStatusListener {
    private String newFileDir = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "FileDownloader";
    private EditText textedit;
    private ListView msgShowlist;
    private Button sendbtn, button11, button22, button33;
    private TextView titleName;
    private ImageView message_title_right;
    private String username, text;
    private List<EMMessage> list;
    private MessAdapter messageAdapter;
    private String groupId;
    private FragmentTransaction transaction;
    private ImageSelectFragment imgFragment;
    private FragmentManager fragmentManager;
    private final static int IMAGE_CODE = 1001;
    private final static int OPEN_VIDEO_CAPTURE = 1002;
    File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_private_message);
        username = getIntent().getStringExtra("username");
        groupId = getIntent().getStringExtra("groupId");
        initView();
        //添加下载监听
        FileDownloader.registerDownloadStatusListener(this);
        getDataFromIntent();
        setTitleName();
        initlistview();

    }

    /**
     * +
     * 获取传递进来的数据
     * 用户名和草稿
     */
    private void getDataFromIntent() {
        username = getIntent().getStringExtra("username");
        text = getIntent().getStringExtra("text");

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

    /**
     * +
     * 设置activity关闭是返回的数据
     */
    private void setRes() {
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra("username", username);
    }

    private void initView() {
        button11 = (Button) findViewById(R.id.button11);
        button22 = (Button) findViewById(R.id.button22);
        button33 = (Button) findViewById(R.id.button33);
        titleName = (TextView) findViewById(R.id.message_title_name);
        textedit = (EditText) findViewById(R.id.private_message_edittext);
        msgShowlist = (ListView) findViewById(R.id.private_message_listview);
        sendbtn = (Button) findViewById(R.id.private_message_send_btn);
        message_title_right = (ImageView) findViewById(R.id.message_title_right);
        imgFragment = new ImageSelectFragment();
        msgShowlist.setSelection(msgShowlist.getBottom());// 设置最后一条可见
        sendbtn.setOnClickListener(this);
        message_title_right.setOnClickListener(this);
        button11.setOnClickListener(this);
        button22.setOnClickListener(this);
        button33.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


    private void initlistview() {
        initDate();
        messageAdapter = new MessAdapter(this, list);
        msgShowlist.setAdapter(messageAdapter);
    }


    private void initDate() {
        // 获取聊天记录
        if (TextUtils.isEmpty(groupId)) {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
            //获取此会话的所有消息
            list = conversation.getAllMessages();
        } else {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupId);
            if (conversation != null) {
                //获取此会话的所有消息
                list = conversation.getAllMessages();
            } else {
                list = new ArrayList<EMMessage>();
            }
        }
    }

    //  EMCallBack
    @Override
    public void onSuccess() {
        Log.e("onSuccess", "xxxxxxxxxxxxxxxxxxx成功");
    }

    @Override
    public void onError(int i, String s) {
        Log.e("onError:", "onError=" + i + "  " + s);
    }

    @Override
    public void onProgress(int i, String s) {

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

        //-----------------------视频类-------------------
        //修改过 本地缩略图路径的  视频消息集合
        ArrayList<EMMessage> list1 = new ArrayList<EMMessage>();
// 遍历接受到的消息
        for (EMMessage emMessage : list) {
            // 处理视频消息
            switch (emMessage.getType()){
                case VIDEO :
                    // 获得消息体
                    EMVideoMessageBody emVideo = (EMVideoMessageBody) emMessage.getBody();
                    // 文件名
                    String name = System.currentTimeMillis() + ".jpg";
                    // 下载视频缩略图
                    FileDownloader.createAndStart(
                            emVideo.getThumbnailUrl()
                            , newFileDir
                            , name);
                    // 把本地路径设置给消息体
                    emVideo.setLocalThumb(newFileDir + "/" + name);
                    // 把消息体添加到 消息对象中
                    emMessage.addBody(emVideo);
                    // 把修改过的消息对象添加到集合中
                    list1.add(emMessage);
                    // 处理过的消息添加到数据源
                    this.list.add(emMessage);
                    break;
                default:
                    // 添加到数据源
                    this.list.add(emMessage);
                    break;

            }
        }


    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

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
            case R.id.button22:
                Intent intent = new Intent(ACTION_IMAGE_CAPTURE);

                file = new File(Environment.getDataDirectory()
                        .getAbsolutePath() + "/"
                        + System.currentTimeMillis()
                        + ".jpg");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(intent, IMAGE_CODE);
                break;
            case R.id.button33:
                Intent caotureImageCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                caotureImageCamera.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(caotureImageCamera, OPEN_VIDEO_CAPTURE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CODE:
                if (resultCode == RESULT_OK) {
                    EMMessage.createVideoSendMessage(getPath(data.getData())//视频路径
                            , Environment.getExternalStorageDirectory().getAbsolutePath() +
                                    "Pictures/Screenshots/IMG_20150819_155252.JPG"
                            , 5000//
                            , username
                    );

                    //拿到carera拍照后的图片
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    File file = setCreatBitmap(bitmap);

                    sendImage(file.getAbsolutePath(), false);

                }
                break;
            case OPEN_VIDEO_CAPTURE:
                //实例化多媒体播放类
                MediaPlayer mediaPlayer = new MediaPlayer();
                //获取视频路径
                String path = getPath(data.getData());
                try {
                    mediaPlayer.setDataSource(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取时长
                int duration = mediaPlayer.getDuration();
                //实例化
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                //设置数据源
                mediaMetadataRetriever.setDataSource(path);
//获取某一诊 图像 并写入文件
                file=setCreatBitmap(mediaMetadataRetriever.getFrameAtTime(1000));
                //释放资源
                mediaPlayer.release();
                mediaMetadataRetriever.release();

                if (resultCode == RESULT_OK) {
                    EMMessage videoMsg = EMMessage.createVideoSendMessage(
                            getPath(data.getData())  //视频路径
//                            , Environment.getExternalStorageDirectory().getAbsolutePath()
//                                    + "/storage/sdcard1/Tencent/QQ_Favorite/5689525755.jpg"  //视频预览图片路径
                            , file.getAbsolutePath()
                            , duration                   //视频时长
                            , username             //用户名
                    );
                    sendMessage(videoMsg);
                }
                break;
        }
    }

    @NonNull
    private File setCreatBitmap(Bitmap bitmap) {
        //创建文件对象
        File file = new File(Environment
                .getExternalStorageDirectory()
                , System.currentTimeMillis() + ".jpg");
        try {
            //开启这个文件输出流
            FileOutputStream out = new FileOutputStream(file);
            //把Bitmap内容写入输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            try {
                //刷新输出流
                out.flush();
                //关闭输出流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        return file;
    }

    /**
     * 获取视频文件的uri 获取path
     *
     * @param uri 视频文件的uri
     * @return
     */
    private String getPath(Uri uri) {
        //定义   需要查询的字段
        String[] projection = {MediaStore.Video.Media.DATA};
        //查询uri
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        //获取所需字段 对应的下标；
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        //将  游标 指向移动到第一个
        cursor.moveToFirst();
        //返回  字段下标获取出来的数据
        return cursor.getString(column_index);
    }
//private void getVideo(){
//    EMClient.getInstance().chatManager().downloadFile(
//
//    );
//}

    /**
     * 发送图片
     *
     * @param imgPath     图片路径
     * @param isThumbnail 是否发送原图  true 原图  false 缩略图
     */
    public void sendImage(String imgPath, boolean isThumbnail) {
        //创建图片Image消息
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

    /**
     * +
     * 发送消息方法
     *
     * @param message1
     */
    private void sendMessage(EMMessage message1) {
        //如果是群聊，设置chattype，默认是单聊
//                if (chatType == CHATTYPE_GROUP)
        message1.setChatType(EMMessage.ChatType.Chat);
        message1.setMessageStatusCallback(this);

        //发送消息
        EMClient.getInstance()
                .chatManager()
                .sendMessage(message1);
        //图片发送之后 关闭图片选择fragment图片选择器
        if (imgFragment.isAdded()) {
            closeImgFragment();
        }
        list.add(message1);

        //调用刷新消息列表的方法messageActivity
        messageAdapter.notifyDataSetChanged();
        //调用刷新列表的方法  chatlist
        //MessageManager.getInsatance().getMessageListListener().refChatList();
    }

    public void addSendMsg2list(EMMessage messages) {
        list.add(messages);
        messageAdapter.notifyDataSetChanged();
        msgShowlist.setSelection(msgShowlist.getBottom());
    }

    //关闭图片选择器 fragment
    private void closeImgFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.remove(imgFragment);
        transaction.commit();
        //从fragment的返回栈中移除fragment
        fragmentManager.popBackStackImmediate("message_bottom_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onBackPressed() {
//点击返回键时 把 此会话的用户名和草稿   返回给之前的acyivity
        setRes();
        super.onBackPressed();
    }

    @Override
    public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {

    }

    @Override
    public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {

    }

    @Override
    public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {

    }

    @Override
    public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {

    }

    @Override
    public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {

    }

    @Override
    public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {

    }
}

