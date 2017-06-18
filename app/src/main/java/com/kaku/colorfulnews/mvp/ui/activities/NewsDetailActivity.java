//新闻的详细信息
package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kaku.colorfulnews.App;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.common.Constants;
import com.kaku.colorfulnews.mvp.entity.NewsDetail;
import com.kaku.colorfulnews.mvp.presenter.impl.NewsDetailPresenterImpl;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.mvp.ui.adapter.PersonAdapter;
import com.kaku.colorfulnews.mvp.view.NewsDetailView;
import com.kaku.colorfulnews.utils.AsyncNetUtil;
import com.kaku.colorfulnews.utils.MyUtils;
import com.kaku.colorfulnews.utils.NetUtil;
import com.kaku.colorfulnews.utils.TransformUtils;
import com.kaku.colorfulnews.widget.URLImageGetter;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


public class NewsDetailActivity extends BaseActivity implements NewsDetailView {
    @BindView(R.id.news_detail_photo_iv)
    ImageView mNewsDetailPhotoIv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    /*    @BindView(R.id.news_detail_title_tv)
        TextView mNewsDetailTitleTv;*/
    @BindView(R.id.news_detail_from_tv)
    TextView mNewsDetailFromTv;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.mask_view)
    View mMaskView;

    @Inject
    NewsDetailPresenterImpl mNewsDetailPresenter;

    private URLImageGetter mUrlImageGetter;
    private String mNewsTitle;
    private String mShareLink;
    App app;
    private String postId;
    private String userId;
    private String newsUrl;
    private JSONArray jsonArray = null;
    private JSONObject jsonObject;
    private String jsonString = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        postId = getIntent().getStringExtra(Constants.NEWS_POST_ID);

        mNewsDetailPresenter.setPosId(postId);
        mPresenter = mNewsDetailPresenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        app = (App) getApplication();

    }

    //初始化页面数据
    @SuppressWarnings("deprecation")
    @Override
    public void setNewsDetail(NewsDetail newsDetail) {
        mShareLink = newsDetail.getShareLink();
        mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = MyUtils.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();
        String NewsImgSrc = getImgSrcs(newsDetail);


        setToolBarLayout(mNewsTitle);
//        mNewsDetailTitleTv.setText(newsTitle);
        mNewsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);
        setNewsDetailBodyTv(newsDetail, newsBody);

        userId = app.person.getUserId();
        newsUrl = mShareLink;
        String content = "userId=" + this.userId + "&newsUrl=" + this.newsUrl;
        if (userId.length()>0) {
            Toast.makeText(NewsDetailActivity.this, "userId yes = " + userId, Toast.LENGTH_LONG).show();
            AsyncNetUtil.post("http://10.0.3.2:8000/api/v1/user/news",content, new AsyncNetUtil.Callback() {
                @Override
                public void onResponse(String response) {
                    try {
                        jsonString = "[" + response + "]";
                        jsonArray = new JSONArray(jsonString);
                        jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.getString("status").equals("success")) {
//                            Toast.makeText(NewsDetailActivity.this, "已经收藏了", Toast.LENGTH_SHORT).show();
                            mFab.setImageResource(R.drawable.ic_stared);
                        } else {
//                            Toast.makeText(NewsDetailActivity.this, "你还没有收藏哦", Toast.LENGTH_SHORT).show();
                            mFab.setImageResource(R.drawable.ic_star);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void setToolBarLayout(String newsTitle) {
        mToolbarLayout.setTitle(newsTitle);
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }

    //设置新闻详细的图片
    private void setNewsDetailPhotoIv(String imgSrc) {
        Glide.with(this).load(imgSrc).asBitmap()
                .placeholder(R.drawable.ic_loading)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.drawable.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mNewsDetailPhotoIv)/*(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mNewsDetailPhotoIv.setImageBitmap(resource);
                        mMaskView.setVisibility(View.VISIBLE);
                    }
                })*/;
    }


    //新闻的详细信息
    private void setNewsDetailBodyTv(final NewsDetail newsDetail, final String newsBody) {
        mSubscription = Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(TransformUtils.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                        mFab.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.RollIn).playOn(mFab);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setBody(newsDetail, newsBody);
                    }
                });
    }

    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
//              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, newsBody, imgTotal);
            mNewsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            mNewsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }

    private boolean isShowBody(String newsBody, int imgTotal) {
        return App.isHavePhoto() && imgTotal >= 2 && newsBody != null;
    }

    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(Constants.NEWS_IMG_RES);
        }
        return imgSrc;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showMsg(String message) {
        mProgressBar.setVisibility(View.GONE);
        if (NetUtil.isNetworkAvailable()) {
            Snackbar.make(mAppBar, message, Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_web_view:
                openByWebView();
                break;
            case R.id.action_browser:
                openByBrowser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openByWebView() {
        Intent intent = new Intent(this, NewsBrowserActivity.class);
        intent.putExtra(Constants.NEWS_LINK, mShareLink);
        intent.putExtra(Constants.NEWS_TITLE, mNewsTitle);
        startActivity(intent);
    }

    private void openByBrowser() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        if (canBrowse(intent)) {
            Uri uri = Uri.parse(mShareLink);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private boolean canBrowse(Intent intent) {
        return intent.resolveActivity(getPackageManager()) != null && mShareLink != null;
    }

    @Override
    protected void onDestroy() {
        cancelUrlImageGetterSubscription();
        super.onDestroy();

    }

    private void cancelUrlImageGetterSubscription() {
        try {
            if (mUrlImageGetter != null && mUrlImageGetter.mSubscription != null
                    && !mUrlImageGetter.mSubscription.isUnsubscribed()) {
                mUrlImageGetter.mSubscription.unsubscribe();
                KLog.d("UrlImageGetter unsubscribe");
            }
        } catch (Exception e) {
            KLog.e("取消UrlImageGetter Subscription 出现异常： " + e.toString());
        }
    }

    @OnClick(R.id.fab)
    public void onClick() {
        share();
    }

    private boolean isCollected() {
        return true;
    }

    private void share() {

        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getShareContents());
        startActivity(Intent.createChooser(intent, getTitle()));*/

        if (app.person.getUserId().isEmpty()) {
            Toast.makeText(NewsDetailActivity.this, "你还没有登录哦--" + mShareLink, Toast.LENGTH_LONG).show();
        } else {
            userId = app.person.getUserId();
            newsUrl = mShareLink;
            String content = "userId=" + this.userId + "&newsUrl=" + this.newsUrl;
            AsyncNetUtil.post("http://10.0.3.2:8000/api/v1/news/collect", content, new AsyncNetUtil.Callback() {
                @Override
                public void onResponse(String response) {
                    try {
                        jsonString = "[" + response + "]";
                        jsonArray = new JSONArray(jsonString);
                        jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.getString("status").equals("collect")) {
                            Toast.makeText(NewsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            mFab.setImageResource(R.drawable.ic_stared);
                        } else {
                            Toast.makeText(NewsDetailActivity.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                            mFab.setImageResource(R.drawable.ic_star);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @NonNull
    private String getShareContents() {
        if (mShareLink == null) {
            mShareLink = "";
        }
        return getString(R.string.share_contents, mNewsTitle, mShareLink);
    }
}
