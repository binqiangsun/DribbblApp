package com.dribbb.sun.dribbblapp;

import android.app.Application;

import com.dribbb.sun.dribbblapp.instance.FrescoManager;

/**
 * Created by sunbinqiang on 16/2/3.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FrescoManager.getInstance().initFresco(this, getPackageName());
    }
}
