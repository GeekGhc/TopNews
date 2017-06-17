
package com.kaku.colorfulnews.mvp.presenter;

import com.kaku.colorfulnews.common.PhotoRequestType;
import com.kaku.colorfulnews.mvp.presenter.base.BasePresenter;


public interface PhotoDetailPresenter extends BasePresenter {
    void handlePicture(String imageUrl, @PhotoRequestType.PhotoRequestTypeChecker int type);
}
