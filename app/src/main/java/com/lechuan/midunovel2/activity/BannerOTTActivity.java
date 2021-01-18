package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxWallView;

public class BannerOTTActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private FoxWallView wv;

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTop;
    private TextView tvBottom;

    private int slotId;
    private static final int ANIMATION_DURATION = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ott);

        slotId = getIntent().getIntExtra("slotId", 0);

        wv = findViewById(R.id.wv_banner);
        tvLeft = findViewById(R.id.tv_banner_left);
        tvRight = findViewById(R.id.tv_banner_right);
        tvTop = findViewById(R.id.tv_banner_top);
        tvBottom = findViewById(R.id.tv_banner_bottom);

        // 设置获焦监听事件
        tvLeft.setOnFocusChangeListener(this);
        tvRight.setOnFocusChangeListener(this);
        tvTop.setOnFocusChangeListener(this);
        tvBottom.setOnFocusChangeListener(this);

        // 设置方向按键
        wv.setFoxNextLeftFocusView(tvLeft);
        wv.setFoxNextRightFocusView(tvRight);
        wv.setFoxNextUpFocusView(tvTop);
        wv.setFoxNextDownFocusView(tvBottom);

        // 加载广告id
        wv.loadAd(slotId);
        // 初始化焦点位置
        wv.requestFocus();
    }

    @Override
    protected void onDestroy() {
        // 销毁view
        if (wv != null){
            wv.destroy();
        }

        super.onDestroy();
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