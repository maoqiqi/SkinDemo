package com.software.march.skin.manager;

import android.content.Context;

import com.software.march.skin.observe.SkinObservable;

/**
 * 更改皮肤管理类
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinManager extends SkinObservable {

    private static SkinManager sInstance;

    private Context mContext;

    private SkinManager(Context context) {
        super();
        mContext = context.getApplicationContext();
        // 初始化
        SkinPreferencesManager.init(mContext);
        SkinResourcesManager.init(mContext);
    }

    /**
     * 初始化,必须首先调用
     *
     * @param context the context
     * @return {@link SkinManager}
     */
    public static SkinManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 得到当前对象
     *
     * @return {@link SkinManager}
     */
    public static SkinManager getInstance() {
        if (sInstance == null)
            throw new NullPointerException("SkinManager must first call the init(Context context)");

        return sInstance;
    }
}