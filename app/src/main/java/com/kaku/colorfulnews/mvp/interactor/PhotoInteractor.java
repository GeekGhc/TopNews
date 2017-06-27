
package com.kaku.colorfulnews.mvp.interactor;

import com.kaku.colorfulnews.listener.RequestCallBack;

import rx.Subscription;


public interface PhotoInteractor<T> {
    Subscription loadPhotos(RequestCallBack<T> listener, int size, int page);
}
