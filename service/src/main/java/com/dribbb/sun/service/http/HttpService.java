package com.dribbb.sun.service.http;

import android.os.Handler;
import android.os.Looper;

import com.dribbb.sun.service.ServiceConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
            //byte[] postData = request.body() == null ? new byte[0] : IOUtils.toByteArray(request.body());
            //RequestBody postBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), postData);
            builder.method(request.method(),request.body());
        } else {
            builder.method(request.method(),null);
        }
        return builder.tag(request.method()).build();
    }

    private static class IOUtils {
        public static byte[] toByteArray(InputStream input)
                throws IOException {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            copy(input, output);
            return output.toByteArray();
        }

        private static int copy(InputStream input, OutputStream output)
                throws IOException {
            long count = copyLarge(input, output);
            if (count > 2147483647L) {
                return -1;
            }
            return (int)count;
        }

        private static long copyLarge(InputStream input, OutputStream output)
                throws IOException {
            byte[] buffer = new byte[4096];
            long count = 0L;
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            return count;
        }
    }
}
