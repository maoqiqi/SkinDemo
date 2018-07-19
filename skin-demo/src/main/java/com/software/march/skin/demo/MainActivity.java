package com.software.march.skin.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.software.march.skin.manager.SkinManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_color_picker, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_color_picker) {
            createDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createDialog() {
        new AlertDialog.Builder(this)
                .setTitle("选择主题")
                .setItems(new String[]{"默认(蓝色)", "绿色", "红色"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            SkinManager.getInstance().restoreDefaultSkin();
                        } else if (which == 1) {
                            SkinManager.getInstance().loadBuiltInSkin("_green");
                        } else if (which == 2) {
                            SkinManager.getInstance().loadBuiltInSkin("_red");
                        }
                    }
                })
                .create().show();
    }
}