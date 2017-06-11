package com.software.march.skin.manager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * 资源管理类,根据当前选择的皮肤,加载对应的资源
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinResourcesManager {

    private static SkinResourcesManager sInstance;

    private final Context mContext;

    /**
     * 内置皮肤后缀名
     */
    private String mSkinSuffixName;

    /**
     * 当前皮肤的包名
     */
    private String mSkinPackageName;

    /**
     * 当前皮肤的资源
     */
    private Resources mSkinResources;

    /**
     * 当前皮肤是否是内置皮肤
     */
    private boolean mIsBuiltInSkin;

    /**
     * 当前皮肤是否是外部皮肤
     */
    private boolean mIsExternalSkin;

    private SkinResourcesManager(Context context) {
        mContext = context.getApplicationContext();
        // 设置默认资源
        restoreDefaultSkin();
    }

    /**
     * 初始化,必须首先调用
     *
     * @param context the context
     */
    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (SkinResourcesManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinResourcesManager(context);
                }
            }
        }
    }

    /**
     * 得到当前对象,defined from {@link SkinManager#SkinManager }
     *
     * @return {@link SkinResourcesManager}
     */
    public static SkinResourcesManager getInstance() {
        if (sInstance == null)
            throw new NullPointerException("SkinResourcesManager must first call the init(Context context)");

        return sInstance;
    }

    /**
     * 设置内置皮肤后缀名
     *
     * @param skinSuffixName 内置皮肤后缀名
     */
    public void setSkinInfo(String skinSuffixName) {
        mSkinSuffixName = skinSuffixName;
        mSkinPackageName = mContext.getPackageName();
        mSkinResources = mContext.getResources();
        mIsBuiltInSkin = true;
        mIsExternalSkin = false;
    }

    /**
     * 设置外部皮肤包名和资源
     *
     * @param skinPackageName 包名
     * @param skinResources   资源
     */
    public void setSkinInfo(String skinPackageName, Resources skinResources) {
        mSkinSuffixName = "";
        mSkinPackageName = skinPackageName;
        mSkinResources = skinResources;
        mIsBuiltInSkin = false;
        mIsExternalSkin = true;
    }

    /**
     * 恢复默认皮肤
     */
    public void restoreDefaultSkin() {
        mSkinSuffixName = "";
        mSkinPackageName = mContext.getPackageName();
        mSkinResources = mContext.getResources();
        mIsBuiltInSkin = false;
        mIsExternalSkin = false;
    }

    /**
     * 判断当前皮肤是否是默认皮肤
     *
     * @return true or false
     */
    public boolean isDefaultSkin() {
        return !mIsBuiltInSkin && !mIsExternalSkin;
    }

    /**
     * 得到当前皮肤的包名
     *
     * @return
     */
    public String getSkinPackageName() {
        return mSkinPackageName;
    }

    /**
     * 得到当前皮肤的资源
     *
     * @return
     */
    public Resources getSkinResources() {
        return mSkinResources;
    }

    /**
     * 追加皮肤后缀名
     *
     * @param entryName resource name
     * @return
     */
    private String appendSkinSuffixName(String entryName) {
        return entryName + mSkinSuffixName;
    }

    /**
     * 根据资源id得到color
     *
     * @param resId resource id
     * @return
     */
    public int getColor(int resId) {
        int color = ContextCompat.getColor(mContext, resId);
        if (isDefaultSkin()) return color;

        String entryName = mContext.getResources().getResourceEntryName(resId);

        int colorId = mSkinResources.getIdentifier(appendSkinSuffixName(entryName), "color", mSkinPackageName);

        return colorId == 0 ? color : mSkinResources.getColor(colorId);
    }

    /**
     * 根据资源名得到color
     *
     * @param entryName resource name
     * @return
     */
    public int getColor(String entryName) {
        int colorId = mSkinResources.getIdentifier(appendSkinSuffixName(entryName), "color", mSkinPackageName);
        return colorId == 0 ? 0 : mSkinResources.getColor(colorId);
    }

    /**
     * 根据资源id得到Drawable
     *
     * @param resId resource id
     * @return
     */
    public Drawable getDrawable(int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        if (isDefaultSkin()) return drawable;

        String entryName = mContext.getResources().getResourceEntryName(resId);
        String typeName = mContext.getResources().getResourceTypeName(resId);

        if (!"drawable".equals(typeName) && "mipmap".equals(drawable)) return drawable;

        int drawableId = mSkinResources.getIdentifier(entryName, typeName, mSkinPackageName);

        return drawableId == 0 ? drawable : mSkinResources.getDrawable(drawableId);
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList,保证selector类型的Color也能被转换。
     *
     * @param resId resource id
     * @return
     */
    public ColorStateList getColorStateList(int resId) {
        ColorStateList colorStateList = ContextCompat.getColorStateList(mContext, resId);
        if (isDefaultSkin()) return colorStateList;

        String entryName = mContext.getResources().getResourceEntryName(resId);

        int colorStateListId = mSkinResources.getIdentifier(entryName, "color", mSkinPackageName);

        return colorStateListId == 0 ? colorStateList : mSkinResources.getColorStateList(colorStateListId);
    }

    /**
     * 获取应用的主要暗色调
     *
     * @return
     */
    public int getColorPrimaryDark() {
        return getColor("colorPrimaryDark");
    }

    /**
     * 获取应用的主要色调
     *
     * @return
     */
    public int getColorPrimary() {
        return getColor("colorPrimary");
    }
}