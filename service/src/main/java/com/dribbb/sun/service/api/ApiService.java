package com.dribbb.sun.service.api;

import com.dribbb.sun.service.http.ApiRequestHandler;
import com.dribbb.sun.service.http.HttpService;

import okhttp3.Request;

/**
 * Created by sunbinqiang on 16/2/2.
 */
public class ApiService {

    public static final String API_SERVICE = "https://api.dribbble.com/v1";
    public static final String GET_METHOD = "GET";
    public static final String FOLLOWING_SHOTS_URL = API_SERVICE + "/user/following/shots";
    public static final String POPULAR_SHOTS_URL = API_SERVICE + "/shots";
    public static final String RECENT_SHOTS_URL = API_SERVICE + "/shots?sort=recent";


    public static final String ACCESS_TOKEN = "acea171b23f1058c2a5de36300f708761f810513e8e083c349fc10ffabee4036";


    public static void getFollowingShots(ApiRequestHandler handler){
        Request request = new Request.Builder().url(FOLLOWING_SHOTS_URL).build();
        HttpService.exec(request, handler);
    }

    public static void getSelectedShots(ApiRequestHandler handler){
        Request request = new Request.Builder().url(POPULAR_SHOTS_URL).build();
        HttpService.exec(request, handler);
    }

}
