package com.example.demo;



import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.base.BaseActivity;

public class BindDeviceActivity extends BaseActivity {

    private static final String TAG = "BindDeviceActivity";
    private int mode = 0;
    private EditText bindDeviceEdit;
    private TextView bindDeviceButtonState;
    private String deviceCode;

    @Override
    protected void initViews() {
        bindDeviceEdit = findViewById(R.id.bind_device_edit);
        bindDeviceButtonState = findViewById(R.id.bind_device_button_state);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_device;
    }

    // 设置切换页面动画
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.page_enter2, R.anim.page_close2);
    }

    public void backMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.page_enter2, R.anim.page_close2);
    }

    public void bindDevice(View view) {
        if (mode == 0) {
            if (bindDeviceEdit.getText().toString().isEmpty()) {
                Toast.makeText(this, "编号为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            bindDeviceEdit.setEnabled(false);
            deviceCode = bindDeviceEdit.getText().toString();
            Log.d(TAG, "bindDevice: " + deviceCode);
            bindDeviceButtonState.setText("解除绑定");
            mode = 1;
        } else if (mode == 1) {
            bindDeviceEdit.setEnabled(true);
            bindDeviceButtonState.setText("绑定");
            mode = 0;
        }
    }
}