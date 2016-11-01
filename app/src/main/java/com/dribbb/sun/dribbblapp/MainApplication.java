package com.dribbb.sun.dribbblapp;

import com.dribbb.sun.core.service.LibApplication;
import com.dribbb.sun.core.service.ServicesManager;
import com.dribbb.sun.core.service.account.AccountService;
import com.dribbb.sun.dribbblapp.instance.FrescoManager;
import com.dribbb.sun.dribbblapp.react.NativeReactPackage;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sunbinqiang on 16/2/3.
 */
public class MainApplication extends LibApplication implements ReactApplication {

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

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return false;
        }

        @Override
        public List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new NativeReactPackage()
            );
        }


    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
