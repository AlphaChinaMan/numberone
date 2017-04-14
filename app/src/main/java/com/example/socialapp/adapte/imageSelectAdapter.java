package com.example.socialapp.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.socialapp.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by 陈梦轩 on 2017/4/13.
 */

public class imageSelectAdapter extends RecyclerView.Adapter<imageSelectAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> list;
    //被选中图片的数据源
    private HashSet<String> checkList = new HashSet<>();
    public imageSelectAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(context).inflate(R.layout.item_image_select,parent,false);
        MyViewHolder mvh=new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
final String path=list.get(position);
        //设置图片
        Glide.with(context).load(path).override(300,200).into(holder.img);
        //设置复选框监听
        //把选中的存入集合中
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkList.add(path);
                }else{
                    checkList.remove(path);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
public  HashSet<String> getCheckList(){
    return  checkList;
}

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_image_select_img);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_image_select_check);

        }

    }
}
