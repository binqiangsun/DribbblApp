package com.dribbb.sun.dribbblapp.activity;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.dribbblapp.HomeActivity;
import com.dribbb.sun.dribbblapp.MainApplication;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ViewPageAdapter;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.UserInfoLayoutBinding;
import com.dribbb.sun.dribbblapp.fragment.SelectedFragment;
import com.dribbb.sun.model.Token;
import com.dribbb.sun.model.User;
import com.dribbb.sun.service.ServiceConfig;
import com.dribbb.sun.service.retrofit.DribService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by sunbinqiang on 8/7/16.
 */

public class UserInfoActivity extends BaseActivity<UserInfoLayoutBinding> {
    private static final int MSG_VIEW_ID = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case MSG_VIEW_ID:
                    updateView();
            }
            return false;
        }
    });

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
            }
        });
    }

    private void getUserInfo(){
        ServiceFactory.toSubscribe(ServiceFactory.createRetrofitServiceNoHead(
                DribService.UserInfoService.class).getUser(MainApplication.instance().accountService().token()),
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
                    }
                });
    }

    private void updateView(User user){

    }

    private void setTabViewPager(TabLayout tabLayout, ViewPager viewPager){

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(SelectedFragment.newInstance("", ""));
        fragmentList.add(SelectedFragment.newInstance(ServiceConfig.KEY_RECENT, ServiceConfig.PARAM_RECENT));
        fragmentList.add(SelectedFragment.newInstance(ServiceConfig.KEY_LIST, ServiceConfig.PARAM_DEBUT));
        fragmentList.add(SelectedFragment.newInstance(ServiceConfig.KEY_LIST, ServiceConfig.PARAM_ANIMATED));
        viewPageAdapter.setFragments(fragmentList);

        List<String> titles = new ArrayList<>();
        titles.add("PROFILE");
        titles.add("LIKES");
        titles.add("FOLLOWERS");
        titles.add("FOLLOWINGS");
        viewPageAdapter.setTitles(titles);

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

}
