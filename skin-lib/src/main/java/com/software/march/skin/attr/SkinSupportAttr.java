package com.software.march.skin.attr;

import android.content.Context;
import android.view.View;

/**
 * 支持更改皮肤的属性
 *
 * @author Doc.March
 * @date 2017/6/9
 */
public abstract class SkinSupportAttr {

    /**
     * 无效的ID
     */
    public static final int INVALID_ID = 0;

    /**
     * 资源类型名称color
     */
    public static final String RES_TYPE_NAME_COLOR = "color";

    /**
     * 资源类型名称drawable
     */
    public static final String RES_TYPE_NAME_DRAWABLE = "drawable";

    /**
     * 资源类型名称mipmap
     */
    public static final String RES_TYPE_NAME_MIPMAP = "mipmap";

    /**
     * 属性名称
     */
    public String attrName;

    /**
     * 资源id
     */
    public int resId;

    /**
     * 资源类型名称
     */
    public String resTypeName;

    /**
     * 设置属性名称
     *
     * @param attrName attribute name
     * @return {@link SkinSupportAttr}
     */
    public SkinSupportAttr setAttrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    /**
     * 设置数据
     *
     * @param context the context
     * @param resId   resource id
     * @return {@link SkinSupportAttr}
     */
    public SkinSupportAttr setData(Context context, int resId) {
        this.resId = resId;
        this.resTypeName = context.getResources().getResourceTypeName(resId);
        return this;
    }

    /**
     * 资源类型名称是否为drawable
     *
     * @return
     */
    protected boolean isDrawable() {
        return RES_TYPE_NAME_DRAWABLE.equals(resTypeName) || RES_TYPE_NAME_MIPMAP.equals(resTypeName);
    }

    /**
     * 资源类型名称是否为color
     *
     * @return
     */
    protected boolean isColor() {
        return RES_TYPE_NAME_COLOR.equals(resTypeName);
    }

    /**
     * 为视图设置支持更改皮肤的属性
     *
     * @param view 支持更改皮肤的视图
     */
    public abstract void apply(View view);
}