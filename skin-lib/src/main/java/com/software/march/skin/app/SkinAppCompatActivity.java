package com.software.march.skin.app;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.software.march.skin.manager.SkinManager;
import com.software.march.skin.observe.SkinObserver;

/**
 * Your Activity should extends from this if you what to do skin change.
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public class SkinAppCompatActivity extends AppCompatActivity implements SkinObserver {

    private SkinAppCompatDelegate mSkinDelegate;

    /**
     * 得到SkinAppCompatDelegate实例
     *
     * @return The {@link SkinAppCompatDelegate} being used by this Activity.
     */
    public SkinAppCompatDelegate getSkinDelegate() {
        if (mSkinDelegate == null) {
            mSkinDelegate = SkinAppCompatDelegate.create(this);
        }
        return mSkinDelegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 给Activity的LayoutInflater设置Factory时一定要在调用setContentView()方法之前
        final SkinAppCompatDelegate skinDelegate = getSkinDelegate();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), skinDelegate);
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().removeObserver(this);
        // 清除数据
        getSkinDelegate().clear();
    }

    /**
     * 更新皮肤
     */
    @Override
    public void updateSkin() {
        getSkinDelegate().applySkin();
    }
}