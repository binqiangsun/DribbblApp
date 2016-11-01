package com.dribbb.sun.dribbblapp.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.adapter.ListRecyclerCommentViewAdapter;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.ActivityShotNativeLayoutBinding;
import com.dribbb.sun.dribbblapp.view.ScrollScaleImageView;
import com.dribbb.sun.dribbblapp.viewholder.CommentViewholder;
import com.dribbb.sun.dribbblapp.viewholder.ShotInfoHeaderViewHolder;
import com.dribbb.sun.model.Comment;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.service.retrofit.DribService;
import com.google.gson.Gson;

import org.json.JSONArray;

import rx.Observable;

/**
 * Created by sunbinqiang on 6/14/16.
 */
public class ShotInfoNativeActivity extends BaseActivity<ActivityShotNativeLayoutBinding> {

    private Shot mShot;
    private ShotInfoHeaderViewHolder shotInfoHeaderViewHolder;
    private ScrollScaleImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_native_layout;
    }

    @Override
    protected void initViews() {
        imageView = (ScrollScaleImageView) findViewById(R.id.shot_img);
        mShot = getIntent().getParcelableExtra("shot");
        mBinding.setShot(mShot);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.setAdapter(new ShotInfoAdapter());
        mBinding.recyclerView.addOnScrollListener(scrollListener);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int scrollY = shotInfoHeaderViewHolder.itemView.getTop();
            imageView.setOffset(scrollY);
            //fab.setOffset(fabOffset + scrollY);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // as we animate the main image's elevation change when it 'pins' at it's min height
            // a fling can cause the title to go over the image before the animation has a chance to
            // run. In this case we short circuit the animation and just jump to state.
            //imageView.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING);
        }
    };

    private class ShotInfoAdapter extends ListRecyclerCommentViewAdapter{

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
            shotInfoHeaderViewHolder = new ShotInfoHeaderViewHolder(ShotInfoNativeActivity.this, parent);
            return shotInfoHeaderViewHolder;
        }

        @Override
        protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ShotInfoHeaderViewHolder)holder).setShot(mShot);
        }


        @Override
        protected Observable<Comment[]> getObservable() {
            return ServiceFactory.createRetrofitService(
                    DribService.CommentService.class).getComments(mShot.getId(), String.valueOf(mPage));
        }
    }

}
