package com.ivor.unity_libs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ivor.unity_libs.R;
import com.ivor.unity_libs.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {
    @BindView(R2.id.go_scene)
    Button go_scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.go_scene})
    void OnClick(View view) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
    }
}
