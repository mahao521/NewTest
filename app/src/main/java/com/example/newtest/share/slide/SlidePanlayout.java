package com.mahao.banner;

import android.content.Context;
import android.graphics.Paint;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.UndeclaredThrowableException;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.core.view.DragStartHelper;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class SlidePanlayout extends ViewGroup {


    private static final String TAG = "SlidePanlayout";
    private static final int  DEFAULT_OVERHANGE_SIZE = 32;
    private  int mOverHangSize;
    private static final int MIN_FLING_VELOCITY = 400;

    private ViewDragHelper mDragHelper;
    private View mSlideView;
    private float mSlideOffset;
    private boolean mFirstLayout = true;
    private boolean mCanSlide;
    private boolean mPreservedOpenState;

    public SlidePanlayout(Context context) {
        this(context,null);
    }

    public SlidePanlayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidePanlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float density = context.getResources().getDisplayMetrics().density;
        mOverHangSize = (int) (DEFAULT_OVERHANGE_SIZE * (density + 0.5f));
        setWillNotDraw(false);
        mDragHelper = ViewDragHelper.create(this,0.5f,new ViewDragHelperCallBack());
        mDragHelper.setMinVelocity(MIN_FLING_VELOCITY*density);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        int modeW = MeasureSpec.getSize(widthMeasureSpec);
        int modeH = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();

        Log.d(TAG, "onMeasure: " + sizeW +" h = " + sizeH + " " + modeW + "  " + modeH);
        if(modeW != MeasureSpec.EXACTLY){
            if(isInEditMode()){
                if(modeW == MeasureSpec.AT_MOST){
                    modeW = MeasureSpec.EXACTLY;
                }else if(modeW == MeasureSpec.UNSPECIFIED){
                    modeW = MeasureSpec.EXACTLY;
                    sizeW = 300;
                }
            }else{
                throw new RuntimeException("mode w error");
            }
        }else  if(modeH == MeasureSpec.UNSPECIFIED){
            if(isInEditMode()){
                modeH = MeasureSpec.AT_MOST;
                sizeH = 300;
            }else {
                throw new RuntimeException("mode h error");
            }
        }

        int layoutHeight = 0;
        int maxLayoutHeight = 0;
        if(modeH == MeasureSpec.EXACTLY){
            layoutHeight = maxLayoutHeight = sizeH - getPaddingTop() - getPaddingBottom();
        }else if(modeW == MeasureSpec.AT_MOST){
            maxLayoutHeight = sizeH = - getPaddingBottom()- getPaddingTop();
        }
        int widthSizeAvailable = sizeW - getPaddingLeft() - getPaddingRight();
        int widthRemain = widthSizeAvailable;
        final int count = getChildCount();
        if(count > 2 ){
            Log.d(TAG, "onMeasure: more than two child" );
        }
        boolean canSlide = false;
        mSlideView = null;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            SlideLayoutParams layoutParams = (SlideLayoutParams) child.getLayoutParams();
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            if(modeH == MeasureSpec.AT_MOST && childHeight > layoutHeight){
                layoutHeight = Math.min(childHeight,maxLayoutHeight);
            }
            widthRemain = widthRemain - childWidth;
            canSlide |= layoutParams.slideAble = widthRemain < 0;
            if(layoutParams.slideAble){
                mSlideView = child;
            }
        }
        //non-sliding panels are smaller than the full screen
        if(canSlide){
            final int fixedPanelWidthLimit = widthSizeAvailable - mOverHangSize;
            for (int j = 0; j < getChildCount(); j++) {
                final View child = getChildAt(j);
                if(child.getVisibility() == GONE){
                    continue;
                }
                SlideLayoutParams lp = (SlideLayoutParams) child.getLayoutParams();
                int measuredWidth = child.getMeasuredWidth();
                if(canSlide && child != mSlideView){
                    if(lp.width < 0 && measuredWidth > fixedPanelWidthLimit){
                        child.measure(MeasureSpec.makeMeasureSpec(fixedPanelWidthLimit,MeasureSpec.EXACTLY),
                                getChildMeasureSpec(heightMeasureSpec,getPaddingTop() + getPaddingBottom(),sizeW));
                    }
                }
            }
        }
        mCanSlide = canSlide;
        setMeasuredDimension(sizeW,layoutHeight + getPaddingBottom() + getPaddingTop());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final boolean isLayoutRtl = isLayoutRtlSupport();
        if(isLayoutRtl){
            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
        }else {
            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        }
        final int width = r - l;
        final int paddingStart = isLayoutRtl ? getPaddingRight() : getPaddingLeft();
        final int paddingEnd = isLayoutRtl ? getPaddingLeft() : getPaddingRight();
        final int paddingTop = getPaddingTop();

        final int childCount = getChildCount();
        int xStart = paddingStart;
        int nextStart = xStart;
        if(mFirstLayout){
            mSlideOffset = mCanSlide && mPreservedOpenState ? 1.0f : 0.0f;
        }
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() == View.GONE){
                continue;
            }
            SlideLayoutParams layoutParams = (SlideLayoutParams) child.getLayoutParams();
            final int childWith = child.getMeasuredWidth();
            int offset = 0;
            if(layoutParams.slideAble){


            }else {
                xStart = nextStart;
            }
        }


    }

    class ViewDragHelperCallBack extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return false;
        }

    }

   public  static class  SlideLayoutParams extends MarginLayoutParams{

        boolean slideAble;

        boolean dimWhenOffset;

        Paint dimPaint;

        public SlideLayoutParams(){
            super(MATCH_PARENT,MATCH_PARENT);
        }


        public SlideLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            //todo
        }

        public SlideLayoutParams(int width, int height) {
            super(width, height);
        }

        public SlideLayoutParams(MarginLayoutParams source) {
            super(source);
        }

       public SlideLayoutParams(SlideLayoutParams source) {
           super(source);
           this.slideAble = source.slideAble;
       }
   }

   boolean isLayoutRtlSupport(){
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
   }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFirstLayout = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFirstLayout = true;
    }
}
