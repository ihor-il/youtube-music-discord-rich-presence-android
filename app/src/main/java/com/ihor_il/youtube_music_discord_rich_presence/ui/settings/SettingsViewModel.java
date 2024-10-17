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

        String ip = prefs.getString(SettingsKeys.IP, "0.0.0.0");
        String[] ipValues = ip.split("\\.");

        mIp1.setValue(Integer.parseInt(ipValues[0]));
        mIp2.setValue(Integer.parseInt(ipValues[1]));
        mIp3.setValue(Integer.parseInt(ipValues[2]));
        mIp4.setValue(Integer.parseInt(ipValues[3]));
        mPort.setValue(prefs.getInt(SettingsKeys.PORT, 0));
    }

    public void savePreferences(int[] v) {
        if (v.length != 5) return;

        SharedPreferences prefs = getApplication().getSharedPreferences(SettingsKeys.PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(SettingsKeys.IP, v[0] + "." + v[1] + "." + v[2] + "." + v[3]);
        editor.putInt(SettingsKeys.PORT, v[4]);

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