package com.example.newtest.share;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class DrawlayoutItem extends LinearLayout {

    private static final String TAG = "DrawlayoutItem";
    OnHierarchyChangeListener mOnHierarchyChangeListener;

    public DrawlayoutItem(Context context) {
        this(context, null);
    }

    public DrawlayoutItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawlayoutItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        super.setOnHierarchyChangeListener(new HierarchyChangeListener());
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
        mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.d(TAG, "onMeasure: ");
        View childAt = new View(getContext());
        for (int i = 0; i < getChildCount(); i++) {
            childAt = getChildAt(i);
            Log.d(TAG, "onMeasure: " + childAt.getId() + " " + childAt.getMeasuredWidth() + " 1111111111 " + childAt.getMeasuredHeight());
            measureChildWithMargins(childAt, widthMeasureSpec,0, heightMeasureSpec,0);
            Log.d(TAG, "onMeasure: " + childAt.getId() + " " + childAt.getMeasuredWidth() + " -----" + childAt.getMeasuredHeight());
        }
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout: ");
    }

    private class HierarchyChangeListener implements OnHierarchyChangeListener {
        HierarchyChangeListener() {
        }

        @Override
        public void onChildViewAdded(View parent, View child) {
            Log.d(TAG, "onChildViewAdded: ");
            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        @Override
        public void onChildViewRemoved(View parent, View child) {
         //   onChildViewsChanged(EVENT_VIEW_REMOVED);
            Log.d(TAG, "onChildViewRemoved: ");
            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
