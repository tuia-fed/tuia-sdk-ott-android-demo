package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxTbScreen;

public class InsertOTTActivity extends AppCompatActivity {

    private Button btn;
    private FoxTbScreen tbScreen;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ott);
        btn = findViewById(R.id.bt_insert);

        slotId = getIntent().getIntExtra("slotId", 0);

        initListener();

        btn.requestFocus();
    }

    private void initListener(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tbScreen = new FoxTbScreen(InsertOTTActivity.this);
                tbScreen.setBackEnable(true);
                tbScreen.loadAd(slotId, TAConfig.USER_ID);
            }
        });

        btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    TADemoAnimationUtil.enlarge(v, 1.2F, 300);
                }else {
                    TADemoAnimationUtil.shrink(v, 1.2F, 300);
                }
            }
        });
    }
}