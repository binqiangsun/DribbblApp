package com.dribbb.sun.service.retrofit;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.dribbb.sun.service.retrofit.ServiceFactory.getAccessToken;

/**
 * Created by sunbinqiang on 30/11/2016.
 */

public class HttpClient {

    private static class ClientHolder{
        private static final OkHttpClient INSTANCE = reBuild();
    }

    public static OkHttpClient getClient(){
        return ClientHolder.INSTANCE;
    }

    private static OkHttpClient reBuild(){
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpClientBuilder
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .addNetworkInterceptor(cacheInterceptor)   //addInterceptor 缓存不生效
                .addInterceptor(cacheInterceptor)
                .build();
    }

    private static Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .method(original.method(), original.body())
                    //添加请求头部
                    .header("Authorization", "Bearer " + getAccessToken());

            return chain.proceed(builder.build());
        }
    };

    /**
     * 拦截器,缓存
     * 1, 仅离线状态下缓存
     * 2, 在线状态下, 根据不同接口可以在ApiService接口中,添加Headers自定义缓存
     */
    private static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365,TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if(!HttpUtils.isNetworkConnected()){
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response response = chain.proceed(request);
            if(HttpUtils.isNetworkConnected()){
                //有网的时候读接口上的@Headers里的配置
                String responseControl = request.cacheControl().toString();
                return response.newBuilder()
                        .header("Cache-Control", TextUtils.isEmpty(responseControl) ? "public, max-stale=600" : responseControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}
