package com.example.newtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.newtest.widget.round.RoundImageView;
import com.example.newtest.widget.round.XfModeRoundImage;

public class ThreeActivity extends AppCompatActivity {

    private static final String TAG = "ThreeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        RoundImageView imageView = findViewById(R.id.iv_three_photo);
        //imageView.setImageResource(R.drawable.yuhongm);
        Log.d(TAG, "onCreate: " + imageView.getWidth()+"..." + imageView.getHeight());
        XfModeRoundImage xfModeRoundImage = findViewById(R.id.iv_three_photo2);
        xfModeRoundImage.setImageResource(R.drawable.yuhongm);
    }
}