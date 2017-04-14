package com.example.socialapp.adapte;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.socialapp.R;
import com.example.socialapp.act.PrivateMessageActivity;
import com.example.socialapp.inface.ListitemonClick;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMTextMessageBody;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.Date;
import java.util.List;

/**
 * Created by 陈梦轩 on 2017/3/28.
 */

public class CharlistAdapter extends BaseAdapter {

    private Context context;
    private List<EMConversation> list;
    private ListitemonClick listitemonClick;

    public CharlistAdapter(Context context, List<EMConversation> list) {
        this.context = context;
        this.list = list;
    }

    public void setListitemonClick(ListitemonClick listitemonClick) {
        this.listitemonClick = listitemonClick;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder = null;
        EMConversation item = (EMConversation) getItem(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.chat_list_item, null);

            holder = new Holder();
            holder.lay_char = (RelativeLayout) convertView.findViewById(R.id.lay_char);
            holder.namet = (TextView) convertView.findViewById(R.id.textView1);
            holder.contents = (TextView) convertView.findViewById(R.id.textView2);
            holder.time = (TextView) convertView.findViewById(R.id.textView3);
            holder.onread = (TextView) convertView.findViewById(R.id.textView4);
            holder.delete_char = (Button) convertView.findViewById(R.id.delete_char_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.namet.setText(item.getUserName());
        String username = item.getUserName();
        try {
            holder.onread.setText(getUnreadMsgc(username));
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        holder.time.setText(String.valueOf(getLastMsgTime(item)));





        String sss;
        try {//如果这条消息是其他消息类型，此行代码会抛出异常。
            EMTextMessageBody txtBody = (EMTextMessageBody) item.getLastMessage().getBody();
            sss = txtBody.getMessage();
        } catch (Exception e) {
            sss = "";
            e.printStackTrace();
        }
        holder.contents.setText(sss);






        final View fianlConvertView = convertView;
        //删除item
        holder.delete_char.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                //关闭侧滑菜单，必需关闭
                ((SwipeMenuLayout) fianlConvertView).quickClose();
            }
        });


        holder.lay_char.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PrivateMessageActivity.class);
                //EMConversation emc = (EMConversation) .getItem(position);

                //  判断 聊天模式   单聊 群聊
                EMConversation emc = (EMConversation) getItem(position);

                if (emc.getType() != EMConversation.EMConversationType.GroupChat) {
                    i.putExtra("username", emc.getUserName());
                } else {
                    i.putExtra("groupId", emc.getUserName());
                }

                context.startActivity(i);

            }
        });
        return convertView;
    }

    private String getLastMsgTime(EMConversation msg) {
        long t = msg.getLastMessage().getMsgTime();
        /// 获取最后一条消息的时间距离现在的时间差
        long notT = new Date().getTime() - t;
        // 把时间差的单位重从秒转分钟
        int m = m2M(notT);
        // 判断时间差是否大于60分钟
        if (m > 60) {// 判断是否大于60分钟 如果大于 转成小时
            if (m2h(m) > 24) {// 判断 转成小时后；是否大于24小时
                return h2d(m2h(m)) + "天前";
            }
            return m2h(m) + "小時前";
        } else {// 看都是不是大于1分钟，如果不是显示刚刚
            if (m > 1)
                return m + "分钟前";
        }
        return "刚刚";

    }


    // 毫秒
    private int m2M(long time) {
        return (int) (time / 1000 / 60);
    }

    private int m2h(long time) {
        return (int) (time / 60);
    }

    private int h2d(long time) {
        return (int) (time / 24);
    }

    private int getUnreadMsgc(String username) {
        return EMClient.getInstance().chatManager().getConversation(username).getUnreadMsgCount();
    }

    public void refAll(List<EMConversation> list) {
//		this.list.clear();
//		this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    class Holder {
        RelativeLayout lay_char;
        TextView namet, contents, time, onread;
        Button delete_char, cancel_char;
    }
}
