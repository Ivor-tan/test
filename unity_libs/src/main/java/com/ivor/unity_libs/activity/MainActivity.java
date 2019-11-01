package com.ivor.unity_libs.activity;

import android.app.Activity;
import android.os.Bundle;
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
    @BindView(R2.id.go_scene)
    Button go_scene;

    @BindView(R2.id.rockerXY_View)
    RockerView rockerXY_View;

    @BindView(R2.id.direction)
    TextView Text_direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rockerXY_View.setOnShakeListener(RockerView.DirectionMode.DIRECTION_8, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(RockerView.Direction direction) {
                if (direction == RockerView.Direction.DIRECTION_CENTER){
//                    directionXY = ("当前方向：中心");
                    Text_direction.setText("当前方向：中心");
                }else if (direction == RockerView.Direction.DIRECTION_DOWN){
//                    directionXY = ("当前方向：下");
                    Text_direction.setText("当前方向：下");
                }else if (direction == RockerView.Direction.DIRECTION_LEFT){
//                    directionXY = ("当前方向：左");
                    Text_direction.setText("当前方向：左");
                }else if (direction == RockerView.Direction.DIRECTION_UP){
//                    directionXY = ("当前方向：上");
                    Text_direction.setText("当前方向：上");
                }else if (direction == RockerView.Direction.DIRECTION_RIGHT){
//                    directionXY = ("当前方向：右");
                    Text_direction.setText("当前方向：右");
                }else if (direction == RockerView.Direction.DIRECTION_DOWN_LEFT){
//                    directionXY = ("当前方向：左下");
                    Text_direction.setText("当前方向：左下");
                }else if (direction == RockerView.Direction.DIRECTION_DOWN_RIGHT){
//                    directionXY = ("当前方向：右下");
                    Text_direction.setText("当前方向：右下");
                }else if (direction == RockerView.Direction.DIRECTION_UP_LEFT){
//                    directionXY = ("当前方向：左上");
                    Text_direction.setText("当前方向：左上");
                }else if (direction == RockerView.Direction.DIRECTION_UP_RIGHT){
//                    directionXY = ("当前方向：右上");
                    Text_direction.setText("当前方向：右上");
                }
            }

            @Override
            public void onFinish() {

            }
        });

    }

    @OnClick({R2.id.go_scene})
    void OnClick(View view) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(MainActivity.this, UnitySceneActivity.class));
    }
}
