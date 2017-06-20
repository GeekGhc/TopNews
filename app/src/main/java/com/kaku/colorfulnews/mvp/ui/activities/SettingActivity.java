package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaku.colorfulnews.App;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.event.ChannelItemMoveEvent;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.mvp.ui.adapter.PersonAdapter;
import com.kaku.colorfulnews.utils.RxBus;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class SettingActivity extends BaseActivity {

    App app;
    private DrawerLayout mDrawerLayout;
    private Class mClass;
    private boolean mIsChangeTheme;

    private JSONArray jsonArray = null;
    private JSONObject jsonObject;
    private String jsonString = "";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (App) getApplication();
        initDrawerLayout();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {

//        mMsgTv.setAutoLinkMask(Linkify.ALL);
//        mMsgTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initDrawerLayout() {
        TextView btn_logout = (TextView) findViewById(R.id.btn_logout);

    }

    @OnClick(R.id.btn_logout)
    public void onClick(View view) {
        Intent intent = new Intent(SettingActivity.this, NewsActivity.class);
        startActivity(intent);
    }

}
