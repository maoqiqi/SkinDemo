package com.software.march.skin.manager;

import com.software.march.skin.interfaces.SkinLayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理SkinLayoutInflater创建视图
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinLayoutInflaterManager {

    private static SkinLayoutInflaterManager sInstance;

    /**
     * 自定义SkinLayoutInflater集合
     */
    private List<SkinLayoutInflater> mSkinLayoutInflater;

    private SkinLayoutInflaterManager() {
        this.mSkinLayoutInflater = new ArrayList<>();
    }

    /**
     * 得到当前对象
     *
     * @return {@link SkinLayoutInflaterManager}
     */
    public static SkinLayoutInflaterManager getInstance() {
        if (sInstance == null) {
            synchronized (SkinLayoutInflaterManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinLayoutInflaterManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加自定义SkinLayoutInflater
     *
     * @param inflater {@link SkinLayoutInflater}
     * @return {@link SkinLayoutInflaterManager}
     */
    public synchronized SkinLayoutInflaterManager addSkinLayoutInflater(SkinLayoutInflater inflater) {
        mSkinLayoutInflater.add(inflater);
        return this;
    }

    /**
     * 得到创建视图的SkinLayoutInflater的集合
     *
     * @return
     */
    public synchronized List<SkinLayoutInflater> getSkinLayoutInflaters() {
        return mSkinLayoutInflater;
    }
}