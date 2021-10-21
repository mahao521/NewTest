package com.example.newtest.share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.newtest.R;
import com.example.newtest.share.slide.SlideSampleActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.view.View.LAYOUT_DIRECTION_LTR;

public class SlidePanelLayoutActivity extends AppCompatActivity {

    private SlidingPaneLayout slideLayout;
    private static final String TAG = "SlidePanelLayoutActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_panel_layout);
        slideLayout = findViewById(R.id.slide_layout);
        View childAt = slideLayout.getChildAt(0);
        int width = childAt.getWidth();
        Log.d(TAG, "onCreate: " + width +" parentSize " + slideLayout.getMeasuredWidth());
        childAt.measure(View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),0);
        Log.d(TAG, "onCreate: " + childAt.getMeasuredWidth());
        final float density = getResources().getDisplayMetrics().density;
        int overhangSize = (int) (32 * density + 0.5f);
        Log.d(TAG, "onCreate: " + overhangSize);
        slideLayout.setParallaxDistance(childAt.getMeasuredWidth() - overhangSize);
  /*      chengeRTL1();
        slideLayout.invalidate();
        slideLayout.setLayoutDirection(LAYOUT_DIRECTION_LTR);
        ViewCompat.setLayoutDirection(slideLayout,ViewCompat.LAYOUT_DIRECTION_LTR);*/
        slideLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(@NonNull View panel, float slideOffset) {
                Log.d(TAG, "onPanelSlide: " + slideOffset);
                View child = slideLayout.getChildAt(0);
                int measuredWidth = child.getWidth();
                Log.d(TAG, "onPanelSlide:  child[0] " + measuredWidth + " child getleft " + child.getLeft() +" right " + child.getRight());
                Log.d(TAG, "onPanelSlide: " + slideLayout.getWidth());
                Log.e(TAG, "onPanelSlide: left " + panel.getLeft()+"  right " + panel.getRight());
            }

            @Override
            public void onPanelOpened(@NonNull View panel) {

            }

            @Override
            public void onPanelClosed(@NonNull View panel) {

            }
        });
        findViewById(R.id.tv_content_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlidePanelLayoutActivity.this, SlideSampleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void chengeRTL1(){
        try {
            Field layout_direction_rtl = ViewCompat.class.getDeclaredField("LAYOUT_DIRECTION_RTL");
            if(!layout_direction_rtl.isAccessible()){
                layout_direction_rtl.setAccessible(true);
            }
            layout_direction_rtl.set(null,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}