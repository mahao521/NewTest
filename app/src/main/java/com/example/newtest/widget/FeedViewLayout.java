package com.example.newtest.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FeedViewLayout extends ViewGroup {

    public FeedViewLayout(Context context) {
        super(context);
    }

    public FeedViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeedViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
                TextView textView = (TextView) childAt;
                float v = new Paint().measureText(textView.getText().toString());
                childAt.layout((int)(getMeasuredWidth() - v)/2,(int)(getMeasuredHeight()-v)/2, (int) v, (int) v);
        }
    }

}




























