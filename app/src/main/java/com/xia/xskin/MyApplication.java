package com.xia.xskin;

import android.app.Application;

import com.xia.xskin.manager.SkinManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
