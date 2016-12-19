package com.dribbb.sun.dribbblapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public static class LoadingErrorView extends LinearLayout implements
            View.OnClickListener {

        TextView errorText;
        LoadingErrorView.LoadRetry callback;

        static final String DEFAULT_ERROR_MESSAGE = "网络连接失败 点击重新加载";

        public LoadingErrorView(Context context) {
            this(context, null);
        }

        public LoadingErrorView(Context context, AttributeSet attrs) {
            super(context, attrs);
            LayoutInflater.from(context).inflate(R.layout.base_adapter_loading_error_item,this,true);
            LayoutParams lps = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(lps);
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER);
            setMinimumHeight(200);
            errorText = (TextView) this.findViewById(android.R.id.title);
            errorText.setText(DEFAULT_ERROR_MESSAGE);
            setOnClickListener(this);
        }

        public void setErrorMessage(String errorMsg) {
            if (errorText != null) {
                errorText.setText(errorMsg);
            }
        }

        public void setCallBack(LoadingErrorView.LoadRetry callback) {
            this.callback = callback;
        }

        @Override
        public void onClick(View v) {
            if (callback != null)
                callback.loadRetry(v);
        }

        public interface LoadRetry {
            void loadRetry(View v);
        }
    }

    public static class ErrorViewHolder extends BaseViewHolder {

        public LoadingErrorView loadingErrorView;

        public ErrorViewHolder(Context context) {
            super(new LoadingErrorView(context));
        }


        public void setRetry(LoadingErrorView.LoadRetry callback) {
            loadingErrorView.setCallBack(callback);
        }

        public void setRetry(String errorMsg, LoadingErrorView.LoadRetry callback) {
            loadingErrorView.setErrorMessage(errorMsg);
            loadingErrorView.setCallBack(callback);
        }
    }

}