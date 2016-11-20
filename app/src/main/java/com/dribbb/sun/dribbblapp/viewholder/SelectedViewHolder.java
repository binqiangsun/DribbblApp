package com.dribbb.sun.dribbblapp.viewholder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.activity.ShotInfoNativeActivity;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.SelectedRecyclerViewBinding;
import com.dribbb.sun.model.Shot;

/**
 * Created by sunbinqiang on 16/2/28.
 */
public class SelectedViewHolder extends BaseViewHolder implements View.OnClickListener {

    SelectedRecyclerViewBinding binding;
    private Context mContext;
    private Shot mShot;

    public SelectedViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.selected_recycler_view);
        binding = DataBindingUtil.bind(itemView);
        mContext = context;
    }

    public void setShots(Shot shot){
        mShot = shot;
        binding.setShot(shot);
        binding.setClickHandlers(this);
        binding.executePendingBindings();
    }


    @Override
    public void onClick(View v) {
        startInfo();
    }

    private void startInfo(){
        Intent intent = new Intent(mContext, ShotInfoNativeActivity.class);
        intent.putExtra("shot", mShot);
        intent.putExtra("shotId", String.valueOf(mShot.getId()));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) mContext, binding.imageDraweeView, mContext.getString(R.string.tran_image));
            mContext.startActivity(intent, transitionActivityOptions.toBundle());
        }else {
            mContext.startActivity(intent);
        }
    }
}
