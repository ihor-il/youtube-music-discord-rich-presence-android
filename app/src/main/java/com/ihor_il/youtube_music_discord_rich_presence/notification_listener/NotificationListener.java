package com.ihor_il.youtube_music_discord_rich_presence.notification_listener;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;

public class NotificationListener extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null || !sbn.getPackageName().equals(NotificationKeys.TARGET)) return;

        Bundle extras = sbn.getNotification().extras;
        NotificationData data = new NotificationData(
                extras.getString(NotificationKeys.ARTIST),
                extras.getString(NotificationKeys.TITLE)
        );

        sendData(data);
    }

    private void sendData(NotificationData data) {
        Intent intent = new Intent(NotificationKeys.BROADCAST_KEY);
        intent.putExtra(NotificationKeys.BROADCAST_DATA_KEY, new Gson().toJson(data));

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        broadcastManager.sendBroadcast(intent);
    }
}
