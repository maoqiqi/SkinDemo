package com.software.march.skin.attr;

import android.view.View;
import android.widget.TextView;

import com.software.march.skin.manager.SkinResourcesManager;

/**
 * 更改TextView的TextColor
 *
 * @author Doc.March
 * @date 2017/6/9
 */
public class TextViewTextColorAttr extends SkinSupportAttr {

    @Override
    public void apply(View view) {
        if (resId == INVALID_ID) return;

        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(SkinResourcesManager.getInstance().getColorStateList(resId));
        }
    }
}