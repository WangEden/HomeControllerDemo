package com.example.demo.fragment.monitor.scene;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.demo.R;
import com.example.demo.base.BaseFragment;
import com.example.demo.fragment.monitor.adapter.ScenePagerAdapter;
import com.example.demo.fragment.monitor.scene.sitting_room.AirConditionFragment;
import com.example.demo.fragment.monitor.scene.sitting_room.HumidityFragment;
import com.example.demo.fragment.monitor.scene.sitting_room.TemperatureFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SittingRoomFragment extends BaseFragment {

    private static final String TAG = "MonitorFragment";

    @Override
    protected void initViews() {
        // 获取其页面上的TabLayout控件
        TabLayout tlSceneSittingRoom = find(R.id.tl_scene_sitting_room);
        // 获取其页面上的ViewPager控件
        ViewPager vpSceneSittingRoom = find(R.id.vp_scene_sitting_room);
        // 将TabLayout和ViewPager绑定
        tlSceneSittingRoom.setupWithViewPager(vpSceneSittingRoom);


        List<Fragment> sensors = new ArrayList<>();
        sensors.add(new TemperatureFragment());
        sensors.add(new HumidityFragment());
        sensors.add(new AirConditionFragment());


        // 创建一个pagerAdapter
        ScenePagerAdapter pagerAdapter = new ScenePagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, sensors);


        // 为这个viewpager安装适配器
        vpSceneSittingRoom.setAdapter(pagerAdapter);

        // 为页面设置间距
        vpSceneSittingRoom.setPageMargin(20);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("温度");
        tabNames.add("湿度");
        tabNames.add("空气质量");

        for (int i = 0; i < tlSceneSittingRoom.getTabCount(); i++) {
            tlSceneSittingRoom.getTabAt(i).setText(tabNames.get(i));
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sitting_room;
    }
}