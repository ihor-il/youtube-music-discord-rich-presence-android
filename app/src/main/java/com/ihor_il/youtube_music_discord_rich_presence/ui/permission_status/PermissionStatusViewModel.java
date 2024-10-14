package com.ihor_il.youtube_music_discord_rich_presence.ui.permission_status;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PermissionStatusViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PermissionStatusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}