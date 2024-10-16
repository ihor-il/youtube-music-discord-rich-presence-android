package com.ihor_il.youtube_music_discord_rich_presence.ui.settings;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SettingsViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mIp1 = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> mIp2 = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> mIp3 = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> mIp4 = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> mPort = new MutableLiveData<>(0);

    public SettingsViewModel(Application app) {
        super(app);
    }

    public void loadPreferences() {
        SharedPreferences prefs = getApplication().getSharedPreferences(SettingsKeys.PREFERENCES_KEY, Context.MODE_PRIVATE);

        mIp1.setValue(prefs.getInt(SettingsKeys.IP_1, 0));
        mIp2.setValue(prefs.getInt(SettingsKeys.IP_2, 0));
        mIp3.setValue(prefs.getInt(SettingsKeys.IP_3, 0));
        mIp4.setValue(prefs.getInt(SettingsKeys.IP_4, 0));
        mPort.setValue(prefs.getInt(SettingsKeys.PORT, 0));
    }

    public void savePreferences(int[] values) {
        if (values.length != 5) return;

        SharedPreferences prefs = getApplication().getSharedPreferences(SettingsKeys.PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(SettingsKeys.IP_1, values[0]);
        editor.putInt(SettingsKeys.IP_2, values[1]);
        editor.putInt(SettingsKeys.IP_3, values[2]);
        editor.putInt(SettingsKeys.IP_4, values[3]);
        editor.putInt(SettingsKeys.PORT, values[4]);

        editor.apply();
    }

    public LiveData<Integer> getIp1() {
        return mIp1;
    }

    public LiveData<Integer> getIp2() {
        return mIp2;
    }

    public LiveData<Integer> getIp3() {
        return mIp3;
    }

    public LiveData<Integer> getIp4() {
        return mIp4;
    }

    public LiveData<Integer> getPort() {
        return mPort;
    }
}