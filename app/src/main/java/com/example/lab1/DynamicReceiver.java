package com.example.lab1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BroadcastType.DYNAMICACTION)) {
            System.out.println("Dynamic Received!");
            Bundle bundle = intent.getExtras();
            int index = intent.getIntExtra("i", 0);
            // 添加Notification部分
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("已收藏")
                    .setTicker("您有一条新消息")
                    .setContentText(bundle.getString("name"))
                    .setSmallIcon(R.mipmap.empty_star);

            Intent mIntent = new Intent(context, ShopList.class);
            Bundle data = new Bundle();

            data.putBoolean("showCart", true);
            mIntent.putExtras(data);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

            // 绑定Notification，发送通知请求
            Notification notify = builder.build();
            System.out.println(index);
            manager.notify(index, notify);
        }
    }
}
