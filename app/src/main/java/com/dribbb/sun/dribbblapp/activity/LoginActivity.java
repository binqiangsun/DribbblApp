package com.dribbb.sun.dribbblapp.activity;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.ActivityLoginBinding;

/**
 * Created by sunbinqiang on 4/13/16.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {

        mBinding.loginRegisterTv.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spanString = new SpannableString("还没有帐号？ 立即注册");
        spanString.setSpan(new RelativeSizeSpan(1.3f), 7, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickSpan = new ClickableSpan(){

            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("phone", mBinding.email.getText().toString());
                startActivity(intent);
            }
        };
        spanString.setSpan(clickSpan, 7, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mBinding.loginRegisterTv.setText(spanString);

        mBinding.loginPaswdResetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PasswdResetActivity.class);
                intent.putExtra("phone", mBinding.email.getText().toString());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void setViewListener() {
        mBinding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("正在登录...");



            }
        });
    }



}
