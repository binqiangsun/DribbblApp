package com.dribbb.sun.service.http;

import com.dribbb.sun.service.ServiceConfig;

import okhttp3.Request;

/**
 * Created by sunbinqiang on 16/2/2.
 */
public class HttpRequest {

    public static Request mapiGet(String apiName){
        return new Request.Builder().url(ServiceConfig.DRIBBBLE_URL + apiName).build();
    }

}
