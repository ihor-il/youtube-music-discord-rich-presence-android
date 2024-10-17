package com.ihor_il.youtube_music_discord_rich_presence.networking;

import com.ihor_il.youtube_music_discord_rich_presence.notification_listener.NotificationData;

public class NotificationPackage extends NotificationData {
    private long timestamp;

    public NotificationPackage(String artist, String title) {
        super(artist, title);
        updateTimestamp();
    }

    public void updateTimestamp() {
        timestamp = System.currentTimeMillis() / 1000L;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
