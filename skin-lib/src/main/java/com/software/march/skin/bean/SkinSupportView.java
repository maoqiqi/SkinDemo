package com.software.march.skin.bean;

import android.view.View;

import com.software.march.skin.attr.SkinSupportAttr;

import java.util.List;

/**
 * 定义支持更改皮肤的视图
 *
 * @author Doc.March
 * @date 2017/6/9
 */
public class SkinSupportView {

    /**
     * 视图
     */
    private View view;

    /**
     * 支持更改皮肤的属性集合
     */
    private List<SkinSupportAttr> attrs;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<SkinSupportAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<SkinSupportAttr> attrs) {
        this.attrs = attrs;
    }

    /**
     * 应用皮肤
     */
    public void applySkin() {
        if (view == null || attrs == null || attrs.size() == 0) return;

        for (SkinSupportAttr attr : attrs) {
            if (attr != null) {
                attr.apply(view);
            }
        }
    }

    /**
     * 清除皮肤
     */
    public void cleanSkin() {
        if (view != null) view = null;

        if (attrs != null && attrs.size() > 0) attrs.clear();
    }
}