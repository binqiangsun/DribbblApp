package com.dribbb.sun.dribbblapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.dribbb.sun.dribbblapp.MainApplication;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ViewPageAdapter;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.UserInfoLayoutBinding;
import com.dribbb.sun.dribbblapp.fragment.UserListFragment;
import com.dribbb.sun.dribbblapp.fragment.UserShotFragment;
import com.dribbb.sun.dribbblapp.utils.TypeUtils;
import com.dribbb.sun.model.Token;
import com.dribbb.sun.model.User;
import com.dribbb.sun.service.retrofit.ApiFactory;
import com.dribbb.sun.service.retrofit.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;

import static com.dribbb.sun.service.config.ServiceConfig.CLIENT_ID;
import static com.dribbb.sun.service.config.ServiceConfig.CLIENT_SECRET;

/**
 * Created by sunbinqiang on 8/7/16.
 */

public class UserInfoActivity extends BaseActivity<UserInfoLayoutBinding> {
    private static final int MSG_VIEW_ID = 1;
    private User mUser;
    private int mUserId;

    //其他人个人主页
    public static void startUserInfoActivity(Context context, int userId){
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra("other", true);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    //自己的个人主页
    public static void startProfileActivity(Context context){
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void initViews() {
        boolean isOther = getIntent().getBooleanExtra("other", false);
        if(isOther){
            mUserId = getIntent().getIntExtra("userId", 0);
            getUserInfo();
            return;
        }
        //个人主页
        if(!MainApplication.instance().accountService().isLogin()) {
            Uri uri = getIntent().getData();
            if(uri != null) {
                String code = uri.getQueryParameter("code");
                getUser(code);
            }
        }else{
            updateView(MainApplication.instance().accountService().user());
        }
    }


    public void getUser(String code){
        //
        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getUser(CLIENT_ID, CLIENT_SECRET, code),
                new Subscriber<Token>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Token response) {
                MainApplication.instance().accountService().updateToken(response.getAccess_token());
                getUserInfo(response.getAccess_token());
                showToast(response.getScope());
            }
        });
    }

    /**
     * 获取用户信息 - 自己
     */
    private void getUserInfo(String token){
        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getUser(token),
                new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(User user) {
                        MainApplication.instance().accountService().updateUser(user);
                        updateView(user);
                    }
                });
    }

    /**
     * 获取用户信息 - 其他
     */
    private void getUserInfo(){
        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getUser(mUserId),
                new Action1<User>() {
                    @Override
                    public void call(User user) {
                        updateView(user);
                    }
                });
    }

    private void updateView(User user){
        mUser = user;
        mBinding.setUser(mUser);
        setTabViewPager(mBinding.userTabs, mBinding.userViewPager);
    }

    private void setTabViewPager(TabLayout tabLayout, ViewPager viewPager){

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_SHOTS, mUser.getId()));
        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_LIKES, mUser.getId()));

        fragmentList.add(UserListFragment.newInstance(UserListFragment.TYPE_FOLLOWER, mUser.getId()));
        fragmentList.add(UserListFragment.newInstance(UserListFragment.TYPE_FOLLOWING, mUser.getId()));
        viewPageAdapter.setFragments(fragmentList);

        List<String> titles = new ArrayList<>();
        titles.add("ALL");
        titles.add("LIKES");
        titles.add("FOLLOWERS");
        titles.add("FOLLOWINGS");

        viewPageAdapter.setTitles(titles);

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

}
