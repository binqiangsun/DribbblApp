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
import com.dribbb.sun.dribbblapp.databinding.ActivityRegisterBinding;

/**
 * Created by sunbinqiang on 4/17/16.
 */
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        mBinding.registerPhoneTv.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spanString = new SpannableString("已有帐号？ 马上登录");
        spanString.setSpan(new RelativeSizeSpan(1.3f), 8, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickSpan = new ClickableSpan(){
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        spanString.setSpan(clickSpan, 8, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        mBinding.registerLoginTv.setText(spanString);

        //
        String mPhone = getIntent().getStringExtra("phone");
        mBinding.registerPhoneTv.setText(mPhone);
    }
}
