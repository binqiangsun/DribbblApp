package com.dribbb.sun.dribbblapp.fragment;

import android.os.Bundle;

import com.dribbb.sun.dribbblapp.adapter.ListRecyclerViewAdapter;
import com.dribbb.sun.dribbblapp.adapter.UserShotAdapter;
import com.dribbb.sun.dribbblapp.base.BaseListFragment;
import com.dribbb.sun.model.Shot;

/**
 * Created by sunbinqiang on 16/2/27.
 * 首页精选
 */
public class UserShotFragment extends BaseListFragment<Shot> {

    //实例化, 传递参数
    public static UserShotFragment newInstance(int type, int userId){
        UserShotFragment selectedFragment = new UserShotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("userId", userId);
        selectedFragment.setArguments(bundle);
        return selectedFragment;
    }

    @Override
    protected ListRecyclerViewAdapter<Shot> getAdapter() {
        return new UserShotAdapter(getContext(),
                getArguments().getInt("type"), getArguments().getInt("userId"));
    }


}
