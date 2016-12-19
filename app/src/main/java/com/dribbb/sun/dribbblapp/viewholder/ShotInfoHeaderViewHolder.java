package com.dribbb.sun.dribbblapp.viewholder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.activity.UserInfoActivity;
import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.databinding.ShotInfoHeadViewBinding;
import com.dribbb.sun.dribbblapp.instance.FrescoManager;
import com.dribbb.sun.model.Shot;

/**
 * Created by sunbinqiang on 6/18/16.
 */

public class ShotInfoHeaderViewHolder extends BaseViewHolder {

    ShotInfoHeadViewBinding binding;
    private Context mContext;
    private Shot mShot;

    public ShotInfoHeaderViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.shot_info_head_view);
        binding = DataBindingUtil.bind(itemView);
        mContext = context;
    }

    public void setShot(Shot shot){
        mShot = shot;
        binding.setShot(shot);
        binding.setShot(mShot);
        if(!TextUtils.isEmpty(mShot.getDescription())) {
            binding.shotTextTv.setText(Html.fromHtml(mShot.getDescription()));
        }
        FrescoManager.getInstance().setCircleImageSrc(binding.authorDraweeView, mShot.getUser().getAvatar_url(), 0, 0, R.color.gray_image_background);
        binding.setClickHandlers(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoActivity.startUserInfoActivity(mContext, mShot.getUser().getId());
            }
        });
        binding.executePendingBindings();
    }
}
