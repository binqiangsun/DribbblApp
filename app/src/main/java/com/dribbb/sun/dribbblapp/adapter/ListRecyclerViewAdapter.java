package com.dribbb.sun.dribbblapp.adapter;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.base.BaseRecyclerViewAdapter;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.utils.TypeUtils;
import com.dribbb.sun.service.api.ApiService;
import com.dribbb.sun.service.http.ApiRequestHandler;
import com.dribbb.sun.service.http.HttpService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sunbinqiang on 16/2/3.
 */
public abstract class ListRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Gson gson = new Gson();
    private boolean mIsEnd = false;
    private boolean mIsError = false;
    private List<T> list = new ArrayList<>();
    private Request request;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int mPage = 1;

    public ListRecyclerViewAdapter() {
        super();
        mPage = 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("list_viewtype", String.valueOf(viewType));

        if(isHeaderViewType(viewType)){
            RecyclerView.ViewHolder holder = createHeaderViewHolder(parent, getPositionForHeaderViewType(viewType));
            holder.setIsRecyclable(false);
            return holder;
        }

        switch (viewType){
            case TypeUtils.EMPTY_ID:
            case TypeUtils.ERROR_ID:
            case TypeUtils.LOADING_ID:
                return new BaseRecyclerViewAdapter.LoadingViewHolder(parent.getContext(), parent);
            case TypeUtils.ITEM_ID:
                return onCreateItemView(parent);
        }
        return new BaseViewHolder(parent.getContext(), parent, 0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isPositionInHeaderRange(position)) {
            onBindHeaderViewHolder(holder, position);
            return;
        }
        switch (getItemViewType(position)){
            case TypeUtils.LOADING_ID:
                loadNext();
                break;
            case TypeUtils.EMPTY_ID:
                break;
            case TypeUtils.ERROR_ID:
                break;
            default:
                position -= getHeadViewCount();
                onBindItemView(holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(isEmpty()) count++;
        if((!isEnd()) || isError()) count++;
        count += getHeadViewCount();
        count += getList().size();
        return count;
    }

    @Override
    public int getItemViewType(int position){
        if (hasHeaderViewHolder() && isPositionInHeaderRange(position)) {
            return getViewTypeForHeader(position);
        }

        if (hasHeaderViewHolder()) {
            position -= getHeadViewCount();
        }
        
        if(position < list.size()){
            return TypeUtils.ITEM_ID;
        } else if(isEmpty()){
            return TypeUtils.EMPTY_ID;
        } else if(!isEnd()){
            return TypeUtils.LOADING_ID;
        } else if(!isError()){
            return TypeUtils.ERROR_ID;
        }
        throw  new RuntimeException("unknown item view type for position:"+position);
    }

    private boolean loadNext(){

        if(request != null){
            return false;
        }

        String url = Uri.parse(getRequestUrl()).buildUpon()
                .appendQueryParameter("page", String.valueOf(mPage)).build().toString();
        request = new Request.Builder().url(url)
                .method(ApiService.GET_METHOD, null).build();

        HttpService.exec(request, new ApiRequestHandler() {
            @Override
            public void onRequestFinish(Request req, Response resp) throws IOException {
                super.onRequestFinish(req, resp);
                releaseRequest();
                JSONArray data = null;
                try {
                    data = new JSONArray(new String(resp.body().bytes()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(data != null) {
                    T[] resultList = getResult(gson, data);
                    if (resultList.length == 0){
                        mIsEnd = true;
                    }
                    setResultList(resultList);
                }
                onRequestFinished();
                mPage ++;
            }

            @Override
            public void onRequestFailed(Request req, Response resp) {
                super.onRequestFailed(req, resp);
                releaseRequest();
                onRequestFinished();
            }
        });
        return true;
    }

    private void releaseRequest(){
        request = null;
    }

    protected void setResultList(T[] resultList) {
        if (resultList != null) {
            sortList(list, resultList);
        }
    }

    protected void sortList(List<T> list, T[] newCommingArray) {
        if(newCommingArray == null) return;
        list.addAll(Arrays.asList(newCommingArray));
    }

    protected void onRequestFinished() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    },1000);
                }
                notifyDataSetChanged();
            }
        });
    }

    public void reset(boolean isClear){
        if (isClear) {
            list.clear();
            mIsEnd = false;
            notifyDataSetChanged();
        } else {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
            }
            loadNext();
        }
    }

    protected String getPageParam(){
        return "?page=" + mPage;
    }

    private boolean isError() {
        return mIsError;
    }

    private boolean isEnd() {
        return mIsEnd;
    }

    private boolean isEmpty() {
        return mIsEnd && (list.size() == 0);
    }

    protected abstract String getRequestUrl();

    protected abstract T[] getResult(Gson gson, JSONArray object);

    public List<T> getList() {
        return list;
    }

    protected abstract BaseViewHolder onCreateItemView(ViewGroup parent);

    protected abstract void onBindItemView(RecyclerView.ViewHolder holder, int position);

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    
    //add head view
    protected boolean hasHeaderViewHolder(){
        return getHeadViewCount() > 0;
    }
    
    protected int getHeadViewCount(){
        return 0;
    }

    protected BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int position) {
        return null;
    }

    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private int getPositionForHeaderViewType(int viewType) {
        return viewType & ~TypeUtils.HEADER_FOOTER_TYPE_MASK;
    }

    private boolean isHeaderViewType(int viewType) {
        return hasHeaderViewHolder() && ((viewType & TypeUtils.HEADER_FOOTER_TYPE_MASK) == TypeUtils.HEADER_TYPE);
    }

    private int getViewTypeForHeader(int position) {
        return TypeUtils.HEADER_TYPE | position;
    }

    private boolean isPositionInHeaderRange(int position) {
        return getHeadViewCount() > position;
    }

    private BaseViewHolder createHeaderViewHolder(ViewGroup parent, int position) {
        BaseViewHolder viewHolder = onCreateHeaderViewHolder(parent, position);
        if (viewHolder == null) {
            throw new RuntimeException("postion = " + position + ":HeaderViewHolder can't be null");
        }
        return viewHolder;
    }
}
