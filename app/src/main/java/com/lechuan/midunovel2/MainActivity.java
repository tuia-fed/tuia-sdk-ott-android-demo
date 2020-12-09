package com.lechuan.midunovel2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lechuan.midunovel2.activity.SplashActivity;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private Button btSplash;
    private Button btCrossBanner;
    private Button btInfoStream;
    private Button btInsertInfo;
    private Button btBanner;
    private Button btFloating;
    private Button btTextLink;
    private Button btNativeInsertInfo1;
    private Button btNativeInsertInfo2;
    private Button btAnimationFloating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        // 初始化焦点落焦
        btSplash.post(() -> btSplash.requestFocus());
    }

    private void initView(){
        btSplash = findViewById(R.id.bt_main_splash);
        btCrossBanner = findViewById(R.id.bt_main_cross_banner);
        btInfoStream = findViewById(R.id.bt_main_info_stream);
        btInsertInfo = findViewById(R.id.bt_main_insert_info);
        btBanner = findViewById(R.id.bt_main_banner);
        btFloating = findViewById(R.id.bt_main_floating);
        btTextLink = findViewById(R.id.bt_main_text_link);
        btNativeInsertInfo1 = findViewById(R.id.bt_main_native_insert1);
        btNativeInsertInfo2 = findViewById(R.id.bt_main_native_insert2);
        btAnimationFloating = findViewById(R.id.bt_main_animation_floating);
    }

    private void initListener(){
        btSplash.setOnClickListener(this);
        btCrossBanner.setOnClickListener(this);
        btInsertInfo.setOnClickListener(this);
        btInfoStream.setOnClickListener(this);
        btBanner.setOnClickListener(this);
        btFloating.setOnClickListener(this);
        btTextLink.setOnClickListener(this);
        btNativeInsertInfo1.setOnClickListener(this);
        btNativeInsertInfo2.setOnClickListener(this);
        btAnimationFloating.setOnClickListener(this);

        btSplash.setOnFocusChangeListener(this);
        btCrossBanner.setOnFocusChangeListener(this);
        btInsertInfo.setOnFocusChangeListener(this);
        btInfoStream.setOnFocusChangeListener(this);
        btBanner.setOnFocusChangeListener(this);
        btFloating.setOnFocusChangeListener(this);
        btTextLink.setOnFocusChangeListener(this);
        btNativeInsertInfo1.setOnFocusChangeListener(this);
        btNativeInsertInfo2.setOnFocusChangeListener(this);
        btAnimationFloating.setOnFocusChangeListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            // 开屏广告
            case R.id.bt_main_splash:
                intent = new Intent(this, SplashActivity.class);
                intent.putExtra("slotId", TAConfig.SPLASH_ID);
                break;
            case R.id.bt_main_cross_banner:

                break;
            case R.id.bt_main_info_stream:

                break;
            case R.id.bt_main_insert_info:

                break;
            case R.id.bt_main_banner:

                break;
            case R.id.bt_main_floating:

                break;
            case R.id.bt_main_text_link:

                break;
            case R.id.bt_main_native_insert1:

                break;
            case R.id.bt_main_native_insert2:

                break;
            case R.id.bt_main_animation_floating:

                break;
            default:
                break;
        }

        startActivity(intent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            TADemoAnimationUtil.enlarge(v, 1.2f, 200);
        }else {
            TADemoAnimationUtil.shrink(v, 1.2f, 200);
        }
    }
}