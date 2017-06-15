package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.http.MyHttpClientImpl;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;

public class RegisterActivity extends Activity implements MyHttpClientImpl{

    @BindView(R.id.link_login)
    TextView login;

    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_password)
    EditText input_password;

    private String email;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.link_login, R.id.btn_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link_login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_signup:

        }
    }

    //实现用户注册
    private void sign() {
        this.name = this.input_name.getText().toString();
        this.email = this.input_email.getText().toString();
        this.password = this.input_password.getText().toString();

        post_request();
    }

    public void get_request(){

    }

    public void post_request(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
