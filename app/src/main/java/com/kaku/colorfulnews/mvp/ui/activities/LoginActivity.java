package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.mvp.ui.adapter.PersonAdapter;
import com.kaku.colorfulnews.utils.AsyncNetUtil;
import com.kaku.colorfulnews.utils.RxBus;
import com.mob.tools.utils.UIHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends AppCompatActivity implements PlatformActionListener, Handler.Callback {

    @BindView(R.id.link_signup)
    TextView signUp;
    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_password)
    EditText input_password;

    private static final int MSG_USERID_FOUND = 1;//用户已经存在
    private static final int MSG_LOGIN = 2;//登录中
    private static final int MSG_AUTH_CANCEL = 3;//取消授权
    private static final int MSG_AUTH_ERROR = 4;//授权出错
    private static final int MSG_AUTH_COMPLETE = 5;//授权成功

    private String email;
    private String password;
    private JSONArray jsonArray = null;
    private JSONObject jsonObject;
    private String jsonString = "";
    private HashMap<String, Object> mapQQ = new HashMap<String, Object>();//授权信息
    private HashMap<String, Object> mapSina = new HashMap<String, Object>();//授权信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
    }

    @OnClick({R.id.link_signup, R.id.btn_login,R.id.btn_login_qq,R.id.btn_login_weibo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link_signup:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_login_qq:
                Toast.makeText(LoginActivity.this, "QQ登录", Toast.LENGTH_SHORT).show();
                authorize(new QQ(this));
                break;
            case R.id.btn_login_weibo:
                Toast.makeText(LoginActivity.this, "微博登录", Toast.LENGTH_SHORT).show();
                authorize(new SinaWeibo(this));
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
                try {
                    jsonString = "[" + response + "]";
                    jsonArray = new JSONArray(jsonString);
                    jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("status").equals("success")) {
                        jsonString = "["+jsonObject.getString("user")+"]";
                        jsonArray = new JSONArray(jsonString);
                        jsonObject = jsonArray.getJSONObject(0);
                        PersonAdapter personAdapter = new PersonAdapter(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("avatar"),jsonObject.getString("desc"));
                        Bundle data = new Bundle();
                        data.putSerializable("user",personAdapter);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                        intent.putExtras(data);
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


    private void authorize(Platform plat) {
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                if (plat.getName().equals("QQ")) {
                    Toast.makeText(LoginActivity.this, "这是QQ", Toast.LENGTH_SHORT).show();
//                    socialLogin(plat.getName(), userId, mapQQ);
                }
                else if (plat.getName().equals("TencentWeibo")) {
                    Toast.makeText(LoginActivity.this, "这是微博", Toast.LENGTH_SHORT).show();
//                    socialLogin(plat.getName(), userId, mapSina);
                }
                return;
            }
        }
        //若本地没有授权过就请求用户数据
        plat.setPlatformActionListener(this);//
        plat.SSOSetting(false);//此处设置为false，则在优先采用客户端授权的方法，设置true会采用网页方式
        plat.showUser(null);//获得用户数据
    }

    private void socialLogin(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
        //跳转到第二个页面，获取到的数据就在这里
        Toast.makeText(LoginActivity.this, "data = "+userInfo, Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
        intent.putExtra("userinfo", "userinfo:" + userInfo.toString());
        startActivity(intent);*/
    }

    //一定要停止
    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    @Override
    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Log.e("ShareActivity", platform.getName());
            if (platform.getName().equals("QQ")) {
                mapQQ.clear();
                mapQQ.putAll(res);
            }
            else if (platform.getName().equals("TencentWeibo")) {
                mapSina.clear();
                mapSina.putAll(res);
            }
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            socialLogin(platform.getName(), platform.getDb().getUserId(), res);
        }
        System.out.println(res);
    }


    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {
                String text = getString(R.string.logining, message.obj);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }
}
