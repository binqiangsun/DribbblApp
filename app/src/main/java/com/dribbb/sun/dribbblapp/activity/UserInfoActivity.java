package com.dribbb.sun.dribbblapp.activity;

import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.dribbblapp.MainApplication;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.UserInfoLayoutBinding;
import com.dribbb.sun.model.Token;
import com.dribbb.sun.service.retrofit.DribService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunbinqiang on 8/7/16.
 */

public class UserInfoActivity extends BaseActivity<UserInfoLayoutBinding> {

    private static Handler mHandler = new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void initViews() {
        Uri uri = getIntent().getData();
        String code = uri.getQueryParameter("code");
        getUser(code);
    }


    public void getUser(String code){
        //
        DribService.UserService userService = ServiceFactory.createPostRetrofitService(
                DribService.UserService.class);
        userService.getUser(DribService.CLIENT_ID, DribService.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                        Toast.makeText(UserInfoActivity.this, "complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public final void onNext(Token response) {
                        MainApplication.instance().accountService().updateToken(response.getAccess_token());
                    }
                });
    }

}
