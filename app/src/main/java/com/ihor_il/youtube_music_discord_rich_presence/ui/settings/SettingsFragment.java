package com.ihor_il.youtube_music_discord_rich_presence.ui.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentSettingsBinding;
import com.ihor_il.youtube_music_discord_rich_presence.helpers.NumberInRangeTextMask;
import com.ihor_il.youtube_music_discord_rich_presence.networking.NetworkingService;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class SettingsFragment extends Fragment {
    private NetworkingService mService;
    private boolean mBound = false;

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        setMasks();

        SettingsViewModel vm = getViewModel();
        binding.save.setOnClickListener((v) -> saveChanges());
        binding.test.setOnClickListener((v) -> {
            if (!mBound) return;

            try {
                Boolean hasConnection = mService.testConnectionAsync().get();
                Toast.makeText(getContext(), hasConnection ? "Success" : "Failure", Toast.LENGTH_SHORT).show();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        bindObservables(vm);
        vm.loadPreferences();

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getContext(), NetworkingService.class);
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private @NonNull SettingsViewModel getViewModel() {
        return new ViewModelProvider(this)
                .get(SettingsViewModel.class);
    }

    private void setMasks() {
        TextWatcher ipMask = new NumberInRangeTextMask(0, 255);
        binding.ipAddress1.addTextChangedListener(ipMask);
        binding.ipAddress2.addTextChangedListener(ipMask);
        binding.ipAddress3.addTextChangedListener(ipMask);
        binding.ipAddress4.addTextChangedListener(ipMask);

        TextWatcher portMask = new NumberInRangeTextMask(0, 65_535);
        binding.port.addTextChangedListener(portMask);
    }

    private void saveChanges() {
        SettingsViewModel vm = getViewModel();
        String[] values = new String[]{
                binding.ipAddress1.getText().toString(),
                binding.ipAddress2.getText().toString(),
                binding.ipAddress3.getText().toString(),
                binding.ipAddress4.getText().toString(),
                binding.port.getText().toString(),
        };

        int[] intValues = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
        vm.savePreferences(intValues);
        mService.tryRestartAsync();
    }

    private void bindObservables(SettingsViewModel vm) {
        vm.getIp1().observe(getViewLifecycleOwner(), (v) -> binding.ipAddress1.setText(v.toString()));
        vm.getIp2().observe(getViewLifecycleOwner(), (v) -> binding.ipAddress2.setText(v.toString()));
        vm.getIp3().observe(getViewLifecycleOwner(), (v) -> binding.ipAddress3.setText(v.toString()));
        vm.getIp4().observe(getViewLifecycleOwner(), (v) -> binding.ipAddress4.setText(v.toString()));
        vm.getPort().observe(getViewLifecycleOwner(), (v) -> binding.port.setText(v.toString()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            NetworkingService.NetworkingBinder binder = (NetworkingService.NetworkingBinder) service;
            mService = binder.getService();
            mBound = true;

            try {
                mService.tryRestartAsync().get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}