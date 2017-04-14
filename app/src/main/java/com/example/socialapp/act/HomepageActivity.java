package com.example.socialapp.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.MainActivity;
import com.example.socialapp.R;
import com.example.socialapp.fragme.NewssFragment;
import com.example.socialapp.fragme.SetFragment;
import com.example.socialapp.fragme.ContactsFragment;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, EMConnectionListener {
    private FragmentManager fm;
    private NewssFragment fna;
    private ContactsFragment fca;
    private SetFragment fsa;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<Fragment>();
    private Button aa_news, aa_contacts, aa_set;
    private Fragment fragme;
private TextView t_home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(this);
        add2List();
        init();
    }

    private void init() {
        t_home= (TextView) findViewById(R.id.t_home);
        aa_set = (Button) findViewById(R.id.aa_set);
        aa_contacts = (Button) findViewById(R.id.aa_contacts);
        aa_news = (Button) findViewById(R.id.aa_news);
        aa_contacts.setOnClickListener(this);
        aa_set.setOnClickListener(this);
        aa_news.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        fm = getSupportFragmentManager();
        FragmentPagerAdapter fpa = new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        //限定加载的页面个数
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fpa);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }

    private void add2List() {
        fna = new NewssFragment();
        fsa = new SetFragment();
        fca = new ContactsFragment();
        list.add(fna);
        list.add(fca);
        list.add(fsa);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aa_news:
                viewPager.setCurrentItem(0);
                break;
            case R.id.aa_contacts:
                viewPager.setCurrentItem(1);
                break;
            case R.id.aa_set:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        NotifColorBackGroud(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void NotifColorBackGroud(int i) {
        if (i == 0) {
            aa_news.setBackgroundResource(R.color.colorAccent);
            aa_set.setBackgroundResource(R.color.colorPrimary);
            aa_contacts.setBackgroundResource(R.color.colorPrimary);
        }
        if (i == 1) {
            aa_news.setBackgroundResource(R.color.colorPrimary);
            aa_contacts.setBackgroundResource(R.color.colorAccent);
            aa_set.setBackgroundResource(R.color.colorPrimary);
        }
        if (i == 2) {
            aa_news.setBackgroundResource(R.color.colorPrimary);
            aa_contacts.setBackgroundResource(R.color.colorPrimary);
            aa_set.setBackgroundResource(R.color.colorAccent);

        }
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int error) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                    t_home.setText("帐号已经被移除");
                    t_home.setVisibility(View.VISIBLE);
                    Toast.makeText(HomepageActivity.this,"帐号已经被移除",Toast.LENGTH_SHORT).show();
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    t_home.setText("帐号已在其他设备登录");
                    t_home.setVisibility(View.VISIBLE);
                    Toast.makeText(HomepageActivity.this,"帐号已在其他设备登录",Toast.LENGTH_SHORT).show();

                } else {
                    if (NetUtils.hasNetwork(HomepageActivity.this)) {
                        //连接不到聊天服务器
                        t_home.setText("连接不到聊天服务器");
                        t_home.setVisibility(View.VISIBLE);
                        Toast.makeText(HomepageActivity.this,"连接不到聊天服务器",Toast.LENGTH_SHORT).show();

                    } else {
                        //当前网络不可用，请检查网络设置
                        t_home.setText("当前网络不可用，请检查网络设置");
                        t_home.setVisibility(View.VISIBLE);
                        Toast.makeText(HomepageActivity.this,"当前网络不可用，请检查网络设置",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

}
