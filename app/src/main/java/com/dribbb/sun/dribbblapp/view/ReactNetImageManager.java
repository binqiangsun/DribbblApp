package com.dribbb.sun.dribbblapp.view;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by sunbinqiang on 31/10/2016.
 */

public class ReactNetImageManager extends SimpleViewManager<NetworkImageView> {
    @Override
    public String getName() {
        return "NetImageView";
    }

    @Override
    protected NetworkImageView createViewInstance(ThemedReactContext reactContext) {
        return new NetworkImageView(reactContext);
    }

    @ReactProp(name="imageUrl")
    public void setImageUrl(NetworkImageView imageView, String imageUrl){
        imageView.setImageUrl(imageUrl);
    }



}
