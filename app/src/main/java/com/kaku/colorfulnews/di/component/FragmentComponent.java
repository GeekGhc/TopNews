package com.kaku.colorfulnews.di.component;

import android.app.Activity;
import android.content.Context;

import com.kaku.colorfulnews.di.module.FragmentModule;
import com.kaku.colorfulnews.di.scope.ContextLife;
import com.kaku.colorfulnews.di.scope.PerFragment;
import com.kaku.colorfulnews.mvp.ui.fragment.NewsListFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsListFragment newsListFragment);
}
