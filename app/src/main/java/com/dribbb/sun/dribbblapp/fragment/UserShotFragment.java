package com.dribbb.sun.dribbblapp.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.UserShotAdapter;
import com.dribbb.sun.dribbblapp.base.BaseFragment;

/**
 * Created by sunbinqiang on 16/2/27.
 * 首页精选
 */
public class UserShotFragment extends BaseFragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_recycle_layout;
    }

    @Override
    protected void setViews(View view) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary, R.color.colorPrimary,
                R.color.colorAccent, R.color.colorPrimary);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(4, 4, 4,
                        4);
            }
        });
        final UserShotAdapter adapter = new UserShotAdapter(getContext(),
                getArguments().getInt("type"), getArguments().getInt("userId"));
        adapter.setSwipeRefreshLayout(swipeRefreshLayout);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.reset(false);
            }
        });

    }



}