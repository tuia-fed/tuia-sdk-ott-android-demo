package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.view.FoxStreamerView;
import com.mediamain.android.view.interfaces.FoxListener;

public class CrossBannerOTTActivity extends AppCompatActivity {

    private LinearLayout llContainer;
    private FoxStreamerView foxStreamerView1;
    private FoxStreamerView foxStreamerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_banner_ott);

        llContainer = findViewById(R.id.ll_cross_banner_container);
        foxStreamerView1 = findViewById(R.id.sv_cross_banner);
        initStreamView();

        int slotId = getIntent().getIntExtra("slotId", 0);
        foxStreamerView1.loadAd(slotId);
        foxStreamerView2.loadAd(slotId, TAConfig.USER_ID);
    }

    private void initStreamView(){
        if (foxStreamerView1 == null){
            return;
        }

        foxStreamerView1.setAdListener(new FoxListener() {
            @Override
            public void onReceiveAd() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 Receive ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 receive failed, message=" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadFailed() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 load failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCloseClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 click close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 click ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExposure() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 ad exposure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdActivityClose(String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 activity close", Toast.LENGTH_SHORT).show();
            }
        });

        foxStreamerView2 = new FoxStreamerView(this, null);
        foxStreamerView2.setFoxWidth(1000);
        foxStreamerView2.setFoxHeight(500);
        llContainer.addView(foxStreamerView2);

        foxStreamerView2.setAdListener(new FoxListener() {
            @Override
            public void onReceiveAd() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 Receive ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 receive failed, message=" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadFailed() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 load failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCloseClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 click close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 click ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExposure() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 ad exposure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdActivityClose(String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 activity close", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 及时销毁控件
        if (foxStreamerView1 != null){
            foxStreamerView1.destroy();
        }
        if (foxStreamerView2 != null){
            foxStreamerView2.destroy();
        }

        super.onDestroy();
    }
}