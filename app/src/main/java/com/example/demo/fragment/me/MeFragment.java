package com.example.demo.fragment.me;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.AboutActivity;
import com.example.demo.BindDeviceActivity;
import com.example.demo.FeedbackActivity;
import com.example.demo.HelpActivity;
import com.example.demo.MyApplication;
import com.example.demo.R;
import com.example.demo.SettingActivity;
import com.example.demo.base.BaseFragment;
import com.example.demo.fragment.me.adapter.MeMenuContainerAdapter;
import com.example.demo.utils.OnDoubleClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MeFragment extends BaseFragment {

    // 设置选取照片时的状态
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    private ImageView imgMeUserHeadImg;
    private MeMenuContainerAdapter meMenuContainerAdapter;
    private Uri imageUri;
    private TextView tvMeUsername;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initViews() {
        {
            tvMeUsername = find(R.id.tv_me_username);
            tvMeUsername.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick() {
//                    Toast.makeText(fragmentActivity, "username", Toast.LENGTH_SHORT).show();
                    View popup_view = getLayoutInflater().inflate(R.layout.fragment_pop_setting_username, null);
                    PopupWindow window = new PopupWindow(popup_view, ViewGroup.LayoutParams.WRAP_CONTENT
                            , ViewGroup.LayoutParams.WRAP_CONTENT);
                    // 点击弹窗外则消失
                    window.setOutsideTouchable(true);
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.showAsDropDown(tvMeUsername);

                    EditText editMeUsername = LayoutInflater.from(getContext())
                            .inflate(R.layout.fragment_pop_setting_username, null)
                            .findViewById(R.id.edit_me_username);

                    editMeUsername.setFocusable(true);

                    editMeUsername.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            tvMeUsername.setText(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }));
        }
        {
            imgMeUserHeadImg = find(R.id.img_me_user_head_img);
            // 为图片设置点击事件
            imgMeUserHeadImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = find(R.id.img_me_user_head_img);
                    View popup_view = getLayoutInflater().inflate(R.layout.fragment_pop_setting_head, null);

                    PopupWindow window = new PopupWindow(popup_view, ViewGroup.LayoutParams.WRAP_CONTENT
                            , ViewGroup.LayoutParams.WRAP_CONTENT);
                    // 点击弹窗外则消失
                    window.setOutsideTouchable(true);
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.showAsDropDown(view);

                    View meHeadOption1 = popup_view.findViewById(R.id.me_head_option1);
                    View meHeadOption2 = popup_view.findViewById(R.id.me_head_option2);

                    // 从相册获取点击事件
                    meHeadOption1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            choosePhoto(v);
                        }
                    });

                    // 拍照获取
                    meHeadOption2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            takePhoto(v);
                        }
                    });
                }
            });
        }
        {
            // 查找布局
            RecyclerView rvMeMenuContainer = find(R.id.rv_me_menu_container);
            // 获取RecyclerView页面管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            // 设置管理器
            rvMeMenuContainer.setLayoutManager(layoutManager);
            // 获取适配器
            meMenuContainerAdapter = new MeMenuContainerAdapter();
            // 设置适配器
            rvMeMenuContainer.setAdapter(meMenuContainerAdapter);
            //
            initListener();

        }
    }


    private void initListener() {
        meMenuContainerAdapter.setOnMenuItemClickListener(new MeMenuContainerAdapter.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                // TODO
                // 在这里处理点击事件
//                 Toast.makeText(getActivity(), position + "被点击了", Toast.LENGTH_SHORT).show();
                // 跳转页面
                switch (position) {
                    case 0:
                        startActivity(new Intent(fragmentActivity, BindDeviceActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(fragmentActivity, FeedbackActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(fragmentActivity, SettingActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(fragmentActivity, AboutActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(fragmentActivity, HelpActivity.class));
                        break;
                }
                fragmentActivity.overridePendingTransition(R.anim.page_enter, R.anim.page_close);


            }
        });
    }

    private void choosePhoto(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
//        doTakePhoto();
    }

    private void takePhoto(View v) {
        if (ContextCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 若以获取拍照权限，则拍照
            doTakePhoto();
        } else {
            // 申请权限
            ActivityCompat.requestPermissions(fragmentActivity, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    // 接受申请的权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doTakePhoto();
            } else {
                Toast.makeText(fragmentActivity, "摄像头权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doTakePhoto() {
        // 获取文件路径
        File imageTemp = new File(fragmentActivity.getExternalCacheDir(), "imageOut.jpeg");
        if (imageTemp.exists())
            imageTemp.delete();
        try {
            imageTemp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 根据安卓版本以不同方式处理文件
        if (Build.VERSION.SDK_INT > 24) {
            // 内容提供者
            imageUri = FileProvider.getUriForFile(fragmentActivity, "com.example.demo.fileProvider", imageTemp);
        } else {
            imageUri = Uri.fromFile(imageTemp);
        }

        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == -1) {
                // 获取拍摄的照片
                try {
                    // 该图片文件的字节流
                    InputStream inputStream = fragmentActivity.getContentResolver().openInputStream(imageUri);
                    // 解析字节流
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    // 裁剪图片为圆形
                    RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    // 设置抗锯齿
                    roundedBitmap.setAntiAlias(true);
                    roundedBitmap.setCornerRadius((float) (Math.max(bitmap.getWidth() / 2, bitmap.getHeight() / 2) ));
                    // 设置图片
                    imgMeUserHeadImg.setImageDrawable(roundedBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }
}
