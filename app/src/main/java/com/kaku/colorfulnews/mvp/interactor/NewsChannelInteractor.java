package com.kaku.colorfulnews.mvp.interactor;

import com.kaku.colorfulnews.greendao.NewsChannelTable;
import com.kaku.colorfulnews.listener.RequestCallBack;

import rx.Subscription;


public interface NewsChannelInteractor<T> {
    Subscription lodeNewsChannels(RequestCallBack<T> callback);

    void swapDb(int fromPosition,int toPosition);

    void updateDb(NewsChannelTable newsChannel, boolean isChannelMine);
}
