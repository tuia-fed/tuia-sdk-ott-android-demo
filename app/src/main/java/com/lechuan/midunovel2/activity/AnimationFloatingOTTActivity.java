package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.holder.FoxFloatingWebHolder;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationFloatingOTTActivity extends AppCompatActivity implements FoxFloatingWebHolder.FloatingWebAdLoadListener, View.OnFocusChangeListener {

    private int slotId;
    private static final int ANIMATION_DURATION = 300;

    private FoxFloatingWebHolder foxFloatingWebHolder;

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTop;
    private TextView tvBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_floating_ott);

        tvLeft = findViewById(R.id.tv_animation_floating_left);
        tvRight = findViewById(R.id.tv_animation_floating_right);
        tvTop = findViewById(R.id.tv_animation_floating_top);
        tvBottom = findViewById(R.id.tv_animation_floating_bottom);

        // 获取广告id
        slotId = getIntent().getIntExtra("slotId", 0);

        initView();
    }

    private void initView() {
        // 设置获焦监听事件
        tvLeft.setOnFocusChangeListener(this);
        tvRight.setOnFocusChangeListener(this);
        tvTop.setOnFocusChangeListener(this);
        tvBottom.setOnFocusChangeListener(this);

        // 创建悬浮升级holder
        foxFloatingWebHolder = FoxNativeAdHelper.getFoxFloatingWebHolder();
        // holder绑定当前activity
        foxFloatingWebHolder.setFloatingHost(this);
        // 设置holder信息
        foxFloatingWebHolder.setConfigInfo(TAConfig.APP_KEY, TAConfig.APP_SECRET);

        // 设置方向按键
        foxFloatingWebHolder.setFoxNextLeftFocusView(tvLeft);
        foxFloatingWebHolder.setFoxNextRightFocusView(tvRight);
        foxFloatingWebHolder.setFoxNextUpFocusView(tvTop);
        foxFloatingWebHolder.setFoxNextDownFocusView(tvBottom);

        // 设置holder属性
        String left = "50";
        String right = "100";
        String top = "100";
        String bottom = "50";

        // holder加载广告id
        foxFloatingWebHolder.loadFloatingWebAd(slotId, TAConfig.USER_ID, left, top, right, bottom, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsumed = false;
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (foxFloatingWebHolder != null) {
                // 获取holder是否允许返回键退出
                isConsumed = foxFloatingWebHolder.goBack();
            }
        }
        if (!isConsumed) {
            return super.onKeyDown(keyCode, event);
        }

        return isConsumed;
    }

    @Override
    protected void onDestroy() {
        // 销毁holder
        if (foxFloatingWebHolder != null){
            foxFloatingWebHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onLoadSuccess() {
        Toast.makeText(this, getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed(int i, String s) {
        Toast.makeText(this, getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdMessage(String s) {
        Toast.makeText(this, "ad msg = " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            TADemoAnimationUtil.enlarge(v, 1.2F, ANIMATION_DURATION);
        }else {
            TADemoAnimationUtil.shrink(v, 1.2F, ANIMATION_DURATION);
        }
    }
}