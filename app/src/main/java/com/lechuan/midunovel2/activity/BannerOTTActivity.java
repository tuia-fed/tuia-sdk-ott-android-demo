package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lechuan.midunovel2.R;
import com.mediamain.android.view.FoxWallView;

public class BannerOTTActivity extends AppCompatActivity {

    private FoxWallView wv;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ott);

        slotId = getIntent().getIntExtra("slotId", 0);

        wv = findViewById(R.id.wv_banner);

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
}