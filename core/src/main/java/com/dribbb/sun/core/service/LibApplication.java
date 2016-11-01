package com.dribbb.sun.core.service;

import android.app.Application;

import com.dribbb.sun.core.service.account.AccountService;

/**
 * Created by sunbinqiang on 9/26/16.
 */

public class LibApplication extends Application {

    private static LibApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static LibApplication instance() {
        if (instance == null) {
            throw new RuntimeException("Application is not initialized");
        }
        return instance;
    }

    public AccountService accountService(){
        return (AccountService) ServicesManager.instance(this).getService(ACCOUNT_SERVICE);
    }
}
