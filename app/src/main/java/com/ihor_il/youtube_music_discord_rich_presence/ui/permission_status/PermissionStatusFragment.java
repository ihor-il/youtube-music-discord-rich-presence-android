package com.ihor_il.youtube_music_discord_rich_presence.ui.permission_status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentPermissionStatusBinding;

public class PermissionStatusFragment extends Fragment {

    private FragmentPermissionStatusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PermissionStatusViewModel homeViewModel =
                new ViewModelProvider(this).get(PermissionStatusViewModel.class);

        binding = FragmentPermissionStatusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}