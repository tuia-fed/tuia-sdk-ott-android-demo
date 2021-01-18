package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxTbScreen;

public class InsertOTTActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private Button btn;
    private FoxTbScreen tbScreen;

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTop;
    private TextView tvBottom;

    private static final int ANIMATION_DURATION = 300;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ott);
        btn = findViewById(R.id.bt_insert);
        tvLeft = findViewById(R.id.tv_insert_left);
        tvRight = findViewById(R.id.tv_insert_right);
        tvTop = findViewById(R.id.tv_insert_top);
        tvBottom = findViewById(R.id.tv_insert_bottom);

        slotId = getIntent().getIntExtra("slotId", 0);

        btn.setOnFocusChangeListener(this);
        tvLeft.setOnFocusChangeListener(this);
        tvRight.setOnFocusChangeListener(this);
        tvTop.setOnFocusChangeListener(this);
        tvBottom.setOnFocusChangeListener(this);

        initListener();

        // 初始化焦点位置
        btn.requestFocus();
    }

    private void initListener(){
        btn.setOnClickListener(v -> {
            // 弹窗广告绑定当前activity
            tbScreen = new FoxTbScreen(InsertOTTActivity.this);
            // 设置按返回键可关闭弹窗广告（option）
            tbScreen.setBackEnable(true);
            // 加载广告id
            tbScreen.loadAd(slotId, TAConfig.USER_ID);
        });

        tbScreen.setFoxNextLeftFocusView(tvLeft);
        tbScreen.setFoxNextRightFocusView(tvRight);
        tbScreen.setFoxNextUpFocusView(tvTop);
        tbScreen.setFoxNextDownFocusView(tvBottom);
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