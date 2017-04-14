package com.example.socialapp.adapte;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socialapp.R;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 陈梦轩 on 2017/3/28.
 */

public class MessAdapter extends BaseAdapter {
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

    // 给空间设置数据的方法
    public void setViewContent(Holder holder, EMMessage emMessage) {
        // 设置控件的可见状态
        holder.item_msg_time_lay.setVisibility(View.VISIBLE);
        //获取时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        holder.item_msg_time_textview.setText(dateFormat.format(emMessage.getMsgTime()));
        EMMessage.Type type = emMessage.getType();
             switch (type){
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
                         holder.item_msg_right_context.setText(txt.getMessage());
                         holder.item_msg_right_namee.setText("浮生若梦");
                     }
                     break;
                 case IMAGE:
                     // 判断消息的发送方是不是自己
                     if (emMessage.getFrom().equals(emMessage.getUserName())) {
                         //如果不是自己
                         holder.item_msg_right_lay.setVisibility(View.GONE);
                         holder.item_msg_left_lay.setVisibility(View.VISIBLE);
                         // 获取消息对象中的消息体 并强转成 文本消息体
                         // TODD 需加其他消息类型
                         holder.item_msg_left_context.setVisibility(View.GONE);
                         holder.leftImage.setVisibility(View.VISIBLE);
                         holder.item_msg_right_namee.setText(emMessage.getUserName());
                         // 获取消息对象中的消息体 并强转成 文本消息体
                         // TODD 需加其他消息类型
                         EMImageMessageBody txt = (EMImageMessageBody) emMessage.getBody();
                         //设置图片
                         Glide.with(context)
                                 .load(txt.getThumbnailUrl())
                                 .override(200,300)
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
                         // 获取消息对象中的消息体 并强转成 文本消息体
                         // TODD 需加其他消息类型
                         holder.item_msg_right_namee.setText("浮生若梦");
                         EMImageMessageBody txt = (EMImageMessageBody) emMessage.getBody();
                         //设置图片
                         Glide.with(context)
                                 .load(txt.getLocalUrl())
                                 .override(200,300)
                                 .into(holder.reghtImage);

                     }
                     break;


             }

    }// 存放空间的内部类


    class Holder {
        LinearLayout item_msg_time_lay, item_msg_left_lay, item_msg_right_lay;
        TextView item_msg_time_textview, item_msg_left_name, item_msg_left_context, item_msg_right_namee,
                item_msg_right_context;
        ImageView item_msg_right_img, item_msg_left_img,leftImage,reghtImage;

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

            leftImage= (ImageView) view.findViewById(R.id.item_msg_left_image);
            reghtImage= (ImageView) view.findViewById(R.id.item_msg_right_image);
        }


    }

}
