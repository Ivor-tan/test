package com.ivor.unity_libs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ivor.unity_libs.R;
import com.ivor.unity_libs.R2;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitySceneActivity extends UnityPlayerActivity {
    @BindView(R2.id.unity_scene)
    LinearLayout mLlUnityContainer;

    @BindView(R2.id.unity_button)
    Button unity_button;

    @BindView(R2.id.unity_start_button)
    Button start_button;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_unity_scene);
        ButterKnife.bind(this);

        //将Unity的View添加到布局里
        View scene = mUnityPlayer.getView();
        mLlUnityContainer.addView(scene);
//        UnityPlayer.LoadLevel
        UnityPlayer.UnitySendMessage("Text_Loading","StartGame","");
        unity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UnitySceneActivity.this, "开始啊", Toast.LENGTH_SHORT).show();
                UnityPlayer.UnitySendMessage("Slider_Loading","Loading","TankGame");
            }
        });

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityPlayer.UnitySendMessage("Slider_Loading","StartGame","");
            }
        });

    }
}
