
package com.kaku.colorfulnews.di.module;

import android.app.Activity;
import android.content.Context;

import com.kaku.colorfulnews.di.scope.ContextLife;
import com.kaku.colorfulnews.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context ProvideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity ProvideActivity() {
        return mActivity;
    }
}
