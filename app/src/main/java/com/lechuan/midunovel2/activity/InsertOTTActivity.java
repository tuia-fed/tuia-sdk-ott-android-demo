package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxTbScreen;

public class InsertOTTActivity extends AppCompatActivity {

    private Button btn;
    private FoxTbScreen tbScreen;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ott);
        btn = findViewById(R.id.bt_insert);

        slotId = getIntent().getIntExtra("slotId", 0);

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

        btn.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                TADemoAnimationUtil.enlarge(v, 1.2F, 300);
            }else {
                TADemoAnimationUtil.shrink(v, 1.2F, 300);
            }
        });
    }
}