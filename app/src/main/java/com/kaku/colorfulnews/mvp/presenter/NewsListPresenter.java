
package com.kaku.colorfulnews.mvp.presenter;

import com.kaku.colorfulnews.mvp.presenter.base.BasePresenter;


public interface NewsListPresenter extends BasePresenter {
    void setNewsTypeAndId(String newsType, String newsId);

    void refreshData();

    void loadMore();
}
