package com.kaku.colorfulnews.mvp.interactor;

import com.kaku.colorfulnews.listener.RequestCallBack;

import rx.Subscription;


public interface NewsListInteractor<T> {

    Subscription loadNews(RequestCallBack<T> listener, String type, String id, int startPage);
}
