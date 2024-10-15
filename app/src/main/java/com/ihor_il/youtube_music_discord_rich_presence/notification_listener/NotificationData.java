package com.ihor_il.youtube_music_discord_rich_presence.notification_listener;

import java.io.Serializable;

public class NotificationData implements Serializable {
    private final String artist;
    private final String title;

    public NotificationData(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }
}
