package com.example.socialapp.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.ben.CreatGroupBen;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by 陈梦轩 on 2017/4/8.
 */

public class CreatGroupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name_edit_group;
    private Button creat_button_group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_group_creat);
        init();
    }

    private void init() {
        name_edit_group = (EditText) findViewById(R.id.name_edit_group);
        creat_button_group = (Button) findViewById(R.id.creat_button_group);
        creat_button_group.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.creat_button_group:
                //获取创建群组所填的信息
                CreatGroupBen cgb=getDate();
                //创建群组
                try {
                    EMClient.getInstance().groupManager().createGroup(cgb.getGroupname(), cgb.getDesc(), cgb.getAllMembers(), cgb.getReason(), cgb.getOption());
                    Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
                    finish();
                } catch (HyphenateException e) {
                    // 创建失败 会抛出异常 进行失败提示
                    Toast.makeText(this,"创建失败",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
        }
    }

    //获取创建群组 所填写信息的方法
    private CreatGroupBen getDate() {
        //实现其他属性的交互，校验数据
        CreatGroupBen cgb = new CreatGroupBen();
        //获取群名字
        String name = name_edit_group.getText().toString();
        cgb.setGroupname(name);//群组名
        cgb.setAllMembers(new String[]{});//初始化群成员
        cgb.setDesc("");//简介
        cgb.setReason("");//邀请成员加入的reason
        //群组类型选项，；可以设置群组最大用户数默认（200）及群组类型
       EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
        option.maxUsers = 200;//设置用户数
        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
        // option里的GroupStyle分别为：
        // EMGroupStylePrivateOnlyOwnerInvite----私有群，只有群主可以邀请人
        // EMGroupStylePrivateMemberCanInvite-----私有群，群成员也能邀请人进群
        // EMGroupStylePublicJoinNeedApproval------公开群，加入此群除了群主邀请，只能通过申请加入此群
        // EMGroupStylePublicOpenJoin-------公开群，任何人都能加入此群
        cgb.setOption(option);
        return cgb;
    }
}
