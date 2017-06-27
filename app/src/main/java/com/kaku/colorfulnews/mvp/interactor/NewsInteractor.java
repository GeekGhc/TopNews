package com.kaku.colorfulnews.mvp.interactor;

import com.kaku.colorfulnews.listener.RequestCallBack;

import rx.Subscription;


public interface NewsInteractor<T> {
    Subscription lodeNewsChannels(RequestCallBack<T> callback);
}
