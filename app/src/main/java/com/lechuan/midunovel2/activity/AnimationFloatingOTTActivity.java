package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.view.holder.FoxFloatingWebHolder;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationFloatingOTTActivity extends AppCompatActivity implements FoxFloatingWebHolder.FloatingWebAdLoadListener {

    private int slotId;

    private FoxFloatingWebHolder foxFloatingWebHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_floating_ott);

        slotId = getIntent().getIntExtra("slotId", 0);

        initView();
    }

    private void initView() {
        foxFloatingWebHolder = FoxNativeAdHelper.getFoxFloatingWebHolder();
        foxFloatingWebHolder.setFloatingHost(this);
        foxFloatingWebHolder.setConfigInfo(TAConfig.APP_KEY, TAConfig.APP_SECRET);

        String left = "50";
        String right = "100";
        String top = "100";
        String bottom = "50";

        foxFloatingWebHolder.loadFloatingWebAd(slotId, TAConfig.USER_ID, left, top, right, bottom, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsumed = false;
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (foxFloatingWebHolder != null) {
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
}