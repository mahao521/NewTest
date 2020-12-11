package com.example.newtest.widget.round;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;


/**
 *
 */
public class RoundImageView extends AppCompatImageView {

    private static final String TAG = "RoundImageView";
    private int ratio = 19;
    private int width,height;
    private Path mPath;

    public RoundImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + MeasureSpec.getSize(widthMeasureSpec) +" " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: " + getWidth() + " " + getHeight());
        width = getWidth();
        height = getHeight();
    }

    public void init(){
        if(Build.VERSION.SDK_INT < 18){
            setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: " + getWidth() + " " + getHeight());
        if(width > ratio && height > ratio){
            mPath.reset();
            mPath.moveTo(ratio, 0);

            mPath.lineTo(width - ratio, 0);
            mPath.quadTo(width, 0, width, ratio);

            mPath.lineTo(width, height - ratio);
            mPath.quadTo(width, height, width - ratio, height);

            mPath.lineTo(ratio, height);
            mPath.quadTo(0, height, 0, height - ratio);

            mPath.lineTo(0, ratio);
            mPath.quadTo(0, 0, ratio, 0);

            canvas.clipPath(mPath);
        }
        super.onDraw(canvas);
    }
}
