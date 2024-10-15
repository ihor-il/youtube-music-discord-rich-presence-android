package com.ihor_il.youtube_music_discord_rich_presence.ui.current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentCurrentBinding;

public class CurrentFragment extends Fragment {

    private FragmentCurrentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {
        CurrentViewModel dashboardViewModel = new ViewModelProvider(this)
                .get(CurrentViewModel.class);

        binding = FragmentCurrentBinding.inflate(inflater, container, false);

        dashboardViewModel.getTitle().observe(getViewLifecycleOwner(), binding.textTitle::setText);
        dashboardViewModel.getArtist().observe(getViewLifecycleOwner(), binding.textArtist::setText);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}