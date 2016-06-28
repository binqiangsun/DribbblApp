package com.dribbb.sun.dribbblapp.activity;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.dribbb.sun.service.api.ApiService;
import com.google.gson.Gson;

import org.json.JSONArray;

/**
 * Created by sunbinqiang on 6/14/16.
 */
public class ShotInfoNativeActivity extends BaseActivity<ActivityShotNativeLayoutBinding> {

    private Shot mShot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_native_layout;
    }

    @Override
    protected void initViews() {
        mShot = getIntent().getParcelableExtra("shot");
        mBinding.shotImg.setImageURI(Uri.parse(mShot.getHdipImage()));
        //mBinding.collapsingToolbarLayout.setTitle(mShot.getTitle());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.setAdapter(new ShotInfoAdapter());
    }



    private class ShotInfoAdapter extends ListRecyclerViewAdapter<Comment>{

        @Override
        protected String getRequestUrl() {
            return ApiService.API_SERVICE+"/shots/" + mShot.getId() + "/comments";
        }

        @Override
        protected Comment[] getResult(Gson gson, JSONArray object) {
            return gson.fromJson(object.toString(), Comment[].class);
        }

        @Override
        protected BaseViewHolder onCreateItemView(ViewGroup parent) {
            return new CommentViewholder(ShotInfoNativeActivity.this, parent);
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
            return new ShotInfoHeaderViewHolder(ShotInfoNativeActivity.this, parent);
        }

        @Override
        protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ShotInfoHeaderViewHolder)holder).setShot(mShot);
        }
    }

}
