package com.kaku.colorfulnews.mvp.ui.activities;

import android.content.Intent;
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

public class PasswordActivity extends BaseActivity {

    App app;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.input_password)
    EditText uPwd;



    private JSONArray jsonArray = null;
    private JSONObject jsonObject;
    private String jsonString = "";
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        app = (App) getApplication();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_password;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {

//        mMsgTv.setAutoLinkMask(Linkify.ALL);
//        mMsgTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.btn_updatePwd)
    public void onClick(View view) {
        this.password = uPwd.getText().toString();
        String content = "userId="+app.person.getUserId()+"&password=" + this.password;
        AsyncNetUtil.post("http://10.0.3.2:8000/api/v1/user/password", content, new AsyncNetUtil.Callback() {
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
                        Toast.makeText(PasswordActivity.this, "修改成功"+jsonObject.getString("name"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PasswordActivity.this, NewsActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PasswordActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
