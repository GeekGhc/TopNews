package com.kaku.colorfulnews.mvp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.kaku.colorfulnews.R;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv = (TextView) this.findViewById(R.id.sec_text);
        String text = getIntent().getStringExtra("userinfo");
        tv.setText(text);
    }
}
