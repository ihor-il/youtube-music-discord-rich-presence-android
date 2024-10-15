package com.ihor_il.youtube_music_discord_rich_presence.ui.permission_status;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ihor_il.youtube_music_discord_rich_presence.R;
import com.ihor_il.youtube_music_discord_rich_presence.databinding.FragmentPermissionStatusBinding;

public class PermissionStatusFragment extends Fragment {

    private FragmentPermissionStatusBinding binding;

    private final ActivityResultLauncher<Intent> settingsActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> getViewModel().checkPermissionStatus()
    );

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentPermissionStatusBinding.inflate(inflater, container, false);

        PermissionStatusViewModel vm = getViewModel();
        vm.getPermissionStatus().observe(getViewLifecycleOwner(), this::setPermissionStatusControls);
        vm.checkPermissionStatus();

        binding.openSettingsButton.setOnClickListener(v -> openSettings());

        return binding.getRoot();
    }

    private @NonNull PermissionStatusViewModel getViewModel() {
        return new ViewModelProvider(this)
                .get(PermissionStatusViewModel.class);
    }

    private void setPermissionStatusControls(boolean hasPermission) {
        binding.status.setText(getTextString(hasPermission));
        binding.status.setTextColor(getTextColor(hasPermission));
    }

    private @NonNull String getTextString(boolean hasPermission) {
        int stringId = hasPermission
                ? R.string.permission_status_allowed
                : R.string.permission_status_restricted;

        return getResources().getString(stringId);
    }

    private int getTextColor(boolean hasPermission) {
        int colorCode = hasPermission
                ? com.google.android.material.R.attr.colorPrimary
                : com.google.android.material.R.attr.colorError;

        TypedValue value = new TypedValue();
        Resources.Theme theme = this.requireActivity().getTheme();
        theme.resolveAttribute(colorCode, value, true);
        return ContextCompat.getColor(this.requireContext(), value.resourceId);
    }

    private void openSettings() {
        final String INTENT_KEY = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
        settingsActivityLauncher.launch(new Intent(INTENT_KEY));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}