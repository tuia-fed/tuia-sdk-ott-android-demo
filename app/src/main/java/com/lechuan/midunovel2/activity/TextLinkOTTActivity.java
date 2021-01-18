package com.lechuan.midunovel2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.config.TAConfig;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.holder.FoxTextLintAd;
import com.mediamain.android.view.interfaces.FoxTextLinkHolder;

public class TextLinkOTTActivity extends AppCompatActivity implements FoxTextLinkHolder.LoadInfoAdListener, View.OnFocusChangeListener {

    private FrameLayout flContainer;

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTop;
    private TextView tvBottom;

    private FoxTextLinkHolder foxTextLinkHolder;

    private static final int ANIMATION_DURATION = 300;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_link_ott);

        flContainer = findViewById(R.id.fl_text_link_container);
        tvLeft = findViewById(R.id.tv_text_link_left);
        tvRight = findViewById(R.id.tv_text_link_right);
        tvTop = findViewById(R.id.tv_text_link_top);
        tvBottom = findViewById(R.id.tv_text_link_bottom);

        slotId = getIntent().getIntExtra("slotId", 0);

        tvLeft.setOnFocusChangeListener(this);
        tvRight.setOnFocusChangeListener(this);
        tvTop.setOnFocusChangeListener(this);
        tvBottom.setOnFocusChangeListener(this);

        initView();

        foxTextLinkHolder.setFoxNextLeftFocusView(tvLeft);
        foxTextLinkHolder.setFoxNextRightFocusView(tvRight);
        foxTextLinkHolder.setFoxNextUpFocusView(tvTop);
        foxTextLinkHolder.setFoxNextDownFocusView(tvBottom);
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
        flContainer.removeAllViews();
        flContainer.addView(foxTextLintAd.getView());

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            TADemoAnimationUtil.enlarge(v, 1.2F, ANIMATION_DURATION);
        }else {
            TADemoAnimationUtil.shrink(v, 1.2F, ANIMATION_DURATION);
        }
    }
}