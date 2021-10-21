package com.example.newtest;

import android.appwidget.AppWidgetManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.newtest.share.DrawerLayoutActivity;
import com.example.newtest.share.SlidePanelLayoutActivity;
import com.example.newtest.widget.JumpManager;

import static com.example.newtest.NewAppWidget.BTN_ACTION;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_mian).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_drawlayout).setOnClickListener(this);
        findViewById(R.id.btn_slide_layout).setOnClickListener(this);
        //InputMethodService
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: " + ev.getY());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getY());
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mian:
                // ViewCompat.offsetLeftAndRight(v, 100);
                //   JumpManager.jumpActivity(this,SecondActivity.class);
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                Intent intent = new Intent(NewAppWidget.BTN_UPDATE_ACTION);
                intent.setComponent(new ComponentName(this,NewAppWidget.class));
                intent.putExtra(BTN_ACTION,"接受到消息了");
                sendBroadcast(intent);
                break;
            case R.id.btn_three:
                JumpManager.jumpActivity(this, ThreeActivity.class);
                break;
            case R.id.btn_drawlayout:
                JumpManager.jumpActivity(this, DrawerLayoutActivity.class);
                break;
            case R.id.btn_slide_layout:
                JumpManager.jumpActivity(this, SlidePanelLayoutActivity.class);
//                InputMethodService.InputMethodImpl
                break;
        }
    }
}