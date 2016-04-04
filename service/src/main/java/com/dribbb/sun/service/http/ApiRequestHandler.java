package com.dribbb.sun.service.http;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sunbinqiang on 16/2/2.
 */
public class ApiRequestHandler implements RequestHandler<Request,Response> {


    @Override
    public void onRequestStart(Request req) {

    }

    @Override
    public void onRequestProgress(Request req, int count, int total) {

    }

    @Override
    public void onRequestFinish(Request req, Response resp) throws IOException {

    }

    @Override
    public void onRequestFailed(Request req, Response resp) {

    }
}
