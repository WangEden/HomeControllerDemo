package com.example.demo.fragment.monitor.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ScenePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> data;
    public ScenePagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> data) {
        super(fm, behavior);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
