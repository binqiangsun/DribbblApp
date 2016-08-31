package com.dribbb.sun.service.retrofit;

import com.dribbb.sun.model.Comment;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.model.ShotResult;
import com.dribbb.sun.model.Token;
import com.dribbb.sun.model.User;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by sunbinqiang on 8/17/16.
 */

public class DribService {

    public static String CLIENT_ID = "4bb696924129982493144fe6c11560052410112e681aa8c931226a437965521d";
    public static String CLIENT_SECRET = "644ecb9d39bdc6bcdc0b3f6e679c1fd68d92e767b114e290a6fc303e6110b4e5";

    public static String SERVICE_ENDPOINT = "https://api.dribbble.com/v1/";
    public static String TOKEN_URL = "https://dribbble.com/";

    public interface UserService{
        @POST("oauth/token")
        Observable<Token> getUser(@Query("client_id") String clientId,
                                  @Query("client_secret") String clientSecret,
                                  @Query("code") String code);
    }

    public interface SelectedShotService {
        @GET("shots")
        Observable<Shot[]> getShots(@Query("page") String page,
                                    @QueryMap Map<String, String> queryMap);
    }

    public interface UserShotService{
        @GET("users/{id}/likes")
        Observable<ShotResult[]> getLikes(@Path("id") int id,
                                          @Query("page") String page);
        @GET("users/{id}/buckets")
        Observable<ShotResult[]> getBuckets(@Path("id") int id,
                                    @Query("page") String page);
        @GET("users/{id}/shots")
        Observable<ShotResult[]> getShots(@Path("id") int id,
                                      @Query("page") String page);
    }

    public interface CommentService{
        @GET("shots/{id}/comments")
        Observable<Comment[]> getComments(@Path("id") int id,
                                          @Query("page") String page);
    }

    public interface UserInfoService{
        @GET("user")
        Observable<User> getUser(@Query("access_token") String token);
    }


}
