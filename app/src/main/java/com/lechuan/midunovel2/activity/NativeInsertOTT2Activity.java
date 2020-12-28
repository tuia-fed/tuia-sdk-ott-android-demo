package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.nativead.Ad;
import com.mediamain.android.nativead.DefaultAdCallBack;

import androidx.appcompat.app.AppCompatActivity;

public class NativeInsertOTT2Activity extends AppCompatActivity {

    private Ad ad;

    private Button bt;

    private int slotId;
    private boolean isLoadSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_insert_ott2);
        bt = findViewById(R.id.bt_native_insert2);

        slotId = getIntent().getIntExtra("slotId", 0);
        initView();
    }

    private void initView() {
        ad = new Ad(slotId + "", TAConfig.USER_ID, "", Ad.AD_NEW_LOADING_HIDE);
        ad.init(this, null, Ad.AD_URL_OLD, new DefaultAdCallBack() {
            @Override
            public void onReceiveAd() {
                super.onReceiveAd();

                bt.setText("加载成功了！！！！");
                bt.requestFocus();

                isLoadSuccess = true;
            }
        });
        ad.loadAd(this, true);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoadSuccess){
                    ad.show();
                }
            }
        });
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