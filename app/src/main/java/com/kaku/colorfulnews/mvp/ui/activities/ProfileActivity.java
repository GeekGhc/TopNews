package com.kaku.colorfulnews.mvp.ui.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kaku.colorfulnews.App;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity {

    App app;
    private DrawerLayout mDrawerLayout;
    private Class mClass;
    private boolean mIsChangeTheme;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
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
