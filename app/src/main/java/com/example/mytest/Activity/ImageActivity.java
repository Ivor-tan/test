package com.example.mytest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mytest.bean.TokenBean;
import com.test.my.R;

import java.io.Serializable;

public class ImageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

        TokenBean bean = new TokenBean();
        bean.setCode("asdad");
        Intent intent = new Intent();
        intent.putExtra("name", "22222222222222");
        intent.putExtra("bean", bean);
        setResult(RESULT_OK, intent);
//        this.finish();
    }
}
