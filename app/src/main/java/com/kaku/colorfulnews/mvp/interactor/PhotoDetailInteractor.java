package com.kaku.colorfulnews.mvp.interactor;

import com.kaku.colorfulnews.listener.RequestCallBack;

import rx.Subscription;


public interface PhotoDetailInteractor<T> {
    Subscription saveImageAndGetImageUri(RequestCallBack<T> listener, String url);
}
