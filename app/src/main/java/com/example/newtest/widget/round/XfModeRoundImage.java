package com.example.newtest.widget.round;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.newtest.util.DisplayUtil;


public class XfModeRoundImage extends AppCompatImageView {

    private static final String TAG = "XfModeRoundImage";
    Paint mPaint = new Paint();
    Paint imagePaint = new Paint();
    Path mPath = new Path();
    public static int ratio;
    public int width, height;
    public int minSizeW, minSizeH;
    private boolean isUsePath = true;

    public XfModeRoundImage(@NonNull Context context) {
        super(context);
        init(context);
    }

    public XfModeRoundImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XfModeRoundImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        initPaint();
        //    initPath();
        initSize();
    }

    public void initSize() {
        minSizeH = DisplayUtil.dp2Px(getContext(), 200);
        minSizeW = minSizeH;
        ratio = DisplayUtil.dp2Px(getContext(), 15);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);  //父view和子view测量之后的大小
        int modeW = MeasureSpec.getMode(widthMeasureSpec);  //父view和子view测量之后的mode
        int sizeH = MeasureSpec.getSize(heightMeasureSpec); //父view和子view测量之后的大小
        int modeH = MeasureSpec.getMode(heightMeasureSpec); //父view和子view测量之后的mode
        if (modeW == MeasureSpec.EXACTLY) {
            width = sizeW; //子view的大小 或者父view传递给他的父view的大小
        } else if (modeW == MeasureSpec.AT_MOST) {
            width = getDrawable().getIntrinsicWidth();
        } else if (modeW == MeasureSpec.UNSPECIFIED) { //父亲的mode是unSpeified
            //比如 child.measur(0,0); 强制child使用Measurespec.Unspecified方式测量。 就是子view大小，由子view来确定。
            width = minSizeH; // 这里应该设置成子view测量完成之后的大小。
            //此时sizeW的大小为0
        }
        if (modeH == MeasureSpec.EXACTLY) {
            height = sizeH;
        } else if (modeH == MeasureSpec.AT_MOST) {
            height = getDrawable().getIntrinsicHeight();
        } else if (modeH == MeasureSpec.UNSPECIFIED) {
            width = minSizeH;
        }
        Log.d(TAG, "onMeasure: " + getMeasuredWidth() + " " + getMeasuredHeight() + " " + getMeasuredState());
    }

    private Path initPath() {
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

        mPath.close();
        return mPath;
    }

    public void drawToRight(Canvas canvas) {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        int width = getMeasuredWidth();
        Path path = new Path();
        //  path.moveTo(ratio,ratio);
        path.moveTo(0, ratio + 10);
        path.lineTo(0, 0);
        path.lineTo(ratio + 10, 0);
        path.arcTo(new RectF(0, 0, ratio + 10, (ratio + 10) * 2), -90, -90);
        path.close();
        canvas.drawPath(path, mPaint);
    }


    private void drawBottomLeft(Canvas canvas) {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        int height = getHeight();
        Path path = new Path();
        path.moveTo(0, height - ratio);
        path.lineTo(0, height);
        path.lineTo(ratio, height);
        path.arcTo(new RectF(0, height - 2 * ratio,
                ratio * 2, height), 90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    public Paint initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        imagePaint = new Paint();
        imagePaint.setXfermode(null);
        return mPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint, Canvas.ALL_SAVE_FLAG);
        initPath();
        super.onDraw(canvas);
        if(isUsePath){
            canvas.drawPath(mPath, mPaint);
        }else {
            drawToRight(canvas);
            drawBottomLeft(canvas);
        }
        //canvas.drawCircle(getDrawable().getIntrinsicWidth()/2,getDrawable().getIntrinsicHeight()/2,getDrawable().getIntrinsicWidth()/2,mPaint);
        canvas.restore();
    }
}
