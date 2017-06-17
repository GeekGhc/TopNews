package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.event.ScrollToTopEvent;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.utils.AsyncNetUtil;
import com.kaku.colorfulnews.utils.RxBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.link_signup)
    TextView signUp;

    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_password)
    EditText input_password;

    private String email;
    private String password;
    private JSONArray jsonArray = null;
    private String jsonString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.link_signup, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link_signup:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        this.email = this.input_email.getText().toString();
        this.password = this.input_password.getText().toString();

        String content = "email=" + this.email + "&password=" + this.password;

        AsyncNetUtil.post("http://10.0.3.2:8000/api/v1/user/login", content, new AsyncNetUtil.Callback() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(LoginActivity.this, "返回 = " + response, Toast.LENGTH_SHORT).show();
                try {
                    jsonString = "[" + response + "]";
                    jsonArray = new JSONArray(jsonString);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("status").equals("success")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "密码或者邮箱错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
