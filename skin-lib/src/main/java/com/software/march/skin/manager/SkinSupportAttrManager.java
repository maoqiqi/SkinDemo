package com.software.march.skin.manager;

import com.software.march.skin.attr.AbsListViewListSelectorAttr;
import com.software.march.skin.attr.ImageViewSrcAttr;
import com.software.march.skin.attr.ListViewDividerAttr;
import com.software.march.skin.attr.SkinSupportAttr;
import com.software.march.skin.attr.TextViewDrawableAttr;
import com.software.march.skin.attr.TextViewTextColorAttr;
import com.software.march.skin.attr.TextViewTextColorHintAttr;
import com.software.march.skin.attr.ViewBackgroundAttr;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理支持更改皮肤的属性
 *
 * @author Doc.March
 * @date 2017/6/9
 */
public class SkinSupportAttrManager {

    // 支持更改皮肤的属性
    public static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String TEXT_COLOR_HINT = "textColorHint";
    public static final String DRAWABLE_LEFT = "drawableLeft";
    public static final String DRAWABLE_TOP = "drawableTop";
    public static final String DRAWABLE_RIGHT = "drawableRight";
    public static final String DRAWABLE_BOTTOM = "drawableBottom";
    public static final String SRC = "src";
    public static final String LIST_SELECTOR = "listSelector";
    public static final String DIVIDER = "divider";

    private static SkinSupportAttrManager sInstance;

    /**
     * 存储支持更改皮肤的属性集合
     */
    private Map<String, SkinSupportAttr> mSkinSupportAttrMap;

    public SkinSupportAttrManager() {
        initSkinSupportAttrs();
    }

    /**
     * 初始化
     */
    private void initSkinSupportAttrs() {
        mSkinSupportAttrMap = new HashMap<>();
        mSkinSupportAttrMap.put(BACKGROUND, new ViewBackgroundAttr());
        mSkinSupportAttrMap.put(TEXT_COLOR, new TextViewTextColorAttr());
        mSkinSupportAttrMap.put(TEXT_COLOR_HINT, new TextViewTextColorHintAttr());
        mSkinSupportAttrMap.put(DRAWABLE_LEFT, new TextViewDrawableAttr().setAttrName(DRAWABLE_LEFT));
        mSkinSupportAttrMap.put(DRAWABLE_TOP, new TextViewDrawableAttr().setAttrName(DRAWABLE_TOP));
        mSkinSupportAttrMap.put(DRAWABLE_RIGHT, new TextViewDrawableAttr().setAttrName(DRAWABLE_RIGHT));
        mSkinSupportAttrMap.put(DRAWABLE_BOTTOM, new TextViewDrawableAttr().setAttrName(DRAWABLE_BOTTOM));
        mSkinSupportAttrMap.put(SRC, new ImageViewSrcAttr());
        mSkinSupportAttrMap.put(LIST_SELECTOR, new AbsListViewListSelectorAttr());
        mSkinSupportAttrMap.put(DIVIDER, new ListViewDividerAttr());
    }

    /**
     * 单例模式
     *
     * @return {@link SkinSupportAttrManager}
     */
    public static SkinSupportAttrManager getInstance() {
        if (sInstance == null) {
            synchronized (SkinSupportAttrManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinSupportAttrManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 检查属性是否支持更改皮肤
     *
     * @param attrName attribute name
     * @return true or false
     */
    public boolean isSupportedSkin(String attrName) {
        return mSkinSupportAttrMap.containsKey(attrName);
    }

    /**
     * 得到支持更改皮肤的属性信息
     *
     * @param attrName    attribute name
     * @param resId       resource id
     * @param resTypeName resource type name
     * @return
     */
    public SkinSupportAttr getSkinSupportAttr(String attrName, int resId, String resTypeName) {
        SkinSupportAttr skinSupportAttr = mSkinSupportAttrMap.get(attrName);
        if (skinSupportAttr == null) return null;

        skinSupportAttr.attrName = attrName;
        skinSupportAttr.resId = resId;
        skinSupportAttr.resTypeName = resTypeName;

        return skinSupportAttr;
    }

    /**
     * 添加多个支持更改皮肤的属性
     *
     * @param attrs 多个属性
     */
    public synchronized void addSkinSupportAttr(SkinSupportAttr... attrs) {
        if (attrs == null) return;

        for (int i = 0; i < attrs.length; i++) {
            if (attrs[i] != null && attrs[i].attrName != null) {
                mSkinSupportAttrMap.put(attrs[i].attrName, attrs[i]);
            }
        }
    }

    /**
     * 删除多个支持更改皮肤的属性
     *
     * @param attrNames 多个属性
     */
    public synchronized void removeSkinSupportAttr(String... attrNames) {
        if (attrNames == null) return;

        for (int i = 0; i < attrNames.length; i++) {
            if (mSkinSupportAttrMap.containsKey(attrNames[i])) {
                mSkinSupportAttrMap.remove(attrNames[i]);
            }
        }
    }
}