package com.kaku.colorfulnews.mvp.interactor;

import com.kaku.colorfulnews.listener.RequestCallBack;

import rx.Subscription;


public interface NewsDetailInteractor<T> {
    Subscription loadNewsDetail(RequestCallBack<T> callBack, String postId);
}
