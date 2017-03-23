package com.example.socialapp.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.ben.info;

import java.util.List;

/**
 * Created by 陈梦轩 on 2017/3/23.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyVieqHolder> {
    private List<info> list;
    private Context context;

    public NewsAdapter(Context context, List<info> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyVieqHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_news_item,parent,false);
        MyVieqHolder mvh = new MyVieqHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyVieqHolder holder, int position) {
        holder.text_news_content_item.setText(list.get(position).getContent());
        holder.text_news_name_item.setText(list.get(position).getName());
        holder.text_news_time_item.setText(list.get(position).getTime());
        holder.text_news_wen_item.setText(list.get(position).getWei());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyVieqHolder extends RecyclerView.ViewHolder{

        ImageView imageView_news_item;
        TextView text_news_name_item, text_news_content_item, text_news_time_item, text_news_wen_item;

        public MyVieqHolder(View itemView) {
            super(itemView);
            text_news_name_item = (TextView) itemView.findViewById(R.id.text_news_name_item);
            text_news_content_item = (TextView) itemView.findViewById(R.id.text_news_content_item);
            text_news_time_item = (TextView) itemView.findViewById(R.id.text_news_time_item);
            text_news_wen_item = (TextView) itemView.findViewById(R.id.text_news_wen_item);
            imageView_news_item = (ImageView) itemView.findViewById(R.id.imageView_news_item);

        }



    }
}
