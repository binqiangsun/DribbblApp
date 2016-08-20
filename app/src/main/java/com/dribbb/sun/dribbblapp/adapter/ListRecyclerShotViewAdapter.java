package com.dribbb.sun.dribbblapp.adapter;

import com.dribbb.sun.core.service.ServiceFactory;
import com.dribbb.sun.model.Shot;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by sunbinqiang on 8/20/16.
 */

public abstract class ListRecyclerShotViewAdapter extends ListRecyclerViewAdapter<Shot> {


    @Override
    boolean loadNext() {
        if(request != null){
            return false;
        }

        ServiceFactory.toSubscribe(getObservable(), new Subscriber<Shot[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                requestFailed();
            }

            @Override
            public void onNext(Shot[] resultList) {
                requestSuccess(resultList);
            }
        });
        return true;
    }

    abstract Observable<Shot[]> getObservable();
}
