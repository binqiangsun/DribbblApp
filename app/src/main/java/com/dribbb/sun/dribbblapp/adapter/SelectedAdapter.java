package com.dribbb.sun.dribbblapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.viewholder.SelectedViewHolder;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.service.api.ApiService;
import com.google.gson.Gson;

import org.json.JSONArray;

/**
 * Created by sunbinqiang on 4/10/16.
 */
public class SelectedAdapter extends ListRecyclerViewAdapter<Shot>{

    private Context mContext;
    public SelectedAdapter(Context context){
        mContext = context;
    }

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
        return new SelectedViewHolder(mContext, parent);
    }

    @Override
    protected void onBindItemView(RecyclerView.ViewHolder holder, int position) {
        ((SelectedViewHolder)holder).setShots(getList().get(position));
    }


}
