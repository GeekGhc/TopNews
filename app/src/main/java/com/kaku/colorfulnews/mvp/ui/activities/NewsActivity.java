//新闻列表页
package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaku.colorfulnews.App;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.annotation.BindValues;
import com.kaku.colorfulnews.common.Constants;
import com.kaku.colorfulnews.event.ChannelChangeEvent;
import com.kaku.colorfulnews.event.ScrollToTopEvent;
import com.kaku.colorfulnews.greendao.NewsChannelTable;
import com.kaku.colorfulnews.mvp.presenter.impl.NewPresenterImpl;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.mvp.ui.adapter.PagerAdapter.NewsFragmentPagerAdapter;
import com.kaku.colorfulnews.mvp.ui.adapter.PersonAdapter;
import com.kaku.colorfulnews.mvp.ui.fragment.NewsListFragment;
import com.kaku.colorfulnews.mvp.view.NewsView;
import com.kaku.colorfulnews.utils.MyUtils;
import com.kaku.colorfulnews.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

@BindValues(mIsHasNavigationView = true)
public class NewsActivity extends BaseActivity
        implements NewsView {
    private String mCurrentViewPagerName;
    private List<String> mChannelNames;
    App app;

    @BindView(R.id.toolbar)//头部板块
            Toolbar mToolbar;
    @BindView(R.id.tabs)//选项卡(新闻类型)
            TabLayout mTabs;
    @BindView(R.id.view_pager)//新闻展示内容页
            ViewPager mViewPager;
    @BindView(R.id.nav_view)//侧边栏
            NavigationView mNavView;
    @BindView(R.id.fab)//新闻详情页收藏
            FloatingActionButton mFab;
    @BindView(R.id.drawer_layout)//替换页面
            DrawerLayout mDrawerLayout;

    @Inject
    NewPresenterImpl mNewsPresenter;

    private List<Fragment> mNewsFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = RxBus.getInstance().toObservable(ChannelChangeEvent.class)
                .subscribe(new Action1<ChannelChangeEvent>() {
                    @Override
                    public void call(ChannelChangeEvent channelChangeEvent) {
                        mNewsPresenter.onChannelDbChanged();
                    }
                });

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        this.drawHeader = navView.getHeaderView(0);
        Intent intent = getIntent();
        app = (App) getApplication();
        app.person = (PersonAdapter) intent.getSerializableExtra("user");
        if (app.person != null) {
            LinearLayout login_activity_group = (LinearLayout) drawHeader.findViewById(R.id.login_activity_group);
            login_activity_group.setVisibility(View.GONE);
            LinearLayout user_activity_info = (LinearLayout) drawHeader.findViewById(R.id.user_info);
            user_activity_info.setVisibility(View.VISIBLE);
            TextView user_name = (TextView) drawHeader.findViewById(R.id.hd_name);
            user_name.setText(app.person.getUserName());
            TextView user_desc = (TextView) drawHeader.findViewById(R.id.hd_desc);
            if (app.person.getDesc().equals("null")) {
                user_desc.setText("");
            }else{
                user_desc.setText(app.person.getDesc());
            }
        } else {
            app.person = new PersonAdapter();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
//        mIsHasNavigationView = true;
        mBaseNavView = mNavView;
        mPresenter = mNewsPresenter;
        mPresenter.attachView(this);
    }

    //打开新闻频道  移动头部
    @OnClick({R.id.fab, R.id.add_channel_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                RxBus.getInstance().post(new ScrollToTopEvent());
                break;
            case R.id.add_channel_iv:
                Intent intent = new Intent(this, NewsChannelActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initViewPager(List<NewsChannelTable> newsChannels) {
        final List<String> channelNames = new ArrayList<>();
        if (newsChannels != null) {
            setNewsList(newsChannels, channelNames);
            setViewPager(channelNames);
        }
    }

    //设置新闻列表
    private void setNewsList(List<NewsChannelTable> newsChannels, List<String> channelNames) {
        mNewsFragmentList.clear();
        for (NewsChannelTable newsChannel : newsChannels) {
            NewsListFragment newsListFragment = createListFragments(newsChannel);
            mNewsFragmentList.add(newsListFragment);
            channelNames.add(newsChannel.getNewsChannelName());
        }
    }

    private NewsListFragment createListFragments(NewsChannelTable newsChannel) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_ID, newsChannel.getNewsChannelId());
        bundle.putString(Constants.NEWS_TYPE, newsChannel.getNewsChannelType());
        bundle.putInt(Constants.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
        fragment.setArguments(bundle);
        return fragment;
    }

    //设置显示叶爱民
    private void setViewPager(List<String> channelNames) {
        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(
                getSupportFragmentManager(), channelNames, mNewsFragmentList);
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);
        MyUtils.dynamicSetTabLayoutMode(mTabs);
//        mTabs.setTabsFromPagerAdapter(adapter);
        setPageChangeListener();

        mChannelNames = channelNames;
        int currentViewPagerPosition = getCurrentViewPagerPosition();
        mViewPager.setCurrentItem(currentViewPagerPosition, false);
    }

    //获取当前显示页面位置
    private int getCurrentViewPagerPosition() {
        int position = 0;
        if (mCurrentViewPagerName != null) {
            for (int i = 0; i < mChannelNames.size(); i++) {
                if (mCurrentViewPagerName.equals(mChannelNames.get(i))) {
                    position = i;
                }
            }
        }
        return position;
    }

    //监听页面变化的事件
    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentViewPagerName = mChannelNames.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {
        Snackbar.make(mFab, message, Snackbar.LENGTH_SHORT).show();
    }
}
