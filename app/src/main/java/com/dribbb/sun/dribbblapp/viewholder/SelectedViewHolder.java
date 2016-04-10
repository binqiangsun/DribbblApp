package com.dribbb.sun.dribbblapp.viewholder;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.SelectedRecyclerViewBinding;
import com.dribbb.sun.dribbblapp.instance.FrescoManager;
import com.dribbb.sun.dribbblapp.react.ShotInfoActivity;
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
        Log.i("image path", shot.getImages().getTeaser());
        FrescoManager.getInstance().setImageSrc(binding.imageDraweeView, shot.getImages().getTeaser(), 0, 0);
    }


    @Override
    public void onClick(View v) {
        startInfo();
    }

    private void startInfo(){
        Intent intent = new Intent(mContext, ShotInfoActivity.class);
        intent.putExtra("shotImg", mShot.getImages().getHidpi());
        intent.putExtra("shotText", mShot.getTitle());
        mContext.startActivity(intent);
    }
}
