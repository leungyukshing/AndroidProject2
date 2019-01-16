package com.example.lab1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static int status = 0;
    private  static int productId = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        switch (NewAppWidget.status) {
            case 0:
                views.setTextViewText(R.id.appwidget_text, widgetText);
                views.setImageViewResource(R.id.preview, R.mipmap.full_star);
                Intent intent = new Intent(context, ShopList.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget, pendingIntent);
        }
        // views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Widget Broadcast Received!");
        super.onReceive(context, intent);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = intent.getExtras();
        // 启动应用推荐商品（静态广播）
        if (intent.getAction().equals(BroadcastType.STATICACTION)) {
            System.out.println("Receive Static Broadcast!");
            remoteViews.setTextViewText(R.id.appwidget_text, "今日推荐 " + (String)bundle.get("name"));
            remoteViews.setImageViewResource(R.id.preview, R.mipmap.full_star);
            Intent i = new Intent(context, productDetail.class);
            i.putExtras(bundle);
            i.putExtra("showCart", false);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class), remoteViews);
        }
        // 收藏商品（动态广播）
        else if (intent.getAction().equals(BroadcastType.DYNAMICACTION)) {
            System.out.println("Receive Dynamic Broadcast!");
            remoteViews.setTextViewText(R.id.appwidget_text, "已收藏 " + (String)bundle.get("name"));
            remoteViews.setImageViewResource(R.id.preview, R.mipmap.full_star);
            Intent i = new Intent(context, ShopList.class);
            //i.putExtras(bundle);
            i.putExtra("showCart", true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class), remoteViews);
        }
    }
}

