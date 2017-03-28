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

import com.example.socialapp.R;
import com.example.socialapp.fragme.NewssFragment;
import com.example.socialapp.fragme.SetFragment;
import com.example.socialapp.fragme.ContactsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by 陈梦轩 on 2017/3/22.
 */

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fm;
    private NewssFragment fna;
    private ContactsFragment fca;
    private SetFragment fsa;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<Fragment>();
    private Button aa_news, aa_contacts, aa_set;
    private Fragment fragme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        add2List();
        init();
    }

    private void init() {

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
        viewPager.setAdapter(fpa);
        viewPager.setCurrentItem(0);
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
            case R.id.aa_contacts:
                viewPager.setCurrentItem(1);
                break;
            case R.id.aa_news:
                viewPager.setCurrentItem(0);
                break;
            case R.id.aa_set:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
