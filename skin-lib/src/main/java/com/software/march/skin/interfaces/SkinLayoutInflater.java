package com.software.march.skin.interfaces;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 根据布局文件视图的name和AttributeSet创建视图
 *
 * @author Doc.March
 * @date 2017/6/10
 */
public interface SkinLayoutInflater {

    View onCreateView(Context context, final String name, AttributeSet attrs);
}