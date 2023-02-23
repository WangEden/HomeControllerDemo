package com.example.demo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.demo.base.BaseActivity;
import com.example.demo.fragment.control.ControlFragment;
import com.example.demo.fragment.me.MeFragment;
import com.example.demo.fragment.monitor.MonitorFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity implements NavigationBarView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Fragment[] fragments;
    private int lastFragmentIndex = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        // 获取导航栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 为导航栏的项目添加点击事件
        bottomNavigationView.setOnItemSelectedListener(this);
        // 实例化要添加的Fragment
        fragments = new Fragment[]{
                new ControlFragment(),
                new MonitorFragment(),
                new MeFragment()
        };
        // 获取fragment的管理器
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame, fragments[0])
                .commit();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 为导航栏上的item添加选中效果
        item.setChecked(true);
        // 更换Fragment
        switch (item.getItemId()) {
            case R.id.bottom_ctrl:
                switchFragment(0);
                break;
            case R.id.bottom_monitor:
                switchFragment(1);
                break;
            case R.id.bottom_me:
                switchFragment(2);
                break;

        }
        return false;
    }

    /**
     * isAdded() -> boolean
     * 如果Fragment被添加到了Activity上，则 -> true， 否则 -> false
     */
    private void switchFragment(int to) {
        if (lastFragmentIndex == to) {
            return;
        }
        // 切换过程的实现
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (!fragments[to].isAdded()) {
            fragmentTransaction.add(R.id.main_frame, fragments[to]);
        }
        else {
            fragmentTransaction.show(fragments[to]);
        }
        // 隐藏上一个Fragment
        fragmentTransaction.hide(fragments[lastFragmentIndex])
                .commitAllowingStateLoss();
        lastFragmentIndex = to;
    }



}