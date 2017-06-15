package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaku.colorfulnews.R;

import butterknife.BindView;

public class DrawerActivity extends AppCompatActivity {

    @BindView(R.id.login_activity_group)//登录注册板块
    LinearLayout loginGroup;

    @BindView(R.id.login_activity)//登录按钮
    TextView login_activity;

    @BindView(R.id.register_activity)//注册按钮
    TextView register_activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

}
