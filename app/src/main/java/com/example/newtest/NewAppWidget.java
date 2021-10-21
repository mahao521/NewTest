package com.example.newtest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String TAG = "NewAppWidget";
    public static final String BTN_ACTION = "com.btn.action";
    public static final String BTN_UPDATE_ACTION = "com.btn.update.action";
    public static final String url = "https://c1c2133e2cc13.cdn.sohucs.com/s_v2min/pic/2020/10/21/694588405182388224.jpg";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.d(TAG, "updateAppWidget: ");
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_to_main, pendingIntent);

        ImageView imageView  = new ImageView(context);
        Glide.with(context).asDrawable().load(url)
                .into(imageView);
        Drawable drawable = imageView.getDrawable();
        Log.d(TAG, "updateAppWidget: " + drawable);
        Glide.with(context).asDrawable().load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Log.d(TAG, "onResourceReady: " + resource);
                        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
                        Intent intent = new Intent(BTN_UPDATE_ACTION);
                        intent.setComponent(new ComponentName(context,NewAppWidget.class));
                        intent.putExtra(BTN_ACTION,"接受到消息了");
                        manager.sendBroadcast(intent);
                        views.setImageViewBitmap(R.id.iv_app_widget,((BitmapDrawable)resource).getBitmap());
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                });
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled: ");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        super.onReceive(context, intent);
        if (intent == null) return;
        String action = intent.getAction();
        if (action.equals(BTN_UPDATE_ACTION)){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            String stringExtra = intent.getStringExtra(BTN_ACTION);
            views.setTextViewText(R.id.btn_to_main,stringExtra);

            AppWidgetManager instance = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context,NewAppWidget.class);
            instance.updateAppWidget(instance.getAppWidgetIds(componentName), views);
        }
    }
}