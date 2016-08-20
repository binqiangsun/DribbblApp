package com.dribbb.sun.dribbblapp;

import android.app.Application;

import com.dribbb.sun.core.service.AccountService;
import com.dribbb.sun.core.service.ServicesManager;
import com.dribbb.sun.dribbblapp.instance.FrescoManager;

/**
 * Created by sunbinqiang on 16/2/3.
 */
public class MainApplication extends Application {

    public static MainApplication instance(){
        if(instance == null){
            throw new RuntimeException("application is not initial");
        }
        return instance;
    }

    private static MainApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FrescoManager.getInstance().initFresco(this, getPackageName());
    }

    public AccountService accountService(){
        return (AccountService) ServicesManager.instance(this).getService(ACCOUNT_SERVICE);
    }
}
