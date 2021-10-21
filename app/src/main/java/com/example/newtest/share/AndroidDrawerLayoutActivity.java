package com.example.newtest.share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.newtest.R;

public class AndroidDrawerLayoutActivity extends AppCompatActivity {

    private static final String TAG = "DrawerLayoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_drawer_layout);
        findViewById(R.id.ll_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:   ll_left");
            }
        });
    }

    public void onBtnClick(View view) {
        Log.d(TAG, "onBtnClick: ");
    }
}