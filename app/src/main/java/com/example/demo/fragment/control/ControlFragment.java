package com.example.demo.fragment.control;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo.MyApplication;
import com.example.demo.R;
import com.example.demo.base.BaseFragment;
import com.example.demo.diy_view.CustomSpinner;
import com.example.demo.diy_view.GradientCircleProgress;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ControlFragment extends BaseFragment {

    private static final String TAG = "ControlFragment";

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swPower;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swBurner;

    private Timer mTimer; //计时器，每1秒执行一次任务
    private MyTimerTask mTimerTask; //计时任务，判断是否未操作时间到达3s
    private long mLastActionTime; //上一次操作时间

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Handler mHandler;
    private ImageView imgControlSceneSelectorArrow;
    private final int BEDROOM_SCENE = 0;
    private final int KITCHEN_SCENE = 1;
    private final int SITTING_ROOM_SCENE = 2;
    private GradientCircleProgress gradientCircleProgress;
    private TextView tvControlCurrentTemperature;
    private int currentTemperature;
    private Boolean popWindowState = false;


    @Override
    protected void initViews() {
        {
            // 找到那两个开关
            swPower = find(R.id.sw_power);
            swBurner = find(R.id.sw_burner);
            swPower.setChecked(true);
            swBurner.setChecked(true);
            // 查找进度条
            gradientCircleProgress = find(R.id.circleProgress);
            // 查找温度标签
            tvControlCurrentTemperature = find(R.id.tv_control_current_temperature);
            // 查找箭头
            imgControlSceneSelectorArrow = (ImageView) find(R.id.img_control_scene_selector);
        }

        {
            CustomSpinner spinner = find(R.id.spinner_control_scene_selector);
            // 设置数据源
            String[] mScenes = {"卧室", "厨房", "客厅"};
            // 设置初始状态下的spinnerItem
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MyApplication.getInstance(), R.layout.item_spinner_scene_selector, mScenes);
            // 设置下拉菜单中的spinnerItem
            spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_scene_selector);
            spinner.setAdapter(spinnerAdapter);

            spinner.setBackgroundColor(0x000000);
            spinner.setPopupBackgroundResource(R.drawable.round_shape);
            spinner.setDropDownVerticalOffset(100);


            // 监听展开事件，决定箭头是否旋转
            spinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
                @Override
                public void onSpinnerOpened() {
                    imgControlSceneSelectorArrow.animate().setDuration(300).rotation(90).start();
                }

                @Override
                public void onSpinnerClosed() {
                    imgControlSceneSelectorArrow.animate().setDuration(300).rotation(0).start();
                }
            });

            // 监听被选中的选项
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case BEDROOM_SCENE:
                            // TODO
                            currentTemperature = 16;
                            break;
                        case KITCHEN_SCENE:
                            // TODO
                            currentTemperature = 28;
                            break;
                        case SITTING_ROOM_SCENE:
                            // TODO
                            currentTemperature = 22;
                            break;
                    }
                    // 展示温度数据(可视化数据)
                    tvControlCurrentTemperature.setText(String.valueOf(currentTemperature));
                    int progress = (int) ((float) currentTemperature / 40.0 * 100);
                    gradientCircleProgress.startAnimProgress(progress, 400);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO

                }
            });

        }

//        // 找到场景切换的ViewGroup(View直接作为按钮)
//        View controlSceneSelector = find(R.id.control_scene_selector);
//        controlSceneSelector.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        // =====================================
        //开始计时
//        startTimer();

        // 处理子线程发送来的消息
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    // 让屏幕变暗
                    case 0:
                        float f = Float.parseFloat(msg.obj.toString());
                        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
                        layoutParams.alpha = f;
                        getActivity().getWindow().setAttributes(layoutParams);
                        Log.d(TAG, "handleMessage: " + f);
                        break;
                }
            }
        };

        // =====================================


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_control;
    }

    // ======================================================================= //
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Looper.prepare();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url("47.106.205.72:8080?key=0")
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(), "服务器请求失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    popWindowState = true;
                }
            });
            if(popWindowState){
                View view = find(R.id.tv_control_state);
                View popup_view = getLayoutInflater().inflate(R.layout.fragment_pop, null);

                PopupWindow window = new PopupWindow(popup_view, ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);

                window.showAsDropDown(view);

                // 准备消息
                Message message = new Message();
                message.what = 0;
                message.obj = String.valueOf(0.618);
                mHandler.sendMessage(message);

                Button btn1 = popup_view.findViewById(R.id.btn_control_turn_off_power);
                Button btn2 = popup_view.findViewById(R.id.btn_control_pop_close);
                if (btn1 != null) {
                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swPower.setChecked(false);
                            swBurner.setChecked(false);
                            window.dismiss();
                            Message message = new Message();
                            message.what = 0;
                            message.obj = String.valueOf(1.0);
                            mHandler.sendMessage(message);
                        }
                    });
                }
                if (btn2 != null) {
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            window.dismiss();
                            Message message = new Message();
                            message.what = 0;
                            message.obj = String.valueOf(1.0);
                            mHandler.sendMessage(message);
                        }
                    });
                }
            }
            // 停止计时
            stopTimer();
            popWindowState = false;
            Looper.loop();
        }
    }

    //开始计时
    private void startTimer() {
        mTimer = new Timer(true);
        mTimerTask = new MyTimerTask();
        mTimer.schedule(mTimerTask, 6000, 1000); // 3s后执行
        // 初始化上次操作时间为登录成功的时间
        mLastActionTime = System.currentTimeMillis();
    }


    // 停止计时任务
    private void stopTimer() {
        mTimer.cancel();
    }

    // ======================================================================= //


}
