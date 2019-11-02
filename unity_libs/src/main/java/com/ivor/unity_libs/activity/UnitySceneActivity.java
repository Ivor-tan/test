package com.ivor.unity_libs.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ivor.unity_libs.R;
import com.ivor.unity_libs.R2;
import com.ivor.unity_libs.View.RockerView;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitySceneActivity extends UnityPlayerActivity {
    @BindView(R2.id.unity_scene)
    LinearLayout mLlUnityContainer;

    @BindView(R2.id.rockerXY_View)
    RockerView rockerXY_View;

    //    由于摇杆一直监听方向，添加标记避免重复调用C#方法
    private int PlayerDirection = 5;                //上次方向
    private int PlayerCurrentDirection = 2;         //当前方向

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_unity_scene);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        UnityPlayer.UnitySendMessage("Slider_Loading", "Loading", "TankGame");
    }

    private void initView() {
        //将Unity的View添加到布局里
        View scene = mUnityPlayer.getView();
        mLlUnityContainer.addView(scene);
    }

    private void initListener() {
        rockerXY_View.setOnShakeListener(RockerView.DirectionMode.DIRECTION_8, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(RockerView.Direction direction) {

                if (direction == RockerView.Direction.DIRECTION_CENTER) {
                    PlayerCurrentDirection = 0;
                    if (PlayerDirection == PlayerCurrentDirection) {
                        return;
                    }
                    UnityPlayer.UnitySendMessage("PlayerControl", "PlayerIsMove", "0");
                    PlayerDirection = PlayerCurrentDirection;
                } else if (direction == RockerView.Direction.DIRECTION_DOWN) {
                    PlayerCurrentDirection = 2;
                    if (PlayerDirection == PlayerCurrentDirection) {
                        return;
                    }
                    UnityPlayer.UnitySendMessage("PlayerControl", "PlayerIsMove", "2");
                    PlayerDirection = PlayerCurrentDirection;
                } else if (direction == RockerView.Direction.DIRECTION_LEFT) {
                    PlayerCurrentDirection = 3;
                    if (PlayerDirection == PlayerCurrentDirection) {
                        return;
                    }
                    UnityPlayer.UnitySendMessage("PlayerControl", "PlayerIsMove", "3");
                    PlayerDirection = PlayerCurrentDirection;
                } else if (direction == RockerView.Direction.DIRECTION_UP) {
                    PlayerCurrentDirection = 1;
                    if (PlayerDirection == PlayerCurrentDirection) {
                        return;
                    }
                    UnityPlayer.UnitySendMessage("PlayerControl", "PlayerIsMove", "1");
                    PlayerDirection = PlayerCurrentDirection;
                } else if (direction == RockerView.Direction.DIRECTION_RIGHT) {
                    PlayerCurrentDirection = 4;
                    if (PlayerDirection == PlayerCurrentDirection) {
                        return;
                    }
                    UnityPlayer.UnitySendMessage("PlayerControl", "PlayerIsMove", "4");
                    PlayerDirection = PlayerCurrentDirection;
                } else if (direction == RockerView.Direction.DIRECTION_DOWN_LEFT) {
//                    directionXY = ("当前方向：左下");
//                    Text_direction.setText("当前方向：左下");
                } else if (direction == RockerView.Direction.DIRECTION_DOWN_RIGHT) {
//                    directionXY = ("当前方向：右下");
//                    Text_direction.setText("当前方向：右下");
                } else if (direction == RockerView.Direction.DIRECTION_UP_LEFT) {
//                    directionXY = ("当前方向：左上");
//                    Text_direction.setText("当前方向：左上");
                } else if (direction == RockerView.Direction.DIRECTION_UP_RIGHT) {
//                    directionXY = ("当前方向：右上");
//                    Text_direction.setText("当前方向：右上");
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void GameOver(){
        Toast.makeText(UnitySceneActivity.this, "GGGGGGGG", Toast.LENGTH_SHORT).show();
//        finish();
    }
}
