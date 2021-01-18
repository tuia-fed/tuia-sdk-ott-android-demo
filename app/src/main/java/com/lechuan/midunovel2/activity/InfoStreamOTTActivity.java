package com.lechuan.midunovel2.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.adapter.AdListAdapter;
import com.lechuan.midunovel2.bean.DemoBean;
import com.mediamain.android.view.holder.FoxNativeAdHelper;
import com.mediamain.android.view.holder.FoxTempletInfoFeedHolder;
import com.mediamain.android.view.interfaces.IFoxTempletInfoFeedAd;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class InfoStreamOTTActivity extends AppCompatActivity implements FoxTempletInfoFeedHolder.LoadInfoAdListener {

    private ListView lv;

    private FoxTempletInfoFeedHolder foxTempletInfoFeedHolder;
    private AdListAdapter adapter;

    private List<DemoBean> dataList = new ArrayList<>();
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_stream_ott);
        lv = findViewById(R.id.lv_info_stream);

        slotId = getIntent().getIntExtra("slotId", 0);
        // 创建信息流holder
        foxTempletInfoFeedHolder = FoxNativeAdHelper.getFoxTempletInfoFeedHolder();

        initView();
    }

    private void initView() {
        adapter = new AdListAdapter();

        lv.setAdapter(adapter);
        // 加载广告id
        foxTempletInfoFeedHolder.loadInfoAd(slotId, this);
        lv.requestFocus();
    }

    @Override
    protected void onDestroy() {
        // 销毁holder
        if (foxTempletInfoFeedHolder != null){
            foxTempletInfoFeedHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "error = " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void infoAdSuccess(List<IFoxTempletInfoFeedAd> list) {
        if (list == null || list.size() == 0 || adapter == null){
            return;
        }

        // 插入广告到listview中
        dataList.add(new DemoBean(null));
        for (IFoxTempletInfoFeedAd ad : list){
            // 对信息流广告属性设置
            ad.setImageSize(200, 200);
            ad.setTextColor(R.color.yellow);
            ad.setTextSize(20);
            dataList.add(new DemoBean(ad));
        }
        dataList.add(new DemoBean(null));

        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();

        lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.updateFocusDirection();
            }
        }, 500);
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
        Toast.makeText(this, getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
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