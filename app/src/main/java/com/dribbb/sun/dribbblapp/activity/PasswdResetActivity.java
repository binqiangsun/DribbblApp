package com.dribbb.sun.dribbblapp.activity;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.ActivityResetPasswdBinding;

/**
 * Created by sunbinqiang on 4/17/16.
 */
public class PasswdResetActivity extends BaseActivity<ActivityResetPasswdBinding> {

    private String mPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_passwd;
    }

    @Override
    protected void initViews() {

        //
        mPhone = getIntent().getStringExtra("phone");
        mBinding.passwdPhoneTv.setText(mPhone);

    }
}
