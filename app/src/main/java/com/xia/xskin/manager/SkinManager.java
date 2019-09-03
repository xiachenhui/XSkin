package com.xia.xskin.manager;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.xia.xskin.utils.SkinPreference;
import com.xia.xskin.utils.SkinResources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/8/31/031 7:55
 * desc :换肤的管理类，继承被观察者
 **/
public class SkinManager extends Observable {
    private static SkinManager mSkinManager;

    private Application mApplication;

    private SkinManager(Application application) {
        this.mApplication = application;
        //记录当前使用的皮肤
        SkinPreference.init(application);
        //皮肤包资源管理类，用于app或者皮肤加载资源
        SkinResources.init(application);

        /**
         *  提供一个应用的生命周期的回调方法
         *  对应用的生命周期进行集中管理
         *  registerActivityLifecycleCallbacks
         *  Activity的生命周期都会回调这里的方法
         */
        mApplication.registerActivityLifecycleCallbacks(new SkinActivityLifeCycle());
        //加载皮肤包
        loadSkin(SkinPreference.getInstance().getSkin());
    }


    public static void init(Application application) {
        if (mSkinManager == null) {
            synchronized (SkinManager.class) {
                mSkinManager = new SkinManager(application);
            }
        }
    }

    public static SkinManager getInstance() {
        return mSkinManager;
    }
    /**
     * 加载皮肤包
     */
    public void loadSkin(String path) {
        if (TextUtils.isEmpty(path)) {
            //重置，使用默认皮肤
            SkinPreference.getInstance().setSkin("");
            //清空资源管理器，皮肤资源属性等
            SkinResources.getInstance().reset();
        } else {
            //需要加载皮肤包
            try {
                //反射创建AssetManager
                AssetManager assetManager = AssetManager.class.newInstance();
                //这个方法被隐藏了，只能通过反射,资源路径设置， 目录或者压缩包
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, path);
                //获取当前apk的Resources
                Resources appResource = mApplication.getResources();
                //获取皮肤包Resources
                Resources skinResource = new Resources(assetManager, appResource.getDisplayMetrics(), appResource.getConfiguration());
                //保存皮肤包路径
                SkinPreference.getInstance().setSkin(path);
                //通过PackageManager获取皮肤包（外部APK）
                PackageManager packageManager = mApplication.getPackageManager();
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                //获取皮肤包包名
                String packageName = packageArchiveInfo.packageName;
                //记录皮肤包
                SkinResources.getInstance().applySkin(skinResource, packageName);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        //采集的View、皮肤包已经下载完成了
        setChanged();
        //通知观察者
        notifyObservers();
    }
}
