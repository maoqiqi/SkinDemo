package com.software.march.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.software.march.skin.manager.SkinResourcesManager;

/**
 * 更改View的背景
 *
 * @author Doc.March
 * @date 2017/6/9
 */
public class ViewBackgroundAttr extends SkinSupportAttr {

    @Override
    public void apply(View view) {
        if (resId == INVALID_ID) return;

        if (isColor()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                view.setBackgroundColor(SkinResourcesManager.getInstance().getColor(resId));
            } else {
                ColorStateList colorStateList = SkinResourcesManager.getInstance().getColorStateList(resId);
                Drawable drawable = view.getBackground();
                if (drawable != null) {
                    DrawableCompat.setTintList(drawable, colorStateList);
                    ViewCompat.setBackground(view, drawable);
                } else {
                    ColorDrawable colorDrawable = new ColorDrawable();
                    colorDrawable.setTintList(colorStateList);
                    ViewCompat.setBackground(view, colorDrawable);
                }
            }
        } else if (isDrawable()) {
            Drawable drawable = SkinResourcesManager.getInstance().getDrawable(resId);
            ViewCompat.setBackground(view, drawable);
        }
    }
}