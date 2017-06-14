//新闻列表的view
package com.kaku.colorfulnews.mvp.view;

import com.kaku.colorfulnews.greendao.NewsChannelTable;
import com.kaku.colorfulnews.mvp.view.base.BaseView;

import java.util.List;


public interface NewsView extends BaseView {

    void initViewPager(List<NewsChannelTable> newsChannels);
}
