package com.ihor_il.youtube_music_discord_rich_presence.ui.permission_status;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PermissionStatusViewModel extends ViewModel {

    private final MutableLiveData<Boolean> mPermissionStatus;

    public PermissionStatusViewModel() {
        mPermissionStatus = new MutableLiveData<>();
        mPermissionStatus.setValue(false);
    }

    public LiveData<Boolean> getPermissionStatus() {
        return mPermissionStatus;
    }
}