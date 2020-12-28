package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.holder.FoxNativeSplashHolder;
import com.mediamain.android.view.holder.FoxSplashAd;

import androidx.appcompat.app.AppCompatActivity;

public class SplashOTTActivity extends AppCompatActivity implements FoxNativeSplashHolder.LoadSplashAdListener {

    private FrameLayout flContainer;

    private FoxNativeSplashHolder foxNativeSplashHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ott);

        flContainer = findViewById(R.id.fl_splash_container);

        // 创建 开屏广告加载holder
        foxNativeSplashHolder = FoxNativeAdHelper.getNativeSplashHolder();

        int slotId = getIntent().getIntExtra("slotId", 0);
        // 加载广告素材
        foxNativeSplashHolder.loadSplashAd(slotId, TAConfig.USER_ID, this);
    }

    @Override
    protected void onDestroy() {
        if (foxNativeSplashHolder != null){
            foxNativeSplashHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Time out => " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSplashAdSuccess(FoxSplashAd foxSplashAd) {
        View view = null;
        if (foxSplashAd != null){
            foxSplashAd.setScaleType(ImageView.ScaleType.FIT_XY);
            view = foxSplashAd.getView();
        }

        if (view != null && !SplashOTTActivity.this.isFinishing()){
            flContainer.removeAllViews();
            flContainer.addView(view);
        }else {
            finish();
        }
    }

    @Override
    public void onTimeOver() {
        Toast.makeText(this, "Time over => " + getString(R.string.toast_time_over), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoadSuccess() {
        Toast.makeText(this, "Load success => " + getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestAdFailed(int i, String s) {
        Toast.makeText(this, "Request failed => " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(this, "Load failed => " + getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSkipClick() {
        Toast.makeText(this, "Skip click => " + getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAdClick() {
        Toast.makeText(this, "Ad click => " + getString(R.string.toast_click), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdExposure() {
        Toast.makeText(this, "Ad exposure => " + getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdActivityClose(String s) {
        finish();
    }
}