package com.newtechieblog.wordpress.views.localnotifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button btnNotifications;
    ConstraintLayout constraintLayout;

    public final static String CHANNEL_ID = "1";
    public final static String CHANNEL_NAME = "Counter Notification";
    private final static int NOTIFICATION_ID = 1;
    int counter = 0;
    NotificationCompat.Builder builder;
    NotificationManagerCompat compat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       btnNotifications = findViewById(R.id.btnNotifications);
       constraintLayout = findViewById(R.id.constraintLayout);

       btnNotifications.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               counter++;
               btnNotifications.setText(String.valueOf(counter));

               if (counter == 5) {
                   sendNotifications();
               }
           }
       });
    }

    public void sendNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Notification Title")
                .setContentText("Notification Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        compat = NotificationManagerCompat.from(MainActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Snackbar.make(constraintLayout, "Please allow the permission to take notification",
                            Snackbar.LENGTH_LONG).setAction("Allow", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                        }
                    }).show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                }
            } else {
                compat.notify(NOTIFICATION_ID, builder.build());
            }
        } else {
            compat.notify(NOTIFICATION_ID, builder.build());
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            compat.notify(NOTIFICATION_ID, builder.build());
        } else {
            Snackbar.make(constraintLayout, "Please allow the permission to take notification",
                    Snackbar.LENGTH_LONG).setAction("Allow", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                }
            }).show();
        }
    }
}