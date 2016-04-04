package com.dribbb.sun.dribbblapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunbinqiang on 16/2/27.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(Context context, ViewGroup parent, int layoutId) {
        this(context,parent,layoutId,false);
    }

    public BaseViewHolder(Context context, ViewGroup parent, int layoutId, boolean attachToRoot){
        super(LayoutInflater.from(context).inflate(layoutId,parent,attachToRoot));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }


}
