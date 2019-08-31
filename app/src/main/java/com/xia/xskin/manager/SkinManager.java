package com.xia.xskin.manager;

import android.app.Application;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/8/31/031 7:55
 * desc :换肤的管理类
 **/
public class SkinManager {
    private static SkinManager mSkinManager;

    private Application mApplication;

    private SkinManager(Application mApplication) {
        this.mApplication = mApplication;
        /**
         *  提供一个应用的生命周期的回调方法
         *  对应用的生命周期进行集中管理
         *  registerActivityLifecycleCallbacks
         *  Activity的生命周期都会回调这里的方法
         */
        mApplication.registerActivityLifecycleCallbacks(new SkinActivityLifeCycle());
    }

    public static void init(Application application) {
        if (mSkinManager == null) {
            synchronized (SkinManager.class) {
                mSkinManager = new SkinManager(application);
            }
        }
    }
}
