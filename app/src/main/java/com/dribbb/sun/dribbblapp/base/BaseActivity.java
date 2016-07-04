package com.dribbb.sun.dribbblapp.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.dribbb.sun.dribbblapp.R;

/**
 * Created by sunbinqiang on 16/2/26.
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected Toolbar toolbar;
    private ProgressDialog progressDialog;
    protected T mBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initViews();
        setViewListener();
    }

    /**
     * set layout Id
     * @return
     */
    abstract protected int getLayoutId();

    /**
     * init views and set click listeners
     */
    abstract protected void initViews();

    protected void setViewListener(){
        return;
    }


    public void showProgressDialog(String title) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        progressDialog.setOnKeyListener(onKeyListener);
        progressDialog.setMessage(title);
    }

    public boolean isShowProgressDialog() {
        if (progressDialog != null) {
            return progressDialog.isShowing();
        }
        return false;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissProgressDialog();
            }
            return false;
        }
    };
}
