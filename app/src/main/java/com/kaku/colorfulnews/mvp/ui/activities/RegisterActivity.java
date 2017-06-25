package com.kaku.colorfulnews.mvp.ui.activities;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.http.MyHttpClientImpl;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.mvp.ui.adapter.PersonAdapter;
import com.kaku.colorfulnews.utils.AsyncNetUtil;
import com.kaku.colorfulnews.utils.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;

public class RegisterActivity extends Activity{

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
    private String url;
    private Handler handler = new Handler();

    private JSONArray jsonArray = null;
    private JSONObject jsonObject;
    private String jsonString = "";

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
                sign();
                break;
        }
    }

    //实现用户注册
    private void sign() {
        this.name = this.input_name.getText().toString();
        this.email = this.input_email.getText().toString();
        this.password = this.input_password.getText().toString();

        String content = "name="+this.name+"&email="+this.email+"&password="+this.password;

        AsyncNetUtil.post("http://10.0.3.2:8000/api/v1/user/register", content,new AsyncNetUtil.Callback(){
            @Override
            public void onResponse(String response) {
                try {
                    jsonString = "[" + response + "]";
                    jsonArray = new JSONArray(jsonString);
                    jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("status").equals("success")) {
                        jsonString = "["+jsonObject.getString("user")+"]";
                        jsonArray = new JSONArray(jsonString);
                        jsonObject = jsonArray.getJSONObject(0);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
