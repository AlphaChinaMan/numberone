package com.example.socialapp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.socialapp.R;

/**
 * Created by 陈梦轩 on 2017/3/27.
 */

public class TestDialog extends ProgressDialog{
    public TestDialog(Context context) {
        super(context);
    }

    public TestDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIndeterminate(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.login_dialog);
        WindowManager.LayoutParams arr=getWindow().getAttributes();
        arr.width=WindowManager.LayoutParams.WRAP_CONTENT;
        arr.height=WindowManager.LayoutParams.WRAP_CONTENT;
        arr.alpha=0.8f;
        getWindow().setAttributes(arr);

    }
}
