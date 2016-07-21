package com.dribbb.sun.dribbblapp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.dribbb.sun.dribbblapp.R;

/**
 * Created by sunbinqiang on 7/20/16.
 */

public class NetworkImageView extends ImageView {

    private Context mContext;
    boolean isGifEnable;
    private int roundRadius ;

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
    }

    public void setGifEnable(boolean gifEnable) {
        isGifEnable = gifEnable;
    }

    public void setImageUrl(String imageUrl){
        if(TextUtils.isEmpty(imageUrl)) return;
        DrawableTypeRequest glideRequest = Glide.with(mContext).load(imageUrl);
        if(!isGifEnable && imageUrl.endsWith("gif")) {
            glideRequest.asBitmap();
        }
        if(roundRadius > 0) {
            glideRequest.transform(new GlideRoundTransform(mContext, roundRadius));
        }
        glideRequest.centerCrop().into(this);
    }

    private class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

}
