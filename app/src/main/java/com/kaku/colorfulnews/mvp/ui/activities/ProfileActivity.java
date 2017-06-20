package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kaku.colorfulnews.App;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.mvp.ui.adapter.PersonAdapter;
import com.kaku.colorfulnews.utils.AsyncNetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {

    App app;
    private DrawerLayout mDrawerLayout;
    private Class mClass;
    private boolean mIsChangeTheme;
    private String name;
    private String phone;
    private String desc;

    private JSONArray jsonArray = null;
    private JSONObject jsonObject;
    private String jsonString = "";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_name)
    EditText uName;
    @BindView(R.id.user_phone)
    EditText uPhone;
    @BindView(R.id.user_desc)
    EditText uDesc;
    @BindView(R.id.user_email)
    EditText uEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (App) getApplication();

        initData();
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


    private void initData() {
        AsyncNetUtil.get("http://10.0.3.2:8000/api/v1/user/"+app.person.getUserId(), new AsyncNetUtil.Callback() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(LoginActivity.this, "返回 = " + response, Toast.LENGTH_SHORT).show();
                try {
                    jsonString = "[" + response + "]";
                    jsonArray = new JSONArray(jsonString);
                    jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("status").equals("success")) {
                        jsonString = "[" + jsonObject.getString("user") + "]";
                        jsonArray = new JSONArray(jsonString);
                        jsonObject = jsonArray.getJSONObject(0);

                        uName.setText(jsonObject.getString("name"));
                        uEmail.setText(jsonObject.getString("email"));

                        if (jsonObject.getString("phone").equals("null")) {
                            uPhone.setText("");
                        } else {
                            uPhone.setText(jsonObject.getString("phone"));
                        }
                        if (jsonObject.getString("desc").equals("null")) {
                            uDesc.setText("");
                        } else {
                            uDesc.setText(jsonObject.getString("desc"));
                        }

                        Toast.makeText(ProfileActivity.this, "初始化成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.btn_saveUpdate)
    public void onClick(View view) {
        this.name = uName.getText().toString();
        this.phone = uPhone.getText().toString();
        this.desc = uDesc.getText().toString();
        String content = "userId="+app.person.getUserId()+"&name=" + this.name + "&phone=" + this.phone + "&desc=" + this.desc;
//        Toast.makeText(ProfileActivity.this, "data = "+content, Toast.LENGTH_SHORT).show();
        AsyncNetUtil.post("http://10.0.3.2:8000/api/v1/user/info", content, new AsyncNetUtil.Callback() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonString = "[" + response + "]";
                    jsonArray = new JSONArray(jsonString);
                    jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("status").equals("success")) {
                        jsonString = "[" + jsonObject.getString("user") + "]";
                        jsonArray = new JSONArray(jsonString);
                        jsonObject = jsonArray.getJSONObject(0);
                        PersonAdapter personAdapter = new PersonAdapter(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("avatar"),jsonObject.getString("desc"));
                        Bundle data = new Bundle();
                        data.putSerializable("user",personAdapter);
                        Toast.makeText(ProfileActivity.this, "登录成功"+jsonObject.getString("name"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProfileActivity.this, NewsActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                        Toast.makeText(ProfileActivity.this, "更新用户资料成功" + app.person.getUserName(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "更新用户资料失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateProfile() {

    }
}
