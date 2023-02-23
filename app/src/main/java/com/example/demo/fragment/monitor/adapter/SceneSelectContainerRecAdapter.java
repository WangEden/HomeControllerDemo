package com.example.demo.fragment.monitor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.fragment.monitor.beans.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class SceneSelectContainerRecAdapter extends RecyclerView
        .Adapter<SceneSelectContainerRecAdapter.SceneSelectContainerRecHolder>{

    private View inflater;
    private final List<ItemBean> data;
    private OnItemClickListener mOnItemClickListener;

    public SceneSelectContainerRecAdapter() {
        data = new ArrayList<>();
        // 将要用的素材id存入data列表
        data.add(new ItemBean(R.drawable.ic_logo_sitting_room, R.string.scene_sitting_room, R.string.state_normal));
        data.add(new ItemBean(R.drawable.ic_logo_kitchen, R.string.scene_kitchen, R.string.state_normal));
        data.add(new ItemBean(R.drawable.ic_logo_bedroom, R.string.scene_bedroom, R.string.state_normal));
    }

    @NonNull
    @Override
    public SceneSelectContainerRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建一个Holder
        inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scene_selection, parent,false);
        SceneSelectContainerRecHolder sceneSelectContainerRecHolder = new SceneSelectContainerRecHolder(inflater);


        return sceneSelectContainerRecHolder;
    }

    // 将素材和容器进行绑定
    @Override
    public void onBindViewHolder(@NonNull SceneSelectContainerRecHolder holder, int position) {
        holder.bindData(data, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 编写回调接口
     * 1. 创建这个接口
     * 2. 定义接口内部的方法
     * 3. 提供设置接口的方法（其实是外部实现）
     * 4. 接口方法的调用
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        // 设置监听，即设置一个回调的接口
        this.mOnItemClickListener = listener;
    }

    public class SceneSelectContainerRecHolder extends RecyclerView.ViewHolder {

        // 用于存储查找到的item中的三个元素
        private final ImageView sceneSelectionLogo;
        private final TextView sceneSelectionName;
        private final TextView sceneSelectionState;
        private int mPosition;

        public SceneSelectContainerRecHolder(@NonNull View itemView) {
            super(itemView);
            // 查找item中的三个元素
            sceneSelectionLogo = itemView.findViewById(R.id.scene_selection_logo);
            sceneSelectionName = itemView.findViewById(R.id.scene_selection_name);
            sceneSelectionState = itemView.findViewById(R.id.scene_selection_state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 绑定监听点击事件的方法
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        // 绑定数据的方法
        public void bindData(List<ItemBean> data, int position) {
            this.mPosition = position;
            // 将素材写入item
            ItemBean itemBean = data.get(position);
            sceneSelectionLogo.setImageResource(itemBean.icon);
            sceneSelectionName.setText(itemBean.sceneName);
            sceneSelectionState.setText(itemBean.sceneState);
        }
    }
}
