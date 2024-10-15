package com.ihor_il.youtube_music_discord_rich_presence.ui.settings;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentSettingsBinding;
import com.ihor_il.youtube_music_discord_rich_presence.helpers.NumberInRangeTextMask;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        TextWatcher ipMask = new NumberInRangeTextMask(1, 255);
        binding.ipAddress1.addTextChangedListener(ipMask);
        binding.ipAddress2.addTextChangedListener(ipMask);
        binding.ipAddress3.addTextChangedListener(ipMask);
        binding.ipAddress4.addTextChangedListener(ipMask);

        TextWatcher portMask = new NumberInRangeTextMask(1, 65_535);
        binding.port.addTextChangedListener(portMask);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}