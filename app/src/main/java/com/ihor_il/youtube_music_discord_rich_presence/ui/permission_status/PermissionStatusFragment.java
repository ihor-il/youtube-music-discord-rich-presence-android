package com.ihor_il.youtube_music_discord_rich_presence.ui.permission_status;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.R;
import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentPermissionStatusBinding;

public class PermissionStatusFragment extends Fragment {

    private FragmentPermissionStatusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PermissionStatusViewModel vm = new ViewModelProvider(this)
                .get(PermissionStatusViewModel.class);

        binding = FragmentPermissionStatusBinding.inflate(inflater, container, false);

        vm.getPermissionStatus().observe(getViewLifecycleOwner(), this::setPermissionStatusControls);
        return binding.getRoot();
    }

    private void setPermissionStatusControls(boolean hasPermission) {
        final Resources resources = getResources();

        binding.status.setText(resources.getString(getTextStringId(hasPermission)));
        binding.status.setTextColor(resources.getColor(getTextColorId(hasPermission)));
        binding.openSettingsButton.setVisibility(getButtonVisibility(hasPermission));
    }

    private int getTextStringId(boolean hasPermission) {
        return hasPermission
                ? R.string.permission_status_allowed
                : R.string.permission_status_restricted;
    }

    private int getTextColorId(boolean hasPermission)
    {
        return hasPermission
                ? R.color.purple_500
                : R.color.teal_700;
    }

    private int getButtonVisibility(boolean hasPermission)
    {
        return hasPermission ? GONE : VISIBLE;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}