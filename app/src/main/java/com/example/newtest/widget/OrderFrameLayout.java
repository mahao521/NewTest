package com.example.newtest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtest.R;

public class OrderFrameLayout extends FrameLayout {

    private static final String TAG = "OrderFrameLayout";
    public OrderFrameLayout(@NonNull Context context) {
        super(context);
        setChildrenDrawingOrderEnabled(true);
    }

    public OrderFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    public OrderFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
    }

    int flPosition = -1;

    @Override
    protected int getChildDrawingOrder(int childCount, int drawingPosition) {
        Log.d(TAG, "getChildDrawingOrder: " + drawingPosition);
        View childAt = getChildAt(drawingPosition);
        if (childAt.getId() == R.id.fl_container) {
            flPosition = drawingPosition;
            return childCount -1;
        }
        if(flPosition != -1 && drawingPosition == childCount -1){
            return flPosition;
        }
        return super.getChildDrawingOrder(childCount, drawingPosition);
    }
}
