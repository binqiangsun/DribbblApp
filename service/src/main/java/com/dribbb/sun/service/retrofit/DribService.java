package com.dribbb.sun.service.retrofit;

import com.dribbb.sun.model.Comment;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.model.ShotResult;
import com.dribbb.sun.model.Token;
import com.dribbb.sun.model.User;
import com.dribbb.sun.model.response.LikeResponse;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by sunbinqiang on 8/17/16.
 */

public interface DribService {

    @POST("https://dribbble.com/oauth/token")
    Observable<Token> getUser(@Query("client_id") String clientId,
                              @Query("client_secret") String clientSecret,
                              @Query("code") String code);

    @GET("users/{id}")
    Observable<User> getUser(@Path("id") int userId);

    @GET("shots")
    Observable<Shot[]> getShots(@Query("page") String page,
                                @QueryMap Map<String, String> queryMap);


    @GET("users/{id}/likes")
    Observable<ShotResult[]> getLikes(@Path("id") int id,
                                      @Query("page") String page);
    @GET("users/{id}/shots")
    Observable<Shot[]> getShots(@Path("id") int id,
                                  @Query("page") String page);
    @GET("users/{id}/followers")
    Observable<User[]> getFollowers(@Path("id") int id,
                                    @Query("page") String page);
    @GET("users/{id}/following")
    Observable<User[]> getFollowings(@Path("id") int id,
                                    @Query("page") String page);



    @GET("shots/{id}/comments")
    Observable<Comment[]> getComments(@Path("id") int id,
                                      @Query("page") String page);

    @GET("user")
    Observable<User> getUser(@Query("access_token") String token);

    //是否喜欢
    @GET("shots/{id}/like")
    Observable<LikeResponse> getLike(@Path("id") int id);

    //喜欢
    @POST("shots/{id}/like")
    Observable<LikeResponse> postLike(@Path("id") int id);

    //取消喜欢
    @DELETE("shots/{id}/like")
    Observable<LikeResponse> deleteLike(@Path("id") int id);

    //评论
    @POST("shots/{id}/comment")
    Observable<Shot> postComment(@Path("id") int id, @Body String comment);
}
