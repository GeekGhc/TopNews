
package com.kaku.colorfulnews.mvp.presenter.impl;

import com.kaku.colorfulnews.mvp.entity.NewsDetail;
import com.kaku.colorfulnews.mvp.interactor.NewsDetailInteractor;
import com.kaku.colorfulnews.mvp.interactor.impl.NewsDetailInteractorImpl;
import com.kaku.colorfulnews.mvp.presenter.NewsDetailPresenter;
import com.kaku.colorfulnews.mvp.presenter.base.BasePresenterImpl;
import com.kaku.colorfulnews.mvp.view.NewsDetailView;

import javax.inject.Inject;


public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView, NewsDetail>
        implements NewsDetailPresenter {
    private NewsDetailInteractor<NewsDetail> mNewsDetailInteractor;
    private String mPostId;

    @Inject
    public NewsDetailPresenterImpl(NewsDetailInteractorImpl newsDetailInteractor) {
        mNewsDetailInteractor = newsDetailInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSubscription = mNewsDetailInteractor.loadNewsDetail(this, mPostId);

    }

    @Override
    public void success(NewsDetail data) {
        super.success(data);
        mView.setNewsDetail(data);
    }

    @Override
    public void setPosId(String postId) {
        mPostId = postId;
    }
}
