package com.example.demo.fragment.monitor;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.base.BaseFragment;
import com.example.demo.fragment.monitor.adapter.SceneSelectContainerRecAdapter;
import com.example.demo.fragment.monitor.scene.BedroomFragment;
import com.example.demo.fragment.monitor.scene.KitchenFragment;
import com.example.demo.fragment.monitor.scene.SittingRoomFragment;

import java.util.ArrayList;
import java.util.List;

public class MonitorFragment extends BaseFragment {

    private static final String TAG = "MonitorFragment";
    private SceneSelectContainerRecAdapter sceneSelectContainerRecAdapter;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private List<Fragment> scene;

    @Override
    protected void initViews() {
        Log.d(TAG, "MonitorFragment -> initView -> Begin");
        {
            // 查找第二个页面——监测页上的RecyclerView控件
            RecyclerView tlSceneSelectionContainer = find(R.id.tl_scene_selection_container);
            // 获取布局管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
            // 设置布局管理器
            tlSceneSelectionContainer.setLayoutManager(layoutManager);
            // 获取RecyclerView的适配器
            sceneSelectContainerRecAdapter = new SceneSelectContainerRecAdapter();
            // 设置适配器
            tlSceneSelectionContainer.setAdapter(sceneSelectContainerRecAdapter);
            // 初始化事件
            initListener();
        }


        /*
             创建一个存储那三个场景Fragment的列表
             包括 SittingRoomFragment, KitchenFragment, BedroomFragment
         */
        scene = new ArrayList<>();
        scene.add(new SittingRoomFragment());
        scene.add(new KitchenFragment());
        scene.add(new BedroomFragment());

        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.vp_scene_container, scene.get(0));
        fragmentTransaction.add(R.id.vp_scene_container, scene.get(1));
        fragmentTransaction.add(R.id.vp_scene_container, scene.get(2));
        fragmentTransaction.hide(scene.get(1));
        fragmentTransaction.hide(scene.get(2));
        fragmentTransaction.commit();

        Log.d(TAG, "MonitorFragment -> initView -> Over");
    }

    private void initListener() {
        sceneSelectContainerRecAdapter.setOnItemClickListener(new SceneSelectContainerRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 在这里处理点击事件
                // Toast.makeText(getActivity(), position + "被点击了", Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.show(scene.get(position));
                for (int i = 0; i < scene.size(); i++) {
                    if (i != position) {
                        fragmentTransaction.hide(scene.get(i));
                    }
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monitor;
    }
}
