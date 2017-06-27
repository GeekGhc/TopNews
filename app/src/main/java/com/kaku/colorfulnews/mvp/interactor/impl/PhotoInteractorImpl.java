package com.kaku.colorfulnews.mvp.interactor.impl;

import com.kaku.colorfulnews.App;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.common.HostType;
import com.kaku.colorfulnews.listener.RequestCallBack;
import com.kaku.colorfulnews.mvp.entity.GirlData;
import com.kaku.colorfulnews.mvp.entity.PhotoGirl;
import com.kaku.colorfulnews.mvp.interactor.PhotoInteractor;
import com.kaku.colorfulnews.repository.network.RetrofitManager;
import com.kaku.colorfulnews.utils.TransformUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;


public class PhotoInteractorImpl implements PhotoInteractor<List<PhotoGirl>> {

    @Inject
    public PhotoInteractorImpl() {
    }

    @Override
    public Subscription loadPhotos(final RequestCallBack<List<PhotoGirl>> listener, int size, int page) {
        return RetrofitManager.getInstance(HostType.GANK_GIRL_PHOTO)
                .getPhotoListObservable(size, page)
                .map(new Func1<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                })
                .compose(TransformUtils.<List<PhotoGirl>>defaultSchedulers())
                .subscribe(new Subscriber<List<PhotoGirl>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(App.getAppContext().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(List<PhotoGirl> photoGirls) {
                        listener.success(photoGirls);
                    }
                })
                ;
    }
}
