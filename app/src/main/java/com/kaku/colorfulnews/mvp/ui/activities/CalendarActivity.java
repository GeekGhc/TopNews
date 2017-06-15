package com.kaku.colorfulnews.mvp.ui.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;

public class CalendarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {

        return R.layout.fragment_calender;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
//        mMsgTv.setAutoLinkMask(Linkify.ALL);
//        mMsgTv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
