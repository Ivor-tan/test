package com.example.myTest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bravin.btoast.BToast;
import com.example.myTest.R;
import com.example.myTest.Utils.QR_code.ZXingUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;



public class QR_codeActivity extends Activity {

    private String TAG = "QR_codeActivity";

    @ViewInject(R.id.QR_code_image)
    private ImageView QR_code_image;

    @ViewInject(R.id.QR_code_contents)
    private EditText QR_code_contents;

    @ViewInject(R.id.QR_code_make)
    private Button QR_code_make;

    @ViewInject(R.id.QR_code_scan)
    private Button QR_code_scan;

    @ViewInject(R.id.QR_code_result)
    private TextView QR_code_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_qr_code_test);
        x.view().inject(this);
        initView();
    }

    private void initView() {


    }

    private void initData() {


    }
    public void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCaptureActivity(CaptureActivity.class); //设置打开摄像头的Activity
        integrator.setPrompt("请扫描二维码"); //底部的提示文字，设为""可以置空
        integrator.setCameraId(0); //前置或者后置摄像头
        integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            QR_code_result.setText("扫描结果：" + result);
        }

    }

    @Event({R.id.QR_code_make,R.id.QR_code_scan})
    private void OnClick(View view) {

        switch (view.getId()) {
            case R.id.QR_code_scan:
                scan();
                break;
            case R.id.QR_code_make:
                if (QR_code_contents.getText().toString().equals("")) {
                    BToast.success(this)
                            .text("内容为空！！！！！")
                            .show();
                    return;
                }
                Bitmap bitmap = ZXingUtils.createQRImage(QR_code_contents.getText().toString(), 400, 400);
                QR_code_image.setImageBitmap(bitmap);
                break;

        }
    }

}
