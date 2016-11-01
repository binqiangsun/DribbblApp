package com.dribbb.sun.dribbblapp.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.dribbblapp.MainApplication;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ViewPageAdapter;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.UserInfoLayoutBinding;
import com.dribbb.sun.dribbblapp.fragment.UserShotFragment;
import com.dribbb.sun.dribbblapp.utils.TypeUtils;
import com.dribbb.sun.model.Token;
import com.dribbb.sun.model.User;
import com.dribbb.sun.service.retrofit.DribService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by sunbinqiang on 8/7/16.
 */

public class UserInfoActivity extends BaseActivity<UserInfoLayoutBinding> {
    private static final int MSG_VIEW_ID = 1;
    private User mUser;

    @Override
    protected int getLayoutId() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void initViews() {
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
        ServiceFactory.toSubscribe(ServiceFactory.createPostRetrofitService(
                DribService.UserService.class).getUser(DribService.CLIENT_ID, DribService.CLIENT_SECRET, code),
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
            }
        });
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(String token){
        ServiceFactory.toSubscribe(ServiceFactory.createRetrofitServiceNoHead(
                DribService.UserInfoService.class).getUser(token),
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

    private void updateView(User user){
        mUser = user;
        setTabViewPager(mBinding.userTabs, mBinding.userViewPager);
    }

    private void setTabViewPager(TabLayout tabLayout, ViewPager viewPager){

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_LIKES, mUser.getId()));
        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_BUCKETS, mUser.getId()));
        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_SHOTS, mUser.getId()));
        viewPageAdapter.setFragments(fragmentList);

        List<String> titles = new ArrayList<>();
        titles.add("LIKES");
        titles.add("BUCKETS");
        titles.add("ALL");
        viewPageAdapter.setTitles(titles);

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

}
