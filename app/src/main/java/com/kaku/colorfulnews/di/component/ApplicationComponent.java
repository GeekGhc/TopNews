
package com.kaku.colorfulnews.di.component;

import android.content.Context;

import com.kaku.colorfulnews.di.module.ApplicationModule;
import com.kaku.colorfulnews.di.scope.ContextLife;
import com.kaku.colorfulnews.di.scope.PerApp;

import dagger.Component;


@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();

}

