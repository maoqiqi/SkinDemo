package com.software.march.skin.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.software.march.skin.manager.SkinManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        enableLoginBtn();
        etUserName.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            enableLoginBtn();
        }
    };

    private void enableLoginBtn() {
        if (etUserName.getText().length() != 0 && etPassword.getText().length() != 0) {
            btnLogin.setAlpha(1.0f);
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setAlpha(0.3f);
            btnLogin.setEnabled(false);
        }
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