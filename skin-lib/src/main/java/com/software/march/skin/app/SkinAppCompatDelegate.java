package com.software.march.skin.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.software.march.skin.attr.SkinSupportAttr;
import com.software.march.skin.bean.SkinSupportView;
import com.software.march.skin.constant.SkinConstant;
import com.software.march.skin.manager.SkinResourcesManager;
import com.software.march.skin.manager.SkinSupportAttrManager;

import java.util.ArrayList;
import java.util.List;

/**
 * SkinAppCompatDelegate实现{@link LayoutInflaterFactory},用来代替默认的LayoutInflaterFactory
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinAppCompatDelegate implements LayoutInflaterFactory {

    private final Activity mActivity;

    /**
     * 存储那些支持更改皮肤的视图的集合
     */
    private List<SkinSupportView> mSkinSupportViews;

    /**
     * 创建视图
     */
    private SkinAppCompatViewInflater mSkinAppCompatViewInflater;

    private SkinAppCompatDelegate(Activity activity) {
        this.mActivity = activity;
        this.mSkinSupportViews = new ArrayList<>();
    }

    /**
     * 创建SkinAppCompatDelegate
     *
     * @param activity the context
     * @return {@link SkinAppCompatDelegate}
     */
    public static SkinAppCompatDelegate create(Activity activity) {
        return new SkinAppCompatDelegate(activity);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConstant.NAMESPACE, SkinConstant.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable) return null;

        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mSkinAppCompatViewInflater == null) {
            mSkinAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        View view = mSkinAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                isPre21 /* Only tint wrap the context pre-L */
        );

        if (view == null) return null;

        parseSkinAttr(context, attrs, view);

        return view;
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = mActivity.getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    /**
     * 解析换肤属性
     *
     * @param context the context
     * @param attrs   attribute set
     * @param view    view
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinSupportAttr> skinSupportAttrs = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if (!SkinSupportAttrManager.getInstance().isSupportedSkin(attrName)) continue;

            if (attrValue.startsWith("@")) {
                try {
                    int resId = Integer.parseInt(attrValue.substring(1));// 资源Id

                    if (resId == 0) continue;

                    String resTypeName = context.getResources().getResourceTypeName(resId);// 类型名

                    SkinSupportAttr skinSupportAttr = SkinSupportAttrManager.getInstance().getSkinSupportAttr(attrName, resId, resTypeName);

                    if (skinSupportAttr != null) skinSupportAttrs.add(skinSupportAttr);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (skinSupportAttrs.size() > 0) {
            SkinSupportView skinSupportView = new SkinSupportView();
            skinSupportView.setView(view);
            skinSupportView.setAttrs(skinSupportAttrs);

            mSkinSupportViews.add(skinSupportView);

            // 如果当前皮肤不是默认皮肤
            if (!SkinResourcesManager.getInstance().isDefaultSkin()) skinSupportView.applySkin();
        }
    }

    /**
     * 应用皮肤
     */
    public void applySkin() {
        if (mSkinSupportViews.size() == 0) return;

        for (SkinSupportView skinSupportView : mSkinSupportViews) {
            if (skinSupportView.getView() == null) continue;
            skinSupportView.applySkin();
        }
    }

    /**
     * 清除那些支持更改皮肤的视图的集合数据
     */
    public void clear() {
        if (mSkinSupportViews.size() == 0) return;

        for (SkinSupportView skinSupportView : mSkinSupportViews) {
            if (skinSupportView.getView() == null) continue;
            skinSupportView.clear();
        }
    }
}