package com.kaku.colorfulnews.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.kaku.colorfulnews.mvp.view.base.BaseView;


public interface BasePresenter {

//    void onResume();

    void onCreate();

    void attachView(@NonNull BaseView view);

    void onDestroy();

}
