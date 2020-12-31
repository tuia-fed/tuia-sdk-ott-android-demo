package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.view.FoxWallView;

import androidx.appcompat.app.AppCompatActivity;

public class FloatingOTTActivity extends AppCompatActivity {

    private LinearLayout llContainer;
    private FoxWallView wvCircle;
    private FoxWallView wvSquare;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_ott);

        llContainer = findViewById(R.id.ll_floating_container);
        wvCircle = findViewById(R.id.wv_floating_circle);

        slotId = getIntent().getIntExtra("slotId", 0);

        initView();
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

        // 加载广告id
        wvCircle.loadAd(slotId);
        wvSquare.loadAd(slotId, TAConfig.USER_ID);
    }
}