package com.ihor_il.youtube_music_discord_rich_presence.networking;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.ihor_il.youtube_music_discord_rich_presence.notification_listener.NotificationKeys;
import com.ihor_il.youtube_music_discord_rich_presence.ui.settings.SettingsKeys;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NetworkingService extends Service {
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final DatagramSocket socket;
    private final IBinder binder = new NetworkingBinder();

    public class NetworkingBinder extends Binder {
        public NetworkingService getService() {
            return NetworkingService.this;
        }
    }

    public NetworkingService() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate() {
        subscribeToBroadcast();
    }

    @Override
    public void onDestroy() {
        unsubscribeFromBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public Future<?> tryRestartAsync() {
        return threadPool.submit(() -> {
            if (!this.buildSocket()) throw new RuntimeException();

        });
    }

    public Future<Boolean> testConnectionAsync() {
        return threadPool.submit(this::test);
    }

    private boolean test() {
        byte[] data = new byte[] { 0 };
        DatagramPacket packet = new DatagramPacket(data, data.length);

        byte[] responseBuffer = new byte[1];
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        try {
            socket.setSoTimeout(1000);
            socket.send(packet);
            socket.receive(responsePacket);
        } catch (IOException ex) {
            Log.d(NetworkingService.class.getName(), ex.getMessage());
            return false;
        }

        return responseBuffer[0] == 1;
    }

    private boolean buildSocket() {
        SharedPreferences prefs = getSharedPreferences(SettingsKeys.PREFERENCES_KEY, MODE_PRIVATE);

        String ip = prefs.getString(SettingsKeys.IP, "0.0.0.0");
        int port = prefs.getInt(SettingsKeys.PORT, 0);

        try {
            InetAddress address = Inet4Address.getByName(ip);
            socket.connect(address, port);
        } catch (UnknownHostException ex) {
            Log.d(NetworkingService.class.getName(), ex.getMessage());
            return false;
        }

        return true;
    }

    private void subscribeToBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.registerReceiver(receiver, new IntentFilter(NotificationKeys.BROADCAST_KEY));
    }

    private void unsubscribeFromBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            if (!socket.isConnected()) return;

            String str = intent.getStringExtra(NotificationKeys.BROADCAST_DATA_KEY);

            NotificationPackage notificationData = new Gson().fromJson(str, NotificationPackage.class);
            notificationData.updateTimestamp();

            str = new Gson().toJson(notificationData);
            byte[] data = str.getBytes(StandardCharsets.UTF_8);

            DatagramPacket pack = new DatagramPacket(data, data.length);
            try {
                socket.send(pack);
            } catch (IOException ex) {
                Log.d(NetworkingService.class.getName(), ex.getMessage());
            }
        }
    };
}