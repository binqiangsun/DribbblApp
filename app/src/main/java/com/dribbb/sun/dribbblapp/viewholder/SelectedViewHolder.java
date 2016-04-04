package com.dribbb.sun.dribbblapp.viewholder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.SelectedRecyclerViewBinding;
import com.dribbb.sun.dribbblapp.instance.FrescoManager;
import com.dribbb.sun.model.Shot;

/**
 * Created by sunbinqiang on 16/2/28.
 */
public class SelectedViewHolder extends BaseViewHolder {

    SelectedRecyclerViewBinding binding;

    public SelectedViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.selected_recycler_view);
        binding = DataBindingUtil.bind(itemView);
    }

    public void setShots(Shot shot){
        binding.setShot(shot);
        Log.i("image path", shot.getImages().getTeaser());
        FrescoManager.getInstance().setImageSrc(binding.imageDraweeView, shot.getImages().getTeaser(), 0, 0);
    }
}
