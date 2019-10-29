package com.mytest.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mytest.unitytoandroid.R;

public class UnityTest extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
