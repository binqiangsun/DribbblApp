package com.dribbb.sun.core.service;


import com.dribbb.sun.service.ServiceConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) {

        String GET_API_URL = "https://api.dribbble.com/v1/";
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder builder = original.newBuilder()
                        .method(original.method(), original.body())
                        //添加请求头部
                        .header("Authorization", "Bearer " + ServiceConfig.ACCESS_TOKEN);

                return chain.proceed(builder.build());
            }
        });

        OkHttpClient okHttpClient = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GET_API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public static <T> T createPostRetrofitService(final Class<T> clazz) {

        String TOKEN_URL = "https://dribbble.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TOKEN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();


    public static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
