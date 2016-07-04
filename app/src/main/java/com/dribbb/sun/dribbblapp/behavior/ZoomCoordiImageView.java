package com.dribbb.sun.dribbblapp.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by sunbinqiang on 7/2/16.
 */

public class ZoomCoordiImageView extends ImageView {


    public ZoomCoordiImageView(Context context) {
        super(context);
    }

    public ZoomCoordiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomCoordiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class ZoomBehavior extends CoordinatorLayout.Behavior<ZoomCoordiImageView>{
        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, ZoomCoordiImageView child, View target,
                                   int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
            

        }
    }

}
