package com.kaku.colorfulnews.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.kaku.colorfulnews.listener.RequestCallBack;
import com.kaku.colorfulnews.mvp.view.base.BaseView;
import com.kaku.colorfulnews.utils.MyUtils;

import rx.Subscription;


public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallBack<E> {
    protected T mView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        MyUtils.cancelSubscription(mSubscription);
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        // TODO?
        mView = (T) view;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void success(E data) {
        mView.hideProgress();
    }

    @Override
    public void onError(String errorMsg) {
        mView.hideProgress();
        mView.showMsg(errorMsg);
    }

}
