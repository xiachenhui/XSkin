package com.xia.xskin.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/8/31/031 7:57
 * desc :生命周期管理类
 **/
class SkinActivityLifeCycle implements Application.ActivityLifecycleCallbacks {
    /**
     * 缓存观察者
     */
    private final HashMap<Activity, SkinLayoutFactory2> mFactory2HashMap = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //拿到布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        try {
            //mFactorySet默认是false，加载过一次之后会变为true，再加载会抛异常，所以设置为false
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.setBoolean(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //添加自定义创建view的工厂
        SkinLayoutFactory2 factory2 = new SkinLayoutFactory2();
        layoutInflater.setFactory2(factory2);

        //注册观察者
        SkinManager.getInstance().addObserver(factory2);
        mFactory2HashMap.put(activity, factory2);
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
        //删除观察者
        SkinLayoutFactory2 remove = mFactory2HashMap.remove(activity);
        SkinManager.getInstance().deleteObserver(remove);
    }
}
