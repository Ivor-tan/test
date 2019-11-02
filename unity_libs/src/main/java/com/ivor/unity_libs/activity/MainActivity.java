package com.ivor.unity_libs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ivor.unity_libs.R;
import com.ivor.unity_libs.R2;
import com.ivor.unity_libs.View.RockerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {
    @BindView(R2.id.one_player)
    Button one_player;

    @BindView(R2.id.two_player)
    TextView two_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
//        Toast.makeText(MainActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();

    }

    private void init() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @OnClick(R2.id.one_player)
    public void OnClickOnePlayer(View view) {
        startActivity(new Intent(MainActivity.this, UnitySceneActivity.class));
    }
    @OnClick(R2.id.two_player)
    public void OnClickTwoPlayer(View view) {
        Toast.makeText(MainActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
    }
}
