package com.example.socialapp.adapte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialapp.R;
import com.example.socialapp.act.PrivateMessageActivity;
import com.example.socialapp.ben.info;
import com.hyphenate.chat.EMGroup;

import java.util.List;

/**
 * Created by 陈梦轩 on 2017/3/23.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyVieqHolder> {
    private List<EMGroup> list;
    private Context context;

    //构造方法  接受上下文和数据源
    public ContactsAdapter(Context context, List<EMGroup> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public MyVieqHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_conts_item, parent, false);
        MyVieqHolder mvh = new MyVieqHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyVieqHolder holder, final int position) {
        //获取当前item的值
        EMGroup group = list.get(position);
        //给空间设置数据
        String groupName = group.getGroupName();
        holder.text_news_name_item_friends.setText(groupName);
        holder.text_news_content_item.setText("");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMGroup emGroup = list.get(position);
                String groupId = emGroup.getGroupId();
                //
                intent2To(PrivateMessageActivity.class, groupId);
            }
        });

    }

    private void intent2To(Class<?> cls, String groupId) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("groupId", groupId);
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyVieqHolder extends RecyclerView.ViewHolder {

        ImageView imageView_news_item_friends, imageView_news_item_zi;
        TextView text_news_name_item_friends, text_news_time_item_zi, listview_item_time, text_news_wen_item, text_news_content_item;

        public MyVieqHolder(View itemView) {
            super(itemView);
            text_news_name_item_friends = (TextView) itemView.findViewById(R.id.text_news_name_item_friends);
            text_news_time_item_zi = (TextView) itemView.findViewById(R.id.text_news_time_item_zi);
            listview_item_time = (TextView) itemView.findViewById(R.id.listview_item_time);

            imageView_news_item_friends = (ImageView) itemView.findViewById(R.id.imageView_news_item_friends);
            imageView_news_item_zi = (ImageView) itemView.findViewById(R.id.imageView_news_item_zi);
            text_news_wen_item = (TextView) itemView.findViewById(R.id.text_news_wen_item);
            text_news_content_item = (TextView) itemView.findViewById(R.id.text_news_content_item);
        }


    }
}
