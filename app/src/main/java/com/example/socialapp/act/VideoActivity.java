package com.example.socialapp.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.socialapp.R;

/**
 * Created by 陈梦轩 on 2017/4/19.
 */

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private String path, leftpath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);
        getData();
        initView();
    }

    public void getData() {
        path = getIntent().getStringExtra("path");
        leftpath = getIntent().getStringExtra("leftPath");
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.video_view);
        if (TextUtils.isEmpty(path)) {
            videoView.setVideoPath(leftpath);
        } else {
            videoView.setVideoPath(path);
        }
        videoView.start();
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
    }
}
