package com.dribbb.sun.dribbblapp.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ListRecyclerViewAdapter;
import com.dribbb.sun.dribbblapp.base.BaseListFragment;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.UserViewHolderBinding;
import com.dribbb.sun.model.User;
import com.dribbb.sun.service.retrofit.ApiFactory;
import com.google.gson.Gson;

import org.json.JSONArray;

import rx.Observable;

/**
 * Created by sunbinqiang on 26/11/2016.
 */

public class UserListFragment extends BaseListFragment<User> {

    public static final int TYPE_FOLLOWER = 1;
    public static final int TYPE_FOLLOWING = 2;

    //实例化, 传递参数
    public static UserListFragment newInstance(int type, int userId){
        UserListFragment userListFragment = new UserListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("userId", userId);
        userListFragment.setArguments(bundle);
        return userListFragment;
    }


    @Override
    protected ListRecyclerViewAdapter<User> getAdapter() {
        return new UserAdapter(getArguments().getInt("type"), getArguments().getInt("userId"));
    }

    private static class UserAdapter extends ListRecyclerViewAdapter<User>{

        private int mType;
        private int mUserId;

        public UserAdapter(int type, int userId){
            mType = type;
            mUserId = userId;
        }

        @Override
        public Observable<com.dribbb.sun.model.User[]> getObservable() {
            switch (mType) {
                case TYPE_FOLLOWER:
                    return ApiFactory.getRequestService().getFollowers(mUserId, String.valueOf(mPage));
                case TYPE_FOLLOWING:
                    return ApiFactory.getRequestService().getFollowings(mUserId, String.valueOf(mPage));
                default:
                    return null;
            }
        }

        @Override
        protected User[] getResult(Gson gson, JSONArray object) {
            return gson.fromJson(object.toString(), User[].class);
        }

        @Override
        protected BaseViewHolder onCreateItemView(ViewGroup parent) {
            return new UserViewHolder(parent.getContext(), parent);
        }

        @Override
        protected void onBindItemView(RecyclerView.ViewHolder holder, int position) {
            ((UserViewHolder)holder).setUser(getList().get(position));
        }
    }

    private static class UserViewHolder extends BaseViewHolder{

        private UserViewHolderBinding mBinding;

        public UserViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.user_view_holder);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setUser(User user){
            mBinding.setUser(user);
        }

    }
}
