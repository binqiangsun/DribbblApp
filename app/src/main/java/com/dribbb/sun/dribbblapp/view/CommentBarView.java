package com.dribbb.sun.dribbblapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.databinding.CommentBarViewBinding;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.service.retrofit.ApiFactory;
import com.dribbb.sun.service.retrofit.ServiceFactory;

import rx.functions.Action1;

/**
 * Created by sunbinqiang on 20/11/2016.
 */

public class CommentBarView extends LinearLayout {

    private CommentBarViewBinding mBinding;
    private int mShotId;
    private OnCommentSuccessListener mCommentListener;

    public CommentBarView(Context context) {
        this(context, null);
    }

    public CommentBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.comment_bar_view, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommentBarView, defStyleAttr, 0);
        mShotId = a.getInt(R.styleable.CommentBarView_shotId, 0);
        mBinding.setClickHandlers(commentClickListener);
    }

    public void setShotId(int id){
        mShotId = id;
    }

    private OnClickListener commentClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            String strComment = mBinding.commentEt.getText().toString();
            if(!TextUtils.isEmpty(strComment)){
                ServiceFactory.toSubscribe(ApiFactory.getRequestService().postComment(mShotId, strComment),
                        new Action1<Shot>() {
                            @Override
                            public void call(Shot shot) {
                                //comment success
                                if(mCommentListener != null){
                                    mCommentListener.commentSuccess();
                                }
                            }
                        });

            }
        }
    };

    public void setOnCommentListener(OnCommentSuccessListener onCommentListener){
        this.mCommentListener = onCommentListener;
    }

    public static interface OnCommentSuccessListener{
        void commentSuccess();
    }




}
