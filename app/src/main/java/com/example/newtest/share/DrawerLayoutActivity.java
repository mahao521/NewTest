package com.example.newtest.share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.newtest.R;
import com.example.newtest.widget.JumpManager;

import java.lang.reflect.Field;

public class DrawerLayoutActivity extends AppCompatActivity {

    private static final String TAG = "DrawerLayoutLa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drawer_layoutactivity);
        MyDrawerLayout drawerLayout = findViewById(R.id.draw_layout);
        /*setDrawShow(drawerLayout);
        drawerLayout.setDrawerShadow(R.drawable.yuhongm, GravityCompat.START);
        drawerLayout.setDrawerShadow(R.drawable.yuhongm, GravityCompat.END);
        drawerLayout.setScrimColor(Color.TRANSPARENT);*/
        DrawlayoutItem drawlayoutItem = findViewById(R.id.draw_layout_item);
        findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawlayoutItem.removeAllViews();
                Log.d(TAG, "onClick: ");
                JumpManager.jumpActivity(DrawerLayoutActivity.this, AndroidDrawerLayoutActivity.class);
            }
        });
    }

    public void setDrawShow(OriginDrawerLayout drawerLayout) {
        try {
            Field elevation = drawerLayout.getClass().getDeclaredField("SET_DRAWER_SHADOW_FROM_ELEVATION");
            if (!elevation.isAccessible()) {
                elevation.setAccessible(true);
            }
            elevation.set(drawerLayout, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}