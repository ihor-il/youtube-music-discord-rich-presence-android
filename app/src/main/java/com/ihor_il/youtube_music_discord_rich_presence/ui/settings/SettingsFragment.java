package com.ihor_il.youtube_music_discord_rich_presence.ui.settings;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentSettingsBinding;
import com.ihor_il.youtube_music_discord_rich_presence.helpers.NumberInRangeTextMask;

import java.util.Arrays;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        setMasks();

        SettingsViewModel vm = getViewModel();
        binding.save.setOnClickListener((v) -> saveChanges());

        bindObservables(vm);
        vm.loadPreferences();

        return binding.getRoot();
    }

    private SettingsViewModel getViewModel() {
        return new ViewModelProvider(this)
                .get(SettingsViewModel.class);
    }

    private void setMasks() {
        TextWatcher ipMask = new NumberInRangeTextMask(1, 255);
        binding.ipAddress1.addTextChangedListener(ipMask);
        binding.ipAddress2.addTextChangedListener(ipMask);
        binding.ipAddress3.addTextChangedListener(ipMask);
        binding.ipAddress4.addTextChangedListener(ipMask);

        TextWatcher portMask = new NumberInRangeTextMask(1, 65_535);
        binding.port.addTextChangedListener(portMask);
    }

    private void saveChanges() {
       SettingsViewModel vm = getViewModel();
       String[] values = new String[] {
           binding.ipAddress1.getText().toString(),
           binding.ipAddress2.getText().toString(),
           binding.ipAddress3.getText().toString(),
           binding.ipAddress4.getText().toString(),
           binding.port.getText().toString(),
       };

       int[] intValues = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
       vm.savePreferences(intValues);
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
}