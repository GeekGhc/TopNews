package com.kaku.colorfulnews.mvp.presenter;

import com.kaku.colorfulnews.mvp.presenter.base.BasePresenter;


public interface PhotoPresenter extends BasePresenter {
    void refreshData();

    void loadMore();
}
