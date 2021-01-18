package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxStreamerView;
import com.mediamain.android.view.interfaces.FoxListener;

public class CrossBannerOTTActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private LinearLayout llContainer;
    private FoxStreamerView foxStreamerView1;
    private FoxStreamerView foxStreamerView2;

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTop;
    private TextView tvBottom;

    private static final int ANIMATION_DURATION = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_banner_ott);

        llContainer = findViewById(R.id.ll_cross_banner_container);
        foxStreamerView1 = findViewById(R.id.sv_cross_banner);
        tvTop = findViewById(R.id.tv_cross_banner_top);
        tvLeft = findViewById(R.id.tv_cross_banner_left);
        tvRight = findViewById(R.id.tv_cross_banner_right);
        tvBottom = findViewById(R.id.tv_cross_banner_bottom);
        initStreamView();

        // 设置获焦监听事件
        tvLeft.setOnFocusChangeListener(this);
        tvRight.setOnFocusChangeListener(this);
        tvTop.setOnFocusChangeListener(this);
        tvBottom.setOnFocusChangeListener(this);

        // 设置方向按键
        foxStreamerView1.setFoxNextUpFocusView(tvTop);
        foxStreamerView1.setFoxNextLeftFocusView(tvLeft);
        foxStreamerView2.setFoxNextLeftFocusView(tvLeft);
        foxStreamerView1.setFoxNextRightFocusView(tvRight);
        foxStreamerView2.setFoxNextRightFocusView(tvRight);
        foxStreamerView2.setFoxNextDownFocusView(tvBottom);

        int slotId = getIntent().getIntExtra("slotId", 0);
        // 加载广告ID
        foxStreamerView1.loadAd(slotId);
        foxStreamerView2.loadAd(slotId, TAConfig.USER_ID);
    }

    private void initStreamView(){
        if (foxStreamerView1 == null){
            return;
        }

        // 信息流view添加listener
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

        // 动态添加广告view
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            TADemoAnimationUtil.enlarge(v, 1.2F, ANIMATION_DURATION);
        }else {
            TADemoAnimationUtil.shrink(v, 1.2F, ANIMATION_DURATION);
        }
    }
}