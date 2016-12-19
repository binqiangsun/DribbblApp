package com.dribbb.sun.service.retrofit;


import android.widget.Toast;

import com.dribbb.sun.service.LibApplication;
import com.dribbb.sun.service.config.ServiceConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceConfig.GET_API_URL)
                .client(HttpClient.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }



    public static <T> T createRetrofitServiceNoHead(final Class<T> clazz) {

        String GET_API_URL = "https://api.dribbble.com/v1/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GET_API_URL)
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
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
                        throw new HttpException(400, throwable.getMessage());
                    }
                })
                .subscribe(s);
    }

    public static <T> void toSubscribe(Observable<T> o, Action1<T> action1, Action1<Throwable> errorAction) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
                        throw new HttpException(400, throwable.getMessage());
                    }
                })
                .subscribe(action1, errorAction);
    }

    public static <T> void toSubscribe(Observable<T> o, Action1<T> action1) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
                        throw new HttpException(400, throwable.getMessage());
                    }
                })
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(LibApplication.instance(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }



    public static String getAccessToken(){
        if(LibApplication.instance().accountService().isLogin()){
            return LibApplication.instance().accountService().token();
        }else{
            return ServiceConfig.ACCESS_TOKEN;
        }
    }

}
