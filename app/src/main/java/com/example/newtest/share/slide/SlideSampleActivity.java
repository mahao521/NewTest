package com.example.newtest.share.slide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.newtest.R;

import java.util.ArrayList;
import java.util.List;

public class SlideSampleActivity extends AppCompatActivity {

    private static final String TAG = "SlideSampleActivity";
    private int touchSlop;
    private ViewPager2 mViewPager;
    private boolean openState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_sample);
        touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setUserInputEnabled(false);
        FollowFragment followFragment = FollowFragment.newInstance("", "");
        DiscoverFragment discoverFragment = DiscoverFragment.newInstance("", "");
        RecFragment recFragment = RecFragment.newInstance("", "");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(followFragment);
        fragments.add(discoverFragment);
        fragments.add(recFragment);
        FragmentStateAdapter fragmentStateAdapter = new FragementAdapter(this, fragments);
        mViewPager.setAdapter(fragmentStateAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.d(TAG, "onPageScrolled: " + positionOffset + " " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0 || position == 3) {
                    mViewPager.setUserInputEnabled(false);
                } else {
                    mViewPager.setUserInputEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }


    class FragementAdapter extends FragmentStateAdapter {

        private List<Fragment> mFragmentList;

        public FragementAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
            super(fragmentActivity);
            this.mFragmentList = fragmentList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }
    }

    private float downX, downY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: ");
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                //   }else if(openState == true){
                //   mViewPager.setUserInputEnabled(false);
                // }
                break;
            case MotionEvent.ACTION_UP:
                float moveX = ev.getX();
                float moveY = ev.getY();
                float absX = Math.abs(moveX - downX);
                float absY = Math.abs(moveY - downY);
                Log.d(TAG, "dispatchTouchEvent: " + moveX + " " + downX);
                if (mViewPager.getCurrentItem() == 0) {
                    if (moveX - downX > 0) {
                        mViewPager.setUserInputEnabled(false);
                        if (absX - absY > 0 && absX >= touchSlop) {
                            if (moveX - downX > 0 && mViewPager.getCurrentItem() == 0) {
                                mViewPager.setUserInputEnabled(false);
                                openState = true;
                            }
                        } else {/*else if(absX - absY > 0 && absX >= touchSlop && openState == false) {
                            mViewPager.setUserInputEnabled(true);
                        }else if(absX - absY > 0 && absX >= touchSlop && openState == true){
                            mViewPager.setUserInputEnabled(false);
                            openState = false;
                        }else {*/
                            mViewPager.setUserInputEnabled(false);
                        }
                    } else if (moveX - downX < 0) {
                        Log.d(TAG, "dispatchTouchEvent: " + openState);
                        if (absX - absY > 0 && absX >= touchSlop && openState == true) {
                            mViewPager.setUserInputEnabled(false);
                            openState = false;
                        } else if (absX - absY > 0 && absX >= touchSlop && openState == false) {
                            mViewPager.setUserInputEnabled(true);
                        } else {
                            mViewPager.setUserInputEnabled(false);
                        }
                    } else if (mViewPager.getCurrentItem() == 1) {

                    } else if (mViewPager.getCurrentItem() == 2) {


                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}