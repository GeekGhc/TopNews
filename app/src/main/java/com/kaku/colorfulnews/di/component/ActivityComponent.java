//activity进程注入
package com.kaku.colorfulnews.di.component;

import android.app.Activity;
import android.content.Context;

import com.kaku.colorfulnews.di.module.ActivityModule;
import com.kaku.colorfulnews.di.scope.ContextLife;
import com.kaku.colorfulnews.di.scope.PerActivity;
import com.kaku.colorfulnews.mvp.ui.activities.NewsActivity;
import com.kaku.colorfulnews.mvp.ui.activities.NewsChannelActivity;
import com.kaku.colorfulnews.mvp.ui.activities.NewsDetailActivity;
import com.kaku.colorfulnews.mvp.ui.activities.NewsPhotoDetailActivity;
import com.kaku.colorfulnews.mvp.ui.activities.PhotoActivity;
import com.kaku.colorfulnews.mvp.ui.activities.PhotoDetailActivity;


import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();


    void inject(NewsActivity newsActivity);

    void inject(NewsDetailActivity newsDetailActivity);

    void inject(NewsChannelActivity newsChannelActivity);

    void inject(NewsPhotoDetailActivity newsPhotoDetailActivity);

    void inject(PhotoActivity photoActivity);

    void inject(PhotoDetailActivity photoDetailActivity);
}
