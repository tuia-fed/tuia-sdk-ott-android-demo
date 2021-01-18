package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxWallView;

import androidx.appcompat.app.AppCompatActivity;

public class FloatingOTTActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private LinearLayout llContainer;
    private FoxWallView wvCircle;
    private FoxWallView wvSquare;

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTop;
    private TextView tvBottom;

    private static final int ANIMATION_DURATION = 300;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_ott);

        llContainer = findViewById(R.id.ll_floating_container);
        wvCircle = findViewById(R.id.wv_floating_circle);
        tvLeft = findViewById(R.id.tv_floating_left);
        tvRight = findViewById(R.id.tv_floating_right);
        tvTop = findViewById(R.id.tv_floating_top);
        tvBottom = findViewById(R.id.tv_floating_bottom);

        // 获取广告id
        slotId = getIntent().getIntExtra("slotId", 0);

        initView();

        tvLeft.setOnFocusChangeListener(this);
        tvRight.setOnFocusChangeListener(this);
        tvTop.setOnFocusChangeListener(this);
        tvBottom.setOnFocusChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        // 销毁view
        super.onDestroy();

        if (wvCircle != null){
            wvCircle.destroy();
        }

        if (wvSquare != null){
            wvSquare.destroy();
        }
    }

    private void initView() {
        // 动态添加广告
        wvSquare = new FoxWallView(this, 0);
        wvSquare.setFoxWidth(200);
        wvSquare.setFoxHeight(200);
        llContainer.addView(wvSquare);

        // 设置按键监听事件
        wvCircle.setFoxNextUpFocusView(tvTop);
        wvCircle.setFoxNextLeftFocusView(tvLeft);
        wvSquare.setFoxNextLeftFocusView(tvLeft);
        wvCircle.setFoxNextRightFocusView(tvRight);
        wvSquare.setFoxNextRightFocusView(tvRight);
        wvSquare.setFoxNextDownFocusView(tvBottom);

        // 加载广告id
        wvCircle.loadAd(slotId);
        wvSquare.loadAd(slotId, TAConfig.USER_ID);
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