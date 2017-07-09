package com.software.march.skin.demo;

import android.app.Application;

import com.software.march.skin.manager.SkinManager;

/**
 * @author Doc.March
 * @date 2017/7/9
 */
public class SkinDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this).loadSkin();
    }
}