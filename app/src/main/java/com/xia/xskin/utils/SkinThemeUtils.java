package com.xia.xskin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/9/2/002 7:50
 * desc : 处理属性值的Utils
 **/
public class SkinThemeUtils {

    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimaryDark
    };
    private static int[] STATUS_BAR_COLOR_ATTRS = {android.R.attr.statusBarColor, android.R.attr
            .navigationBarColor};

    /**
     * 获取属性值的id 集合
     *
     * @param context
     * @param attrs   属性值
     * @return
     */
    public static int[] getResId(Context context, int[] attrs) {
        int[] ints = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            //赋值
            ints[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return ints;
    }

    //替换状态栏
    public static void updateStatusBarColor(Activity activity) {
        //5.0 以上才能修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        //获取statusBarColor与navigationBarColor  颜色值
        int[] statusBarId = getResId(activity, STATUS_BAR_COLOR_ATTRS);

        //如果statusBarColor 配置颜色值， 就换肤
        if (statusBarId[0] != 0) {
            activity.getWindow().setStatusBarColor(SkinResources.getInstance().getColor(statusBarId[0]));
        } else {
            //获取colorPrimaryDark
            int resId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (resId != 0) {
                activity.getWindow().setStatusBarColor(SkinResources.getInstance().getColor(resId));
            }
        }

        if (statusBarId[1] != 0) {
            activity.getWindow().setNavigationBarColor(SkinResources.getInstance().getColor(statusBarId[1]));
        }

    }
}
