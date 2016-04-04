package com.dribbb.sun.service.http;

import android.os.Handler;
import android.os.Looper;

import com.dribbb.sun.service.ServiceConfig;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sunbinqiang on 16/2/1.
 */
public class HttpService {

    private static Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void exec(Request request, final ApiRequestHandler requestHandler){
        final Request httpRequest = buildRequest(request);
        Call call = mOkHttpClient.newCall(httpRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requestHandler.onRequestFailed(httpRequest, null);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                requestHandler.onRequestFinish(httpRequest,response);
            }
        });
    }

    private static Request buildRequest(Request request) {
        Request.Builder builder = new Request.Builder().url(request.url())
                .header("header", "android")
                .addHeader("Authorization", "Bearer " + ServiceConfig.ACCESS_TOKEN)
                .addHeader("x-model", android.os.Build.MODEL)
                .addHeader("x-os-version", android.os.Build.VERSION.RELEASE);
        if ("POST".equals(request.method())) {
            byte[] postData = new byte[0];
            RequestBody postBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), postData);
            builder.method(request.method(),postBody);
        } else {
            builder.method(request.method(),null);
        }
        return builder.tag(request.method()).build();
    }

}
