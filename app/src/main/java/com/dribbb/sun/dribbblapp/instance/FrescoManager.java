package com.dribbb.sun.dribbblapp.instance;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * Created by sunbinqiang on 16/3/2.
 */
public class FrescoManager {

    private static FrescoManager mInstance = null;
    private static int DISK_CACHE_SIZE_HIGH = 1024*1024*80;
    private static int DISK_CACHE_SIZE_LOW = 1024*1024*40;
    private static int DISK_CACHE_SIZE_VERY_LOW = 1024*1024*20;
    private static GenericDraweeHierarchy mHierarchy;


    public static FrescoManager getInstance(){
        if(mInstance == null){
            mInstance = new FrescoManager();
        }
        return mInstance;
    }

    /**
     * 初始化，在程序一开始运行的时候
     * @param context
     * @param diskCacheUniqueName
     */
    public void initFresco(Context context, String diskCacheUniqueName){
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
                .setMaxCacheSize(DISK_CACHE_SIZE_HIGH)
                .setMaxCacheSizeOnLowDiskSpace(DISK_CACHE_SIZE_LOW)
                .setMaxCacheSizeOnVeryLowDiskSpace(DISK_CACHE_SIZE_VERY_LOW)
                .setBaseDirectoryName(diskCacheUniqueName)
                .setBaseDirectoryPath(getDiskCacheDir(context, diskCacheUniqueName))
                .setVersion(getAppVersion(context))
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(context, config);
    }

    /**
     * 建议使用该函数， 传递宽高值, 可以减少由于压缩图片引起的内存损耗
     * @param draweeView
     * @param strSrc
     * @param width
     * @param height
     */
    public void setImageSrc(final SimpleDraweeView draweeView, String strSrc, int width, int height){
        if(TextUtils.isEmpty(strSrc)){
            return;
        }
        Uri uri = Uri.parse(strSrc);
        setImageSrc(draweeView, uri, width, height);
    }

    public void setImageSrc(final SimpleDraweeView draweeView, Uri uri, int width, int height) {
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        if(width > 0 && height > 0){
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = builder.build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 圆形头像
     * @param draweeView
     * @param src
     * @param failedResId 背景颜色id
     */
    public void setCircleImageSrc(SimpleDraweeView draweeView, String src, int width, int height, int failedResId){
        RoundingParams roundingParams = RoundingParams.asCircle();
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(draweeView.getResources());
        builder = builder.setRoundingParams(roundingParams);
        if(failedResId > 0) {
            builder = builder.setFailureImage(draweeView.getResources().getDrawable(failedResId));  //失败后默认图片
        }
        GenericDraweeHierarchy hierarchy = builder.build();
        draweeView.setHierarchy(hierarchy);

        setImageSrc(draweeView, src, width, height);
    }

    public void setLowImageSrc(SimpleDraweeView draweeView, String lowSrc, String highSrc){
        Uri lowUri = Uri.parse(lowSrc);
        Uri highUri = Uri.parse(highSrc);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowUri))
                .setImageRequest(ImageRequest.fromUri(highUri))
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 圆角图形
     * @param draweeView
     * @param src
     * @param width
     * @param height
     * @param failedResId
     */
    public void setRoundImageSrc(SimpleDraweeView draweeView, String src, int width, int height, float radius, int failedResId){
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(draweeView.getResources());
        builder = builder.setRoundingParams(roundingParams);
        if(failedResId > 0) {
            builder = builder.setFailureImage(draweeView.getResources().getDrawable(failedResId));  //失败后默认图片
        }
        GenericDraweeHierarchy hierarchy = builder.build();
        draweeView.setHierarchy(hierarchy);

        setImageSrc(draweeView, src, width, height);
    }



    /**
     *
     * @param context
     * @param uri
     * @param width          如果图片尺寸不会导致outofmemory， width ＝ 0
     * @param height         同上
     * @param dataSubscriber
     */
    public void getBitmap(Context context, Uri uri, int width, int height, DataSubscriber dataSubscriber){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        if(width > 0 && height > 0){
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = builder.build();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(request, context);
        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }


    /**
     * 设置加载失败 重试显示的图
     * @param draweeView
     */
    public void setRetryImage(SimpleDraweeView draweeView){
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(draweeView.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .build();
        draweeView.setHierarchy(hierarchy);
    }

    public long getDiskCacheSize(){
        long size = Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        return ((size < 0) ? 0 : size);
    }

    public void clearDiskCache(){
        Fresco.getImagePipelineFactory().getMainDiskStorageCache().clearAll();
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else if(context.getCacheDir() != null) {
            cachePath = context.getCacheDir().getPath();
        }else{
            cachePath = "";
        }
        try {
            File file = new File(cachePath + File.separator + uniqueName);
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getAppVersion(Context context){
        try{
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public GenericDraweeHierarchy getHierarchy(Resources res){
        if(mHierarchy == null) {
            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(res);
            mHierarchy = builder
                    .build();
        }
        return mHierarchy;
    }

}
