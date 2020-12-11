package com.example.newtest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyRelativeLayout  extends LinearLayout {

    private static final String TAG = "MyRelativeLayout";

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Log.d(TAG, "onMeasure1: " + childAt.getMeasuredWidth() +" " + childAt.getMeasuredHeight());
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec);
            Log.d(TAG, "onMeasure2: " + childAt.getMeasuredWidth() +" " + childAt.getMeasuredHeight());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
