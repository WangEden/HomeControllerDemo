package com.example.demo;


import android.content.Intent;
import android.view.View;

import com.example.demo.base.BaseActivity;

public class HelpActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    // 设置切换页面动画
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.page_enter2, R.anim.page_close2);
    }

    public void backMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.page_enter2, R.anim.page_close2);
    }
}