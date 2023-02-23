package com.example.demo.fragment.monitor.scene;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.R;
import com.example.demo.base.BaseFragment;
import com.example.demo.fragment.monitor.adapter.ScenePagerAdapter;
import com.example.demo.fragment.monitor.scene.bedroom.AirConditionBedroomFragment;
import com.example.demo.fragment.monitor.scene.bedroom.HumidityBedroomFragment;
import com.example.demo.fragment.monitor.scene.bedroom.TemperatureBedroomFragment;
import com.example.demo.fragment.monitor.scene.kitchen.AirConditionKitchenFragment;
import com.example.demo.fragment.monitor.scene.kitchen.HumidityKitchenFragment;
import com.example.demo.fragment.monitor.scene.kitchen.TemperatureKitchenFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BedroomFragment extends BaseFragment {


    @Override
    protected void initViews() {
        // 获取其页面上的TabLayout控件
        TabLayout tlSceneBedroom = find(R.id.tl_scene_bedroom);
        // 获取其页面上的ViewPager控件
        ViewPager vpSceneBedroom = find(R.id.vp_scene_bedroom);
        // 将TabLayout和ViewPager绑定
        tlSceneBedroom.setupWithViewPager(vpSceneBedroom);


        List<Fragment> sensors = new ArrayList<>();
        sensors.add(new TemperatureBedroomFragment());
        sensors.add(new HumidityBedroomFragment());
        sensors.add(new AirConditionBedroomFragment());


        // 创建一个pagerAdapter
        ScenePagerAdapter pagerAdapter = new ScenePagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, sensors);


        // 为这个viewpager安装适配器
        vpSceneBedroom.setAdapter(pagerAdapter);

        // 为页面设置间距
        vpSceneBedroom.setPageMargin(20);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("_温度_");
        tabNames.add("_湿度_");
        tabNames.add("_空气质量_");

        for (int i = 0; i < tlSceneBedroom.getTabCount(); i++) {
            tlSceneBedroom.getTabAt(i).setText(tabNames.get(i));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bedroom;
    }
}
