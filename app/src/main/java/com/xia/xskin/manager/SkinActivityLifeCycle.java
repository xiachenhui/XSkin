package com.xia.xskin.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/8/31/031 7:57
 * desc :生命周期管理类
 **/
class SkinActivityLifeCycle implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //拿到布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        //添加自定义创建view的工厂
        layoutInflater.setFactory(new SkinLayoutFactory());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
