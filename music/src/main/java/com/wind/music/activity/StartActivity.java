package com.wind.music.activity;

import android.content.Intent;
import android.os.Bundle;

import com.wind.music.R;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
