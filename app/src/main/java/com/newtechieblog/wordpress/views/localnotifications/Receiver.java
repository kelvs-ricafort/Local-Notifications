package com.newtechieblog.wordpress.views.localnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String messageText = intent.getStringExtra("toast");
        Toast.makeText(context, messageText, Toast.LENGTH_LONG).show();

    }
}
