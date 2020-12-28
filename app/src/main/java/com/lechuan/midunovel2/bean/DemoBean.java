package com.lechuan.midunovel2.bean;

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

    public DemoBean(IFoxTempletInfoFeedAd ad){
        this.ad = ad;
    }

    public IFoxTempletInfoFeedAd getAd() {
        return ad;
    }
}
