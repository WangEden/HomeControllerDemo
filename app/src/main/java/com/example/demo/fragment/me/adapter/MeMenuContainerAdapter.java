package com.example.demo.fragment.me.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.fragment.monitor.beans.MeMenuItemBean;

import java.util.ArrayList;
import java.util.List;


public class MeMenuContainerAdapter extends RecyclerView
        .Adapter<MeMenuContainerAdapter.MeMenuContainerHolder>{

    private View inflater;
    private final List<MeMenuItemBean> data;
    private OnMenuItemClickListener mOnMenuItemClickListener;

    public MeMenuContainerAdapter() {
        // 收集用于构建item的素材
        data = new ArrayList<>();
        data.add(new MeMenuItemBean(R.drawable.ic_baseline_devices_24, "绑定设备", R.drawable.ic_underline));
        data.add(new MeMenuItemBean(R.drawable.ic_baseline_article_24, "意见反馈", R.drawable.ic_underline));
        data.add(new MeMenuItemBean(R.drawable.ic_baseline_settings_24, "通用设置", R.drawable.ic_underline));
        data.add(new MeMenuItemBean(R.drawable.ic_baseline_info_24, "关于我们", R.drawable.ic_underline));
        data.add(new MeMenuItemBean(R.drawable.ic_baseline_help_24, "帮助", R.drawable.ic_underline));
    }

    @NonNull
    @Override
    public MeMenuContainerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建Holder
        inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_me_menu, parent, false);
        MeMenuContainerHolder meMenuContainerHolder = new MeMenuContainerHolder(inflater);
        return meMenuContainerHolder;
    }

    // 将素材和容器绑定
    @Override
    public void onBindViewHolder(@NonNull MeMenuContainerHolder holder, int position) {
        holder.bindData(data, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // 设置一个接口并设置接口回调
    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    public class MeMenuContainerHolder extends RecyclerView.ViewHolder{

        // 存储item中的三个元素
        private final ImageView meMenuIcon;
        private final TextView meMenuDescription;
        private final ImageView meMenuUnderline;
        private int mPosition;

        public MeMenuContainerHolder(@NonNull View itemView) {
            super(itemView);
            meMenuIcon = itemView.findViewById(R.id.me_menu_icon);
            meMenuDescription = itemView.findViewById(R.id.me_menu_description);
            meMenuUnderline = itemView.findViewById(R.id.me_menu_underline);

            // 为给个项目设置点击事件监听
            itemView.setOnClickListener(v -> {
                if (mOnMenuItemClickListener != null)
                    mOnMenuItemClickListener.onMenuItemClick(mPosition);
            });

        }

        // 设置绑定数据的方法
        public void bindData(List<MeMenuItemBean> data, int position) {
            this.mPosition = position;
            // 将素材写入
            MeMenuItemBean meMenuItemBean = data.get(position);
            meMenuIcon.setImageResource(meMenuItemBean.iconId);
            meMenuDescription.setText(meMenuItemBean.description);
            if (position != data.size() - 1) {
                meMenuUnderline.setImageResource(meMenuItemBean.underlineId);
            }
        }
    }
}
