package com.dribbb.sun.dribbblapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.instance.GlideInstance;
import com.dribbb.sun.dribbblapp.utils.VariableUtils;

/**
 * Created by sunbinqiang on 7/20/16.
 */

public class NetworkImageView extends ImageView {

    private Context mContext;
    private boolean isGifEnable;
    private int roundRadius ;
    private boolean isCircle;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetworkImageView, defStyleAttr, 0);
        String imageUrl = a.getString(R.styleable.NetworkImageView_imageUrl);
        isGifEnable = a.getBoolean(R.styleable.NetworkImageView_isGifEnable, false);
        roundRadius = a.getInteger(R.styleable.NetworkImageView_roundRadius, 0);
        isCircle = a.getBoolean(R.styleable.NetworkImageView_isCircle, false);
    }

    public void setImageUrl(String imageUrl) {
        switch (VariableUtils.IMAGE_TYPE){
            case TYPEGLIDE:
                setGlideImageUrl(imageUrl);
                break;
            case TYPEFRESCO:
                break;
        }
    }

    public void setGlideImageUrl(String imageUrl){
        if(TextUtils.isEmpty(imageUrl)) return;
        DrawableTypeRequest glideRequest = Glide.with(mContext).load(imageUrl);
        if(!isGifEnable && imageUrl.endsWith("gif")) {
            glideRequest.asBitmap();
        }
        if(roundRadius > 0) {
            glideRequest.transform(new GlideInstance.GlideRoundTransform(mContext, roundRadius));
        }else if(isCircle){
            glideRequest.transform(new GlideInstance.CircleTransform(mContext));
        }else{
            glideRequest.centerCrop();
        }
        glideRequest.into(this);
    }

    private void setFrescoImageUrl(String imageUrl){
        if(TextUtils.isEmpty(imageUrl)) return;

    }

}
