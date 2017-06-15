package com.kaku.colorfulnews.mvp.view;

import com.kaku.colorfulnews.common.LoadNewsType;
import com.kaku.colorfulnews.mvp.entity.PhotoGirl;
import com.kaku.colorfulnews.mvp.view.base.BaseView;

import java.util.List;


public interface PhotoView extends BaseView {
    void setPhotoList(List<PhotoGirl> photoGirls, @LoadNewsType.checker int loadType);
}
