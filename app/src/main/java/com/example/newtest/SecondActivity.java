package com.example.newtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        View view = findViewById(R.id.fl_container);
        Button btnMid = findViewById(R.id.btn_sec_mid);
        RelativeLayout relativeLayout = findViewById(R.id.rl_sec);
        view.bringToFront();

        findViewById(R.id.btn_sec_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height += 100;
                view.requestLayout();
                view.getParent().bringChildToFront(view);
                ((FrameLayout)view.getParent()).updateViewLayout(view, view.getLayoutParams());

            }
        });

        Rect rect = new Rect(0,0,100,100);
        View ll = findViewById(R.id.ll_bottom);
        findViewById(R.id.btn_sec_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  /*              ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height -= 100;
                view.requestLayout();*/

                rect.right += 10;
                rect.bottom += 10;
                ll.setClipBounds(rect);
                ll.requestLayout();
                Log.d(TAG, "onClick: " + btnMid.getMeasuredWidth()+" " + btnMid.getMeasuredHeight());
                btnMid.measure(431,0);
                Log.d(TAG, "onClick: " + btnMid.getMeasuredWidth()+" " + btnMid.getMeasuredHeight());
            }
        });
    }
}