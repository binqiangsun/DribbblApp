package com.dribbb.sun.core.service.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;

import com.dribbb.sun.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by sunbinqiang on 8/18/16.
 */

public class AccountService {

    private ArrayList<AccountListener> listeners = new ArrayList<>();

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
                //.putString("token", )
                .apply();
        dispatchAccountChanged();
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

    public boolean isLogin() {
        return !TextUtils.isEmpty(token()) && user() != null;
    }

    public void login(Context context){
        login(context, null);
    }

    public void login(Context context, AccountListener listener){
        addListener(listener);
        Uri uri = Uri.parse("drib://web");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("url", "https://dribbble.com/oauth/authorize");
        context.startActivity(intent);
    }


    public void addListener(AccountListener listener){
        if(listener == null) return;
        listeners.add(listener);
    }

    public void removeListener(AccountListener listener){
        listeners.remove(listener);
    }

    private void dispatchAccountChanged() {
        for (AccountListener listener : listeners) {
            if (listener == null) continue;
            listener.onAccountChanged(this);
        }
    }
}
