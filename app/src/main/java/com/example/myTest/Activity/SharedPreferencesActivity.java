package com.example.myTest.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.myTest.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SharedPreferencesActivity extends Activity {

    private String TAG = "test";

    @ViewInject(R.id.sharedperferences_getData)
    private Button sharedperferences_getData;

    private SharedPreferences getPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sharedperferences);
        x.view().inject(this);
    }

    @Event({R.id.sharedperferences_getData})
    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.sharedperferences_getData:
                try {
                    Context context = createPackageContext("com.rht.sos", Context.CONTEXT_IGNORE_SECURITY);
                    getPreferences = context.getSharedPreferences("share_userinfo", Context.MODE_PRIVATE);
                    String isFirstRun = getPreferences.getString("isFirstRun", "");
                    Log.d(TAG, "OnClick: " + isFirstRun);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
