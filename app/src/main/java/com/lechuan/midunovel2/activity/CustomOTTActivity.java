package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.mediamain.android.view.FoxCustomerTm;
import com.mediamain.android.view.interfaces.FoxNsTmListener;

import androidx.appcompat.app.AppCompatActivity;

public class CustomOTTActivity extends AppCompatActivity {

    private Button bt;

    private FoxCustomerTm customerTm;

    private int slotId;
    private String url = "baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ott);

        customerTm = new FoxCustomerTm(this);
        initListener();

        slotId = getIntent().getIntExtra("slotId", 0);
        bt.requestFocus();
    }

    private void initListener(){
        // 自定义广告view添加listener
        customerTm.setAdListener(new FoxNsTmListener() {
            @Override
            public void onReceiveAd(String s) {
                // 素材曝光调用
                customerTm.adExposed();
                Toast.makeText(CustomOTTActivity.this, CustomOTTActivity.this.getResources().getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(CustomOTTActivity.this, CustomOTTActivity.this.getResources().getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdActivityClose(String s) {
                Toast.makeText(CustomOTTActivity.this, CustomOTTActivity.this.getResources().getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
            }
        });

        bt.setOnClickListener(v -> {
            // 素材点击调用
            customerTm.adClicked();

            if (slotId > 0){
                // 加载广告id
                customerTm.loadAd(slotId);
            }else {
                // 打开自定义活动页
                customerTm.openFoxActivity(url);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 销毁view
        if (customerTm != null){
            customerTm.destroy();
        }

        super.onDestroy();
    }

}