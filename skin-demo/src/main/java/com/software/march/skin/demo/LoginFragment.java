package com.software.march.skin.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Doc.March
 * @date 2017/7/10
 */
public class LoginFragment extends BaseFragment {


    @BindView(R.id.et_email_1)
    EditText etEmail1;
    @BindView(R.id.et_password_1)
    EditText etPassword1;
    @BindView(R.id.btn_login_1)
    Button btnLogin1;
    @BindView(R.id.et_email_2)
    EditText etEmail2;
    @BindView(R.id.et_password_2)
    EditText etPassword2;
    @BindView(R.id.btn_login_2)
    Button btnLogin2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        enableLoginBtn();
        etEmail1.addTextChangedListener(textWatcher);
        etPassword1.addTextChangedListener(textWatcher);
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
        if (etEmail1.getText().length() != 0 && etPassword1.getText().length() != 0) {
            btnLogin1.setAlpha(1.0f);
            btnLogin1.setEnabled(true);
        } else {
            btnLogin1.setAlpha(0.3f);
            btnLogin1.setEnabled(false);
        }
    }
}