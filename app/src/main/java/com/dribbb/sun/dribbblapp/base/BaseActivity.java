package com.dribbb.sun.dribbblapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dribbb.sun.dribbblapp.R;

/**
 * Created by sunbinqiang on 16/2/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setViews();
    }

    /**
     * set layout Id
     * @return
     */
    abstract protected int getLayoutId();

    /**
     * init views and set click listeners
     */
    abstract protected void setViews();
}
