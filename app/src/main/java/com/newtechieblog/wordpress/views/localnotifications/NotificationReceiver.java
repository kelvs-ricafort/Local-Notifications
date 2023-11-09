package com.newtechieblog.wordpress.views.localnotifications;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    NotificationManagerCompat compat;
    public static final String CHANNEL_ID = "1";
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context);
    }

    @SuppressLint("MissingPermission")
    public void createNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Title")
                .setContentText("Notification Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        compat = NotificationManagerCompat.from(context);
        compat.notify(1, builder.build());
    }
}
