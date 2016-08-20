package com.dribbb.sun.dribbblapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.viewholder.SelectedViewHolder;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.service.retrofit.DribService;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by sunbinqiang on 4/10/16.
 */
public class SelectedAdapter extends ListRecyclerShotViewAdapter{

    private Context mContext;
    private String  mRequestUrl;
    private Map<String, String> mQueryMap;
    public SelectedAdapter(Context context, String requestUrl){
        mContext = context;
        mRequestUrl = requestUrl;
    }

    @Override
    protected String getRequestUrl() {
        return mRequestUrl;
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


    @Override
    Observable<Shot[]> getObservable() {
        return ServiceFactory.createRetrofitService(
                DribService.ShotService.class).getShots(String.valueOf(mPage), new HashMap<String, String>());
    }
}
