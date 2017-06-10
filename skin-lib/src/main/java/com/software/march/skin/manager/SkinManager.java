package com.software.march.skin.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.software.march.skin.interfaces.ISkinLoader;
import com.software.march.skin.observe.SkinObservable;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 更改皮肤管理类
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinManager extends SkinObservable {

    private static SkinManager sInstance;

    private Context mContext;

    private final Object mLock = new Object();
    private boolean mLoading = false;

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

    /**
     * 加载上次选中皮肤
     */
    public void loadSkin() {
        // 加载内置皮肤
        if (SkinPreferencesManager.getInstance().isBuiltInSkin()) {
            String skinSuffixName = SkinPreferencesManager.getInstance().getSkinSuffixName();
            loadBuiltInSkin(skinSuffixName);
            return;
        }

        // 加载外部皮肤
        if (SkinPreferencesManager.getInstance().isExternalSkin()) {
            String skinFilePath = SkinPreferencesManager.getInstance().getSkinFilePath();
            loadExternalSkin(skinFilePath, null);
            return;
        }

        // 恢复默认皮肤
        restoreDefaultSkin();
    }

    /**
     * 加载内置皮肤
     *
     * @param skinSuffixName 内置皮肤后缀名
     */
    public void loadBuiltInSkin(String skinSuffixName) {
        if (SkinPreferencesManager.DEFAULT_SKIN_SUFFIX_NAME.equals(skinSuffixName)) {
            restoreDefaultSkin();
            return;
        }

        // 设置内置皮肤后缀名
        SkinPreferencesManager.getInstance().saveSkinSuffixName(skinSuffixName);
        // 设置外部皮肤包文件的路径
        SkinPreferencesManager.getInstance().saveSkinFilePath(SkinPreferencesManager.DEFAULT_SKIN_FILE_PATH);
        // 设置内置皮肤后缀名
        SkinResourcesManager.getInstance().setSkinInfo(skinSuffixName);
        // 通知更新皮肤
        notifyUpdateSkin();
    }

    /**
     * 加载外部皮肤
     *
     * @param skinFilePath 皮肤包文件路径
     * @param skinLoader   加载监听
     */
    public void loadExternalSkin(String skinFilePath, ISkinLoader skinLoader) {
        new SkinLoaderTask(skinLoader).execute(skinFilePath);
    }

    /**
     * 恢复默认皮肤
     */
    public void restoreDefaultSkin() {
        // 恢复默认皮肤设置
        SkinPreferencesManager.getInstance().restoreDefaultSkin();
        // 恢复默认皮肤设置
        SkinResourcesManager.getInstance().restoreDefaultSkin();
        // 通知更新皮肤
        notifyUpdateSkin();
    }

    private class SkinLoaderTask extends AsyncTask<String, Void, Resources> {

        private ISkinLoader skinLoader;

        private String mSkinFilePath;
        private String mSkinPackageName;

        public SkinLoaderTask(ISkinLoader skinLoader) {
            this.skinLoader = skinLoader;
        }

        @Override
        protected void onPreExecute() {
            if (skinLoader != null) skinLoader.onSkinLoaderStart();
        }

        @Override
        protected Resources doInBackground(String... params) {
            synchronized (mLock) {
                while (mLoading) {
                    try {
                        mLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mLoading = true;
            }
            try {
                if (params.length == 1) {
                    mSkinFilePath = params[0];
                    if (mSkinFilePath == null) return null;

                    File file = new File(mSkinFilePath);
                    if (file == null || !file.exists()) return null;

                    // 得到包管理器
                    PackageManager packageManager = mContext.getPackageManager();
                    // 得到包信息
                    PackageInfo mInfo = packageManager.getPackageArchiveInfo(mSkinFilePath, PackageManager.GET_ACTIVITIES);
                    mSkinPackageName = mInfo.packageName;

                    // AssetManager实例
                    AssetManager assetManager = AssetManager.class.newInstance();
                    // 通过反射调用addAssetPath方法
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    // assetManager对象中带有参数path的addAssetPath方法。返回值是Object,也既是该方法的返回值
                    addAssetPath.invoke(assetManager, mSkinFilePath);

                    // 得到资源实例
                    Resources superRes = mContext.getResources();

                    // 实例化皮肤资源,返回
                    return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Resources result) {
            synchronized (mLock) {
                if (result != null) {
                    // 设置外部皮肤包文件的路径
                    SkinPreferencesManager.getInstance().saveSkinFilePath(mSkinFilePath);
                    // 设置内置皮肤后缀名
                    SkinPreferencesManager.getInstance().saveSkinSuffixName(SkinPreferencesManager.DEFAULT_SKIN_SUFFIX_NAME);

                    // 设置外部皮肤包名和资源
                    SkinResourcesManager.getInstance().setSkinInfo(mSkinPackageName, result);
                    // 通知更新皮肤
                    notifyUpdateSkin();

                    if (skinLoader != null) skinLoader.onSkinLoaderSuccess();
                } else {
                    if (SkinResourcesManager.getInstance().getSkinPackageName() == null ||
                            SkinResourcesManager.getInstance().getSkinResources() == null) {
                        restoreDefaultSkin();
                    }

                    if (skinLoader != null) skinLoader.onSkinLoaderFailed();
                }

                mLoading = false;
                mLock.notifyAll();
            }
        }
    }
}