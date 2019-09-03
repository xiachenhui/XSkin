package com.xia.xskin.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/8/31/031 7:59
 * desc : 自定义创建的view 的工厂，实现 观察者
 **/
public class SkinLayoutFactory2 implements LayoutInflater.Factory2, Observer {
    /**
     * 系统原生的控件
     */
    private static final String[] mClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    /**
     * 构造函数参数集合
     */
    private static final Class<?>[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};

    /**
     * 缓存已经创建过的View，key是全类名，Value是继承自View的构造，避免在一个xml文件中，有多个重复的view的时候需要多次反射创建view
     */
    private static final HashMap<String, Constructor<? extends View>> mConstructor = new HashMap<>();

    /**
     * 属性处理类
     */
    private SkinAttribute mSkinAttribute;

    public SkinLayoutFactory2() {
        mSkinAttribute = new SkinAttribute();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //反射创建View，系统的View
        View view = createViewFromTag(name, context, attrs);
        //自定义的View
        if (null == view) {
            view = createView(name, context, attrs);
        }
        //筛选复合属性的View
        mSkinAttribute.load(view, attrs);
        return view;
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        //包含自定义控件的
        if (-1 != name.indexOf(".")) {
            return null;
        }

        View view = null;
        for (int i = 0; i < mClassPrefixList.length; i++) {
            //创建View
            view = createView(mClassPrefixList[i] + name, context, attrs);
            if (view != null) {
                break;
            }
        }

        return view;
    }

    /**
     * 创建view
     *
     * @param name    全类名
     * @param context
     * @param attrs
     * @return
     */
    private View createView(String name, Context context, AttributeSet attrs) {
        //获取缓存中的构造
        Constructor<? extends View> constructor = mConstructor.get(name);
        //如果没有就添加
        if (constructor == null) {
            try {
                //通过全类名获取ClassLoader再获取Class
                Class<? extends View> aClass = context.getClassLoader().loadClass(name).asSubclass(View.class);
                //获取构造方法
                constructor = aClass.getConstructor(mConstructorSignature);
                //通过构造方法初始化View
                mConstructor.put(name, constructor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (constructor != null) {
            try {
                //通过构造方法，实例化view
                return constructor.newInstance(context, attrs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    /**
     * 观察到刷新了
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        //更换皮肤
        mSkinAttribute.applySkin();
    }
}
