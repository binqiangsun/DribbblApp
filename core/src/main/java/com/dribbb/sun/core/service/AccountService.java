package com.dribbb.sun.core.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dribbb.sun.model.User;
import com.google.gson.Gson;

/**
 * Created by sunbinqiang on 8/18/16.
 */

public class AccountService {

    private Context mContext;
    private SharedPreferences mPreference;

    public AccountService(Context context){
        mContext = context;
        mPreference = context.getSharedPreferences("account", Context.MODE_PRIVATE);
    }

    public void updateToken(String token){
        mPreference.edit().putString("token", token).apply();
    }

    public void updateUser(User user){
        mPreference.edit()
                .putString("user", user.toJsonString())
                .putInt("uid", user.getId())
                .apply();
    }

    public String token(){
        return mPreference.getString("token", "");
    }

    public User user(){
        String userJsonString = mPreference.getString("user", null);
        if (TextUtils.isEmpty(userJsonString))
            return null;
        Gson gson = new Gson();
        return gson.fromJson(userJsonString,User.class);
    }
}
