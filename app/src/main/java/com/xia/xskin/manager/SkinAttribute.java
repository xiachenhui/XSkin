package com.xia.xskin.manager;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xia.xskin.utils.SkinResources;
import com.xia.xskin.utils.SkinThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : xia chen hui
 * email : 184415359@qq.com
 * date : 2019/9/2/002 7:37
 * desc : 属性类
 **/
public class SkinAttribute {
    /**
     * 需要换肤的属性的集合
     */
    private static final List<String> mAttributes = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
    }

    /**
     * 所有的需要换肤的View
     */
    private List<SkinView> mSkinViewList = new ArrayList<>();

    /**
     * 需要换肤的属性
     *
     * @param view
     * @param attrs
     */
    public void load(View view, AttributeSet attrs) {
        List<SkinPair> skinPairList = new ArrayList<>();
        //获取属性的集合的个数
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获取属性名字
            String attributeName = attrs.getAttributeName(i);
            //是需要换肤的就进行处理
            if (mAttributes.contains(attributeName)) {
                //获取属性的具体值
                String attributeValue = attrs.getAttributeValue(i);
                //背景颜色 ,#FFFFFF,如果是写死的就不处理
                if (attributeValue.startsWith("#")) {
                    continue;
                }

                //通过下标id拿到值对应的id

                // 跟随系统 background = ?colorPrimary
                int resId;
                if (attributeValue.startsWith("?")) {
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    //拿到属性值的id,只拿第一个
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //普通的@123435
                    resId = Integer.parseInt(attributeValue.substring(1));
                }
                //符合条件的attr,保存id
                if (resId != 0) {
                    SkinPair skinPair = new SkinPair(attributeName, resId);
                    skinPairList.add(skinPair);
                }
            }
        }


        if (!skinPairList.isEmpty()) {
            SkinView skinView = new SkinView(view, skinPairList);
            //应用skin
            skinView.applySkin();
            mSkinViewList.add(skinView);
        }

    }

    /**
     * view 的需要换肤的属性
     */
    static class SkinView {
        View view;
        List<SkinPair> skinPairs;

        public SkinView(View view, List<SkinPair> skinPairs) {
            this.view = view;
            this.skinPairs = skinPairs;
        }

        /**
         * 设置属性值
         */
        public void applySkin() {
            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, top = null, right = null, bottom = null;
                switch (skinPair.attributeName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(skinPair.resId);
                        //Color
                        if (background instanceof Integer) {
                            view.setBackgroundColor((Integer) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;
                    case "src":
                        background = SkinResources.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                    background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        ((TextView) view).setTextColor(SkinResources.getInstance().getColorStateList
                                (skinPair.resId));
                        break;
                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    default:
                        break;
                }
                if (left != null || top != null || right != null || bottom != null) {
                    ((TextView) view).setCompoundDrawables(left, top, right, bottom);
                }
            }
        }
    }

    /**
     * 需要换肤的属性名称和对应的id
     */
    static class SkinPair {
        String attributeName;
        int resId;

        public SkinPair(String attributeName, int resId) {
            this.attributeName = attributeName;
            this.resId = resId;
        }
    }


    /**
     * 换皮肤
     */
    public void applySkin() {
        for (SkinView mSkinView : mSkinViewList) {
            mSkinView.applySkin();
        }
    }
}
