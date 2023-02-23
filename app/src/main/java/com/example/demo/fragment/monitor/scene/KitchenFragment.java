package com.example.demo.fragment.monitor.scene;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.R;
import com.example.demo.base.BaseFragment;
import com.example.demo.fragment.monitor.adapter.ScenePagerAdapter;
import com.example.demo.fragment.monitor.scene.kitchen.AirConditionKitchenFragment;
import com.example.demo.fragment.monitor.scene.kitchen.HumidityKitchenFragment;
import com.example.demo.fragment.monitor.scene.kitchen.TemperatureKitchenFragment;
import com.example.demo.fragment.monitor.scene.sitting_room.AirConditionFragment;
import com.example.demo.fragment.monitor.scene.sitting_room.HumidityFragment;
import com.example.demo.fragment.monitor.scene.sitting_room.TemperatureFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class KitchenFragment extends BaseFragment {


    @Override
    protected void initViews() {
        // 获取其页面上的TabLayout控件
        TabLayout tlSceneKitchen = find(R.id.tl_scene_kitchen);
        // 获取其页面上的ViewPager控件
        ViewPager vpSceneKitchen = find(R.id.vp_scene_kitchen);
        // 将TabLayout和ViewPager绑定
        tlSceneKitchen.setupWithViewPager(vpSceneKitchen);


        List<Fragment> sensors = new ArrayList<>();
        sensors.add(new TemperatureKitchenFragment());
        sensors.add(new HumidityKitchenFragment());
        sensors.add(new AirConditionKitchenFragment());


        // 创建一个pagerAdapter
        ScenePagerAdapter pagerAdapter = new ScenePagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, sensors);


        // 为这个viewpager安装适配器
        vpSceneKitchen.setAdapter(pagerAdapter);

        // 为页面设置间距
        vpSceneKitchen.setPageMargin(20);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("温度_");
        tabNames.add("湿度_");
        tabNames.add("空气质量_");

        for (int i = 0; i < tlSceneKitchen.getTabCount(); i++) {
            tlSceneKitchen.getTabAt(i).setText(tabNames.get(i));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitchen;
    }
}