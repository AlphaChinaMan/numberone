package com.example.socialapp.img;

import com.example.socialapp.R;

import java.util.HashMap;


/**
 * Created by 陈梦轩 on 2017/4/26.
 */

public class ImageTest {
    static HashMap<String, Integer> img = new HashMap<>();

    public static void setImg() {
        img.put("[jj]", R.mipmap.ic_launcher);
    }

    public static int getImg(String key) {
        return img.get(key);
    }
}
