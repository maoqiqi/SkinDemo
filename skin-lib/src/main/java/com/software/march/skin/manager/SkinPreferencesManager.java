package com.software.march.skin.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存当前所使用皮肤的相关信息,使得重启程序皮肤不变
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinPreferencesManager {

    /**
     * SharedPreferences文件名称
     */
    private static String PREFERENCE_NAME = "skin_preferences";

    /**
     * 内置皮肤后缀名
     */
    private static String SKIN_SUFFIX_NAME = "skin_suffix_name";

    /**
     * 默认内置皮肤后缀名
     */
    public static final String DEFAULT_SKIN_SUFFIX_NAME = "";

    /**
     * 外部皮肤文件路径
     */
    private static final String SKIN_FILE_PATH = "skin_file_path";

    /**
     * 默认外部皮肤文件路径
     */
    public static final String DEFAULT_SKIN_FILE_PATH = "";

    private static SkinPreferencesManager sInstance;

    private final Context mContext;
    private final SharedPreferences mSharedPreferences;

    private SkinPreferencesManager(Context context) {
        mContext = context.getApplicationContext();
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 初始化,必须首先调用
     *
     * @param context the context
     */
    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (SkinPreferencesManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinPreferencesManager(context);
                }
            }
        }
    }

    /**
     * 得到当前对象,defined from {@link SkinManager#SkinManager }
     *
     * @return {@link SkinPreferencesManager}
     */
    public static SkinPreferencesManager getInstance() {
        if (sInstance == null)
            throw new NullPointerException("SkinPreferencesManager must first call the init(Context context)");

        return sInstance;
    }

    /**
     * 保存内置皮肤后缀名
     *
     * @param skinSuffixName 后缀名
     * @return true or false
     */
    public boolean saveSkinSuffixName(String skinSuffixName) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(SKIN_SUFFIX_NAME, skinSuffixName);
        return editor.commit();
    }

    /**
     * 获取内置皮肤后缀名
     *
     * @return Built in skin suffix name
     */
    public String getSkinSuffixName() {
        return mSharedPreferences.getString(SKIN_SUFFIX_NAME, DEFAULT_SKIN_SUFFIX_NAME);
    }

    /**
     * 保存外部皮肤包文件的路径
     *
     * @param skinFilePath the skin package file's path
     * @return true or false
     */
    public boolean saveSkinFilePath(String skinFilePath) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(SKIN_FILE_PATH, skinFilePath);
        return editor.commit();
    }

    /**
     * 获取外部皮肤包文件的路径
     *
     * @return the skin package file's path
     */
    public String getSkinFilePath() {
        return mSharedPreferences.getString(SKIN_FILE_PATH, DEFAULT_SKIN_FILE_PATH);
    }

    /**
     * 是否是内置皮肤
     *
     * @return
     */
    public boolean isBuiltInSkin() {
        return !DEFAULT_SKIN_SUFFIX_NAME.equals(getSkinSuffixName());
    }

    /**
     * 是否是外部皮肤
     *
     * @return
     */
    public boolean isExternalSkin() {
        return !DEFAULT_SKIN_FILE_PATH.equals(getSkinFilePath());
    }

    /**
     * 是否是默认皮肤(不是内置皮肤,也不是外部皮肤)
     *
     * @return true or false
     */
    public boolean isDefaultSkin() {
        return !isExternalSkin() && !isBuiltInSkin();
    }

    /**
     * 恢复默认皮肤
     */
    public void restoreDefaultSkin() {
        // 清除数据
        saveSkinSuffixName(DEFAULT_SKIN_SUFFIX_NAME);
        saveSkinFilePath(DEFAULT_SKIN_FILE_PATH);
    }
}