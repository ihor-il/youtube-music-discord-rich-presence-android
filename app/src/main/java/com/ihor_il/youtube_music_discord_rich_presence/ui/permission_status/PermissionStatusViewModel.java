package com.ihor_il.youtube_music_discord_rich_presence.ui.permission_status;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PermissionStatusViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> mPermissionStatus;

    public PermissionStatusViewModel(Application application) {
        super(application);
        mPermissionStatus = new MutableLiveData<>();
        mPermissionStatus.setValue(false);
    }

    public LiveData<Boolean> getPermissionStatus() {
        return mPermissionStatus;
    }

    public void checkPermissionStatus() {
        Application app = getApplication();
        mPermissionStatus.setValue(hasNotificationAccess(app));
    }

    private boolean hasNotificationAccess(Application application) {
        String NOTIFICATION_ACCESS_KEY = "enabled_notification_listeners";
        Context appContext = application.getApplicationContext();

        String appsWithNotifAccess = Settings.Secure.getString(
                appContext.getContentResolver(), NOTIFICATION_ACCESS_KEY
        );

        return appsWithNotifAccess.contains(appContext.getPackageName());
    }
}