package com.lechuan.midunovel2.bean;

import android.view.View;

import com.mediamain.android.view.interfaces.IFoxTempletInfoFeedAd;

/**
 * <p> File description: <p>
 * <p> Creator: Adroll   <p>
 * <p> Created date: 12/11/20 <p>
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class DemoBean {

    private IFoxTempletInfoFeedAd ad;
    private View beforeView;
    private View nextView;

    public DemoBean(IFoxTempletInfoFeedAd ad){
        this.ad = ad;
    }

    public IFoxTempletInfoFeedAd getAd() {
        return ad;
    }

    public View getBeforeView() {
        return beforeView;
    }

    public void setBeforeView(View beforeView) {
        this.beforeView = beforeView;
    }

    public View getNextView() {
        return nextView;
    }

    public void setNextView(View nextView) {
        this.nextView = nextView;
    }
}
