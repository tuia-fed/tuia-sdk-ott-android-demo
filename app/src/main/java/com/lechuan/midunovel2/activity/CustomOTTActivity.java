package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.mediamain.android.view.FoxCustomerTm;
import com.mediamain.android.view.interfaces.FoxNsTmListener;

public class CustomOTTActivity extends AppCompatActivity {

    private Button bt;

    private FoxCustomerTm customerTm;

    private int slotId;
    private String url = "https://engine.tuiatest.cn/index/activity?appKey=427wTcUcwxkttDmGcqYMTU7NJo3k&adslotId=283720&device_id=1010d8fcf4c0fd1f7b8937a7fc19bb8e&userFromType=1&sckId=48&actsck=0&formUserId=test-12345&sckFromType=0&newApiRid=0a68105fkgsse9vl-84&sdkVersion=3.0.2.5&userId=test-12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ott);
        bt = findViewById(R.id.bt_custom);

        customerTm = new FoxCustomerTm(this);
        initListener();

        slotId = getIntent().getIntExtra("slotId", 0);
        bt.requestFocus();
    }

    private void initListener(){
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

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 素材点击调用
                customerTm.adClicked();

                if (slotId > 0){
                    customerTm.loadAd(slotId);
                }else {
                    customerTm.openFoxActivity(url);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (customerTm != null){
            customerTm.destroy();
        }

        super.onDestroy();
    }
}