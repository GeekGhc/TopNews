
package com.kaku.colorfulnews.mvp.view;

import com.kaku.colorfulnews.mvp.entity.NewsDetail;
import com.kaku.colorfulnews.mvp.view.base.BaseView;


public interface NewsDetailView extends BaseView {
    void setNewsDetail(NewsDetail newsDetail);
}
