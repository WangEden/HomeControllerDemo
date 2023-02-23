package com.example.demo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    protected View contentView;
    protected FragmentActivity fragmentActivity;

    protected abstract void initViews();

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getLayoutId(), container, false);
        fragmentActivity = getActivity();
        initViews();
        return contentView;
    }

    // 添加一个findById方法
    protected <T extends View> T find(@IdRes int id) {
        return contentView.findViewById(id);
    }


}
