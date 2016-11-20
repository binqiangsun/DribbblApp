package com.dribbb.sun.dribbblapp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ListRecyclerViewAdapter;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.ActivityShotNativeLayoutBinding;
import com.dribbb.sun.dribbblapp.viewholder.CommentViewholder;
import com.dribbb.sun.dribbblapp.viewholder.ShotInfoHeaderViewHolder;
import com.dribbb.sun.model.Comment;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.model.response.LikeResponse;
import com.dribbb.sun.service.retrofit.ApiFactory;
import com.dribbb.sun.service.retrofit.ServiceFactory;
import com.google.gson.Gson;

import org.json.JSONArray;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by sunbinqiang on 6/14/16.
 */
public class ShotInfoNativeActivity extends BaseActivity<ActivityShotNativeLayoutBinding> {

    private int mShotId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_native_layout;
    }

    @Override
    protected void initViews() {
        setTitle("");
        Shot shot = getIntent().getParcelableExtra("shot");
        mShotId = shot.getId();
        mBinding.setShot(shot);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.setAdapter(new CommentAdapter(shot));
        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getLike(mShotId),
                new Action1<LikeResponse>() {
                    @Override
                    public void call(LikeResponse likeResponse) {
                        mBinding.setIsLike(true);
                        mBinding.setClickHandlers(deleteLikeListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mBinding.setIsLike(false);
                        mBinding.setClickHandlers(postLikeListener);
                    }
                });
    }

    private View.OnClickListener postLikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mBinding.setIsLike(true);
            mBinding.setClickHandlers(deleteLikeListener);
            ServiceFactory.toSubscribe(ApiFactory.getRequestService().postLike(mShotId),
                    new Action1<LikeResponse>() {
                        @Override
                        public void call(LikeResponse likeResponse) {

                        }
                    });
        }
    };

    private View.OnClickListener deleteLikeListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            mBinding.setIsLike(false);
            mBinding.setClickHandlers(postLikeListener);
            ServiceFactory.toSubscribe(ApiFactory.getRequestService().deleteLike(mShotId),
                    new Action1<LikeResponse>() {
                        @Override
                        public void call(LikeResponse likeResponse) {

                        }
                    });
        }
    };


    private static class CommentAdapter extends ListRecyclerViewAdapter<Comment>{

        private Shot mShot;

        public CommentAdapter(Shot shot){
            mShot = shot;
        }

        @Override
        protected Comment[] getResult(Gson gson, JSONArray object) {
            return gson.fromJson(object.toString(), Comment[].class);
        }

        @Override
        protected BaseViewHolder onCreateItemView(ViewGroup parent) {
            return new CommentViewholder(parent.getContext(), parent);
        }

        @Override
        protected void onBindItemView(RecyclerView.ViewHolder holder, int position) {
            ((CommentViewholder)holder).setComment(getList().get(position));
        }

        @Override
        protected int getHeadViewCount() {
            return 1;
        }

        @Override
        protected BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int position) {
            ShotInfoHeaderViewHolder shotInfoHeaderViewHolder = new ShotInfoHeaderViewHolder(parent.getContext(), parent);
            return shotInfoHeaderViewHolder;
        }

        @Override
        protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ShotInfoHeaderViewHolder)holder).setShot(mShot);
        }

        @Override
        public Observable<Comment[]> getObservable() {
            return ApiFactory.getRequestService().getComments(mShot.getId(), String.valueOf(mPage));
        }
    }

}
