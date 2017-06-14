package com.kaku.colorfulnews.mvp.presenter.impl;

import com.kaku.colorfulnews.greendao.NewsChannelTable;
import com.kaku.colorfulnews.mvp.interactor.NewsInteractor;
import com.kaku.colorfulnews.mvp.interactor.impl.NewsInteractorImpl;
import com.kaku.colorfulnews.mvp.presenter.NewsPresenter;
import com.kaku.colorfulnews.mvp.presenter.base.BasePresenterImpl;
import com.kaku.colorfulnews.mvp.view.NewsView;

import java.util.List;

import javax.inject.Inject;


public class NewPresenterImpl extends BasePresenterImpl<NewsView, List<NewsChannelTable>>
        implements NewsPresenter {

    private NewsInteractor<List<NewsChannelTable>> mNewsInteractor;

    @Inject
    public NewPresenterImpl(NewsInteractorImpl newsInteractor) {
        mNewsInteractor = newsInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsChannels();
    }

    private void loadNewsChannels() {
        mSubscription = mNewsInteractor.lodeNewsChannels(this);
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void onChannelDbChanged() {
        loadNewsChannels();
    }
}
