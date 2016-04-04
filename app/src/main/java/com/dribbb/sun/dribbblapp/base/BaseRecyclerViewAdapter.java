package com.dribbb.sun.dribbblapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static class LoadingViewHolder extends BaseViewHolder {

        public LoadingViewHolder(Context context,ViewGroup parent) {
            this(LayoutInflater.from(context).inflate(R.layout.loading_item, parent, false));
        }

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }

    }

}