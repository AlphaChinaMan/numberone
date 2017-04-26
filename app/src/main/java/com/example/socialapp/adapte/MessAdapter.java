package com.example.socialapp.adapte;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socialapp.R;
import com.example.socialapp.act.VideoActivity;
import com.example.socialapp.img.ImageTest;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;

import org.wlf.filedownloader.FileDownloader;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.socialapp.R.id.default_activity_button;
import static com.example.socialapp.R.id.item_msg_right_image;

/**
 * Created by 陈梦轩 on 2017/3/28.
 */

public class MessAdapter extends BaseAdapter {
    EMVideoMessageBody em;
    private Context context;
    private List<EMMessage> list;

    // 构造方法 接受上下文 和数据源
    public MessAdapter(Context context, List<EMMessage> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 声明内部类
        Holder holder = null;
        // 判断View是否为空
        if (convertView == null) {
            // 加载布局给View
            convertView = View.inflate(context, R.layout.item_msg_list, null);
            // 实例化内部类
            holder = new Holder();
            // 初始化控件
            holder.setviews(convertView);
            convertView.setTag(holder);
        } else {
            // 获取之前set的 初始化完成的控件
            holder = (Holder) convertView.getTag();
        }
        // 给空间设置数据
        setViewContent(holder, (EMMessage) getItem(position));
        // 把 View return 回去
        return convertView;
    }

    // 给空间设置数据的方法 (ViewHolder控件对象，EMMessage消息对象)
    public void setViewContent(Holder holder, EMMessage emMessage) {
        // 设置控件的可见状态
        holder.item_msg_time_lay.setVisibility(View.VISIBLE);
        //获取时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        holder.item_msg_time_textview.setText(dateFormat.format(emMessage.getMsgTime()));
        EMMessage.Type type = emMessage.getType();
        switch (type) {
            case TXT:
                // 判断消息的发送方是不是自己
                if (emMessage.getFrom().equals(emMessage.getUserName())) {
                    //如果不是自己
                    holder.item_msg_right_lay.setVisibility(View.GONE);
                    holder.item_msg_left_lay.setVisibility(View.VISIBLE);
                    holder.item_msg_left_context.setVisibility(View.VISIBLE);
                    holder.leftImage.setVisibility(View.GONE);
                    // 获取消息对象中的消息体 并强转成 文本消息体
                    // TODD 需加其他消息类型
                    EMTextMessageBody txt = (EMTextMessageBody) emMessage.getBody();
                    holder.item_msg_left_context.setText(txt.getMessage());
                    //設置用戶名
                    holder.item_msg_left_name.setText(emMessage.getUserName());

                } else {
                    //是自己
                    holder.item_msg_right_lay.setVisibility(View.VISIBLE);
                    holder.item_msg_left_lay.setVisibility(View.GONE);
                    holder.item_msg_right_context.setVisibility(View.VISIBLE);
                    holder.reghtImage.setVisibility(View.GONE);
                    holder.item_msg_right_namee.setText(emMessage.getUserName());
                    // 获取消息对象中的消息体 并强转成 文本消息体
                    // TODD 需加其他消息类型

                    EMTextMessageBody txt = (EMTextMessageBody) emMessage.getBody();
                    String str = txt.getMessage();
                    SpannableString spannableString = new SpannableString(str);
                    //正则表达式比配字符串里是否含有表情
                    String zhengze = "\\[[^\\]]+\\]";
                    //通过传入的正则表达式来生成一个pattern;
                    Pattern pattern = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(spannableString);

                    while (matcher.find()) {
                        String key = matcher.group();
                        try {
                            if (ImageTest.getImg(key) == 0) {
                                continue;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }

                        int indexStart = matcher.start();
                        int indexEnd = indexStart + key.length();

                        spannableString.setSpan(new ImageSpan(
                                        context
                                        , ImageTest.getImg(key))
                                , indexStart
                                , indexEnd
                                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    holder.item_msg_right_context.setText(spannableString);
                    holder.item_msg_right_context.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.item_msg_right_namee.setText("浮生若梦");
                }
                break;
            case IMAGE:
                // 判断消息的发送方是不是自己
                if (emMessage.getFrom().equals(emMessage.getUserName())) {
                    //如果不是自己
                    holder.item_msg_right_lay.setVisibility(View.GONE);
                    holder.item_msg_left_lay.setVisibility(View.VISIBLE);
                    holder.item_msg_left_context.setVisibility(View.GONE);
                    holder.leftImage.setVisibility(View.VISIBLE);
                    holder.item_msg_right_namee.setText(emMessage.getUserName());
                    // 获取消息对象中的消息体 并强转成 文本消息体
                    // TODD 需加其他消息类型
                    EMImageMessageBody txt = (EMImageMessageBody) emMessage.getBody();
                    //设置图片
                    Glide.with(context)
                            .load(txt.getThumbnailUrl())
                            .override(200, 300)
                            .into(holder.leftImage);
                    //設置用戶名
                    holder.item_msg_left_name.setText(emMessage.getUserName());

                } else {
                    //是自己
                    holder.item_msg_right_lay.setVisibility(View.VISIBLE);
                    holder.item_msg_left_lay.setVisibility(View.GONE);
                    holder.item_msg_right_context.setVisibility(View.GONE);
                    holder.reghtImage.setVisibility(View.VISIBLE);
                    holder.item_msg_right_namee.setText(emMessage.getUserName());
                    // 获取消息对象中的消息体 并强转成 图片消息体
                    // TODD 需加其他消息类型
                    holder.item_msg_right_namee.setText("浮生若梦");
                    EMImageMessageBody txt = (EMImageMessageBody) emMessage.getBody();
                    //设置图片
                    Glide.with(context)
                            .load(txt.getLocalUrl())
                            .override(200, 300)
                            .into(holder.reghtImage);

                }
                break;
            case VIDEO:
                //视频消息类型
                final EMVideoMessageBody emVideo = (EMVideoMessageBody) emMessage.getBody();
                // 判断消息的发送方是不是自己
                if (emMessage.getFrom().equals(emMessage.getUserName())) {

                    //如果不是自己
                    holder.item_msg_right_lay.setVisibility(View.GONE);
                    holder.item_msg_left_lay.setVisibility(View.VISIBLE);
                    holder.item_msg_left_context.setVisibility(View.GONE);
                    holder.leftImage.setVisibility(View.VISIBLE);
                    holder.item_msg_right_namee.setText(emMessage.getUserName());
                    // 获取消息对象中的消息体 并强转成 文本消息体
                    // TODD 需加其他消息类型


                    //设置图片
                    Glide.with(context)
                            .load(R.mipmap.ic_launcher)
                            .override(200, 300)
                            .into(holder.leftImage);
                    //設置用戶名
                    holder.item_msg_left_name.setText(emMessage.getUserName());
                    holder.leftImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //根据视频消息状态  做不同的业务
                            switch (emVideo.downloadStatus()) {
                                case DOWNLOADING:
                                    break;
                                case SUCCESSED:
//                                    //播放
//                                    Intent intent = new Intent(context, VideoActivity.class);
//                                    context.startActivity(intent);
//                                    Intent intent = new Intent(context, VideoActivity.class);
//                                    intent.putExtra("path", emVideo.getLocalUrl());
//                                    context.startActivity(intent);
                                    break;
                                case FAILED:
                                    break;
                                case PENDING:
                                    //下载
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    final String vs = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4";
                                    if (!TextUtils.isEmpty(emVideo.getSecret()))
                                        map.put("share-secret", emVideo.getSecret());
                                    textOtherSendVideo(map, vs);
                                    break;
                                default:

                                    break;
                            }
                        }

                        private void textOtherSendVideo(HashMap<String, String> map, final String vs) {
                            EMClient.getInstance()
                                    .chatManager()
                                    .downloadFile(
                                            emVideo.getRemoteUrl()
                                            , vs
                                            , map
                                            , new EMCallBack() {
                                                @Override
                                                public void onSuccess() {
                                                    //TODO 可能需要从新获取消息列表的数据源并且刷新 因为数据有变化
                                                    Log.e("onSuccess", "onSuccess");
                                                    Intent intent = new Intent(context, VideoActivity.class);
                                                    intent.putExtra("leftPath", vs);
                                                    context.startActivity(intent);
                                                }

                                                @Override
                                                public void onError(int i, String s) {
                                                    //TODO 可能需要从新获取消息列表的数据源并且刷新 因为数据有变化

                                                    Log.e("onError", "onError = " + i + "    " + s);
                                                }

                                                @Override
                                                public void onProgress(int i, String s) {

                                                }
                                            });
                        }
                    });

                } else {
                    //是自己
                    holder.item_msg_right_lay.setVisibility(View.VISIBLE);
                    holder.item_msg_left_lay.setVisibility(View.GONE);
                    holder.item_msg_right_context.setVisibility(View.GONE);
                    holder.reghtImage.setVisibility(View.VISIBLE);
                    holder.item_msg_right_namee.setText(emMessage.getUserName());
                    //判断是否是自己发送的
                    //如果是 使用本地路径url
                    //如果不是  使用缩略图路径url

                    //自己发送的消息
                    //设置显示图片   本地url
                    Glide.with(context)
                            .load(emVideo.getLocalUrl())
                            .override(200, 300)
                            .into(holder.reghtImage);
                }
                holder.reghtImage.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
                                                             Intent intent = new Intent(context, VideoActivity.class);
                                                             intent.putExtra("path", emVideo.getLocalUrl());
                                                             context.startActivity(intent);
                                                         }
                                                     }
                );


                break;

        }

    }// 存放空间的内部类


    class Holder {
        LinearLayout item_msg_time_lay, item_msg_left_lay, item_msg_right_lay;
        TextView item_msg_time_textview, item_msg_left_name, item_msg_left_context, item_msg_right_namee,
                item_msg_right_context;
        ImageView item_msg_right_img, item_msg_left_img, leftImage, reghtImage;

        // 初始化控件的方法
        void setviews(View view) {
            item_msg_right_img = (ImageView) view.findViewById(R.id.item_msg_right_img);
            item_msg_left_img = (ImageView) view.findViewById(R.id.item_msg_left_img);
            item_msg_time_lay = (LinearLayout) view.findViewById(R.id.item_msg_time_lay);
            item_msg_left_lay = (LinearLayout) view.findViewById(R.id.item_msg_left_lay);
            item_msg_right_lay = (LinearLayout) view.findViewById(R.id.item_msg_right_lay);

            item_msg_time_textview = (TextView) view.findViewById(R.id.item_msg_time_textview);
            item_msg_left_name = (TextView) view.findViewById(R.id.item_msg_left_name);
            item_msg_right_namee = (TextView) view.findViewById(R.id.item_msg_right_namee);
            item_msg_left_context = (TextView) view.findViewById(R.id.item_msg_left_context);
            item_msg_right_context = (TextView) view.findViewById(R.id.item_msg_right_context);
            reghtImage = (ImageView) view.findViewById(R.id.item_msg_right_image);
            leftImage = (ImageView) view.findViewById(R.id.item_msg_left_image);
        }


    }

}
