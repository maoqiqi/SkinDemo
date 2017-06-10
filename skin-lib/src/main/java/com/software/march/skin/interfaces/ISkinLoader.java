package com.software.march.skin.interfaces;

/**
 * 皮肤包加载监听
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public interface ISkinLoader {

    /**
     * 皮肤包开始加载时调用
     */
    void onSkinLoaderStart();

    /**
     * 皮肤包加载成功调用
     */
    void onSkinLoaderSuccess();

    /**
     * 皮肤包加载失败调用
     */
    void onSkinLoaderFailed();
}