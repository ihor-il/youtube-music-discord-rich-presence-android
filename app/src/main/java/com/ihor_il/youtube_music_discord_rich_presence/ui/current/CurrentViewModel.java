package com.ihor_il.youtube_music_discord_rich_presence.ui.current;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.ihor_il.youtube_music_discord_rich_presence.notification_listener.NotificationData;
import com.ihor_il.youtube_music_discord_rich_presence.notification_listener.NotificationKeys;

public class CurrentViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mTitle = new MutableLiveData<>();
    private final MutableLiveData<String> mArtist = new MutableLiveData<>();

    public LiveData<String> getTitle() {
        return mTitle;
    }
    public LiveData<String> getArtist() { return mArtist; }

    public CurrentViewModel(Application app) {
        super(app);
        subscribeToBroadcast();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        unsubscribeFromBroadcast();
    }

    private void subscribeToBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplication().getApplicationContext());
        manager.registerReceiver(receiver, new IntentFilter(NotificationKeys.BROADCAST_KEY));
    }

    private void unsubscribeFromBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplication().getApplicationContext());
        manager.unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            String extra = intent.getStringExtra(NotificationKeys.BROADCAST_DATA_KEY);
            NotificationData data = new Gson().fromJson(extra, NotificationData.class);

            mTitle.setValue(data.getTitle());
            mArtist.setValue(data.getArtist());
        }
    };
}