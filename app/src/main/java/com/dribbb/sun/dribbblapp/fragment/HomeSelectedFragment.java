package com.dribbb.sun.dribbblapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.dribbb.sun.service.api.ApiService;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ListRecyclerViewAdapter;
import com.dribbb.sun.dribbblapp.base.BaseFragment;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.viewholder.SelectedViewHolder;
import com.dribbb.sun.model.Shot;
import com.google.gson.Gson;

import org.json.JSONArray;

/**
 * Created by sunbinqiang on 16/2/27.
 * 首页精选
 */
public class HomeSelectedFragment extends BaseFragment {

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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        final SelectedAdapter adapter = new SelectedAdapter();
        adapter.setSwipeRefreshLayout(swipeRefreshLayout);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.reset(false);
            }
        });

    }


    private class SelectedAdapter extends ListRecyclerViewAdapter<Shot>{

        @Override
        protected String getRequestUrl() {
            return ApiService.SELECTING_SHOTS_URL;
        }

        @Override
        protected Shot[] getResult(Gson gson, JSONArray object) {
            return gson.fromJson(object.toString(), Shot[].class);
        }

        @Override
        protected BaseViewHolder onCreateItemView(ViewGroup parent) {
            return new SelectedViewHolder(getContext(), parent);
        }

        @Override
        protected void onBindItemView(RecyclerView.ViewHolder holder, int position) {
            ((SelectedViewHolder)holder).setShots(getList().get(position));
        }


    }



}
