package com.xia.xskin.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/9/2/002 7:50
 * desc : 处理属性值的Utils
 **/
public class SkinThemeUtils {
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
}
