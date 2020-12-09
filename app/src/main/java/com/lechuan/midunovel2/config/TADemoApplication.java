package com.lechuan.midunovel2.config;

import android.app.Application;
import android.content.Context;

import com.mediamain.android.view.config.FoxSDK;

import androidx.multidex.MultiDex;

public class TADemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化SDK
        FoxSDK.init(this, TAConfig.APP_KEY, TAConfig.APP_SECRET);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
