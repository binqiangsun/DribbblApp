package com.dribbb.sun.service.retrofit;

/**
 * Created by sunbinqiang on 19/11/2016.
 */

public class ApiFactory {

    public static DribService getRequestService(){
        return ServiceFactory.createRetrofitService(
                DribService.class);
    }

}
