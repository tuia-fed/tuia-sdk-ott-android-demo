package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.nativead.Ad;
import com.mediamain.android.nativead.AdCallBack;

import androidx.appcompat.app.AppCompatActivity;

public class NativeInsertOTT1Activity extends AppCompatActivity {

    private Ad ad;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_insert_ott1);

        slotId = getIntent().getIntExtra("slotId", 0);
        initAd();
    }

    private void initAd(){
        ad = new Ad(slotId + "", TAConfig.USER_ID, "");
        ad.init(this, null, Ad.AD_URL_NEW, new AdCallBack() {
            @Override
            public void onReceiveAd() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onActivityClose() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onActivityShow() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardClose() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_reward_close), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardShow() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_reward_show), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrizeClose() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_prize_close), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrizeShow() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_prize_show), Toast.LENGTH_SHORT).show();
            }
        });
        ad.loadAd(this, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsume = false;
        if (ad != null){
            isConsume = ad.onKeyBack(keyCode, event);
        }

        if (!isConsume){
            return super.onKeyDown(keyCode, event);
        }
        return isConsume;
    }

    @Override
    protected void onDestroy() {
        if (ad != null){
            ad.destroy();
        }

        super.onDestroy();
    }
}