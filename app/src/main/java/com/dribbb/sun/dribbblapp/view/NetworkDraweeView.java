package com.dribbb.sun.dribbblapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.dribbb.sun.dribbblapp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import static android.R.attr.radius;

/**
 * Created by sunbinqiang on 7/26/16.
 */

public class NetworkDraweeView extends SimpleDraweeView {

    private Context mContext;
    private boolean isGifEnable;
    private int roundRadius ;
    private boolean isCircle;
    private String lowImageUrl = null;

    public NetworkDraweeView(Context context) {
        this(context, null);
    }

    public NetworkDraweeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetworkDraweeView, defStyle, 0);
        isGifEnable = a.getBoolean(R.styleable.NetworkDraweeView_isGifEnable, false);
        roundRadius = a.getInteger(R.styleable.NetworkDraweeView_roundRadius, 0);
        isCircle = a.getBoolean(R.styleable.NetworkDraweeView_isCircle, false);
        lowImageUrl = a.getString(R.styleable.NetworkDraweeView_lowImageUrl);
    }

    public void setImageUrl(String imageUrl) {
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl));

        PipelineDraweeControllerBuilder controllerBuilder =  Fresco.newDraweeControllerBuilder();
        controllerBuilder.setImageRequest(requestBuilder.build());
        if(isCircle){
            this.setHierarchy(new GenericDraweeHierarchyBuilder(this.getResources())
                    .setRoundingParams(RoundingParams.asCircle()).build());
        } else if(roundRadius > 0){
            this.setHierarchy(
                    new GenericDraweeHierarchyBuilder(this.getResources())
                            .setRoundingParams(RoundingParams.fromCornersRadius(radius))
                            .build());
        }
        if(isGifEnable){
            controllerBuilder.setAutoPlayAnimations(true);
        }
        if(!TextUtils.isEmpty(lowImageUrl)){
            controllerBuilder.setLowResImageRequest(ImageRequest.fromUri(Uri.parse(lowImageUrl)));
        }

        this.setController(controllerBuilder.setOldController(this.getController())
                .build());

    }

    public void setLowImageUrl(String lowImageUrl) {
        this.lowImageUrl = lowImageUrl;
    }
}
