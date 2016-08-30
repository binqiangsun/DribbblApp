package com.dribbb.sun.dribbblapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.utils.TypeUtils;
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
public class UserShotAdapter extends ListRecyclerShotViewAdapter{

    private Context mContext;
    private int mType;
    private int mUserId;
    private Map<String, String> mQueryMap = new HashMap<>();
    public UserShotAdapter(Context context, int type, int userId){
        mContext = context;
        mType = type;
        mUserId = userId;
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
        switch (mType){
            case TypeUtils.SHOT_BUCKETS:
                return ServiceFactory.createRetrofitService(
                        DribService.UserShotService.class).getBuckets(mUserId, String.valueOf(mPage));
            case TypeUtils.SHOT_LIKES:
                return ServiceFactory.createRetrofitService(
                        DribService.UserShotService.class).getLikes(mUserId, String.valueOf(mPage));
        }
        return null;
    }
}
