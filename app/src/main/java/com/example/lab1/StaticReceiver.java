package com.example.lab1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BroadcastType.STATICACTION)) {
            Bundle bundle = intent.getExtras();

            // 添加Notification部分
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("今日推荐")
                    .setTicker("您有一条新消息")
                    .setContentText(bundle.getString("name"))
                    .setSmallIcon(R.mipmap.empty_star)
                    .setPriority(Notification.PRIORITY_DEFAULT);//设置该通知优先级;

            Intent mIntent = new Intent(context, productDetail.class);
            mIntent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setFullScreenIntent(pendingIntent, true);

            NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

            // 绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0, notify);
        }
    }
}
