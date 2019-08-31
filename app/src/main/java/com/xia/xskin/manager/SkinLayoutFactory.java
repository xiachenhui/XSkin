package com.xia.xskin.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/8/31/031 7:59
 * desc : 自定义创建的view 的工厂
 **/
public class SkinLayoutFactory implements LayoutInflater.Factory2 {
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }
}
