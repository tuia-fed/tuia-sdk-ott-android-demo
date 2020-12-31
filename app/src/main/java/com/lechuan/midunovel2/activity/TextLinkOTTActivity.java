package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.holder.FoxTextLintAd;
import com.mediamain.android.view.interfaces.FoxTextLinkHolder;

public class TextLinkOTTActivity extends AppCompatActivity implements FoxTextLinkHolder.LoadInfoAdListener {

    private RelativeLayout rlContainer;

    private FoxTextLinkHolder foxTextLinkHolder;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_link_ott);
        rlContainer = findViewById(R.id.rl_text_link_container);

        slotId = getIntent().getIntExtra("slotId", 0);
        initView();
    }

    private void initView(){
        // 创建文字链holder
        foxTextLinkHolder = FoxNativeAdHelper.getFoxTextLinkHolder();
        foxTextLinkHolder.loadInfoAd(this, slotId, TAConfig.USER_ID, this);
    }

    @Override
    protected void onDestroy() {
        // 销毁holder
        if (foxTextLinkHolder != null) {
            foxTextLinkHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "error = " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void infoAdSuccess(FoxTextLintAd foxTextLintAd) {
        if (foxTextLintAd == null || foxTextLintAd.getView() == null){
            return;
        }

        if (isFinishing()){
            return;
        }

        // 对holder里对文字属性设置
        foxTextLintAd.setDescSize(26);
        foxTextLintAd.setTitleSize(26);
        foxTextLintAd.setDescColor(R.color.black);
        foxTextLintAd.setTitleColor(R.color.black);
        foxTextLintAd.setDescColor(getResources().getColor(R.color.yellow));
        // 动态添加文字链holder到container
        rlContainer.removeAllViews();
        rlContainer.addView(foxTextLintAd.getView());

        // 对view手动设置获焦
        foxTextLintAd.getView().requestFocus();
    }

    @Override
    public void onReceiveAd() {
        Toast.makeText(this, getString(R.string.toast_receive_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedToReceiveAd(int i, String s) {
        Toast.makeText(this, getString(R.string.toast_receive_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(this, getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCloseClick() {
        Toast.makeText(this, getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClick() {
        Toast.makeText(this, getString(R.string.toast_click), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdExposure() {
        Toast.makeText(this, getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdActivityClose(String s) {
        Toast.makeText(this, getString(R.string.toast_time_over), Toast.LENGTH_SHORT).show();
    }
}