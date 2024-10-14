package com.ihor_il.youtube_music_discord_rich_presence.ui.current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentCurrentBinding;

public class CurrentFragment extends Fragment {

    private FragmentCurrentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CurrentViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CurrentViewModel.class);

        binding = FragmentCurrentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}