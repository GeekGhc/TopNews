
package com.kaku.colorfulnews.mvp.view;

import com.kaku.colorfulnews.mvp.entity.NewsSummary;
import com.kaku.colorfulnews.common.LoadNewsType;
import com.kaku.colorfulnews.mvp.view.base.BaseView;

import java.util.List;


public interface NewsListView extends BaseView {

    void setNewsList(List<NewsSummary> newsSummary, @LoadNewsType.checker int loadType);
}
