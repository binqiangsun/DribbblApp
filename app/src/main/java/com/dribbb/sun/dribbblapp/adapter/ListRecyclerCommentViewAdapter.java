package com.dribbb.sun.dribbblapp.adapter;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.model.Comment;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by sunbinqiang on 8/20/16.
 */

public abstract class ListRecyclerCommentViewAdapter extends ListRecyclerViewAdapter<Comment> {


    @Override
    boolean loadNext() {
        if(request != null){
            return false;
        }

        ServiceFactory.toSubscribe(getObservable(), new Subscriber<Comment[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                requestFailed();
            }

            @Override
            public void onNext(Comment[] resultList) {
                requestSuccess(resultList);
            }
        });
        return true;
    }

    protected abstract Observable<Comment[]> getObservable();
}
