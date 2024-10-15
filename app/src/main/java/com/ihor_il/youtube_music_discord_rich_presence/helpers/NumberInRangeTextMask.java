package com.ihor_il.youtube_music_discord_rich_presence.helpers;

import android.text.Editable;
import android.text.TextWatcher;

public class NumberInRangeTextMask implements TextWatcher {
    private String value = "";
    private boolean isEditing = false;

    private final Integer MIN;
    private final Integer MAX;

    public NumberInRangeTextMask(int min, int max) {
        MIN = Math.min(min, max);
        MAX = Math.max(max, min);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (isEditing) return;
        value = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newValue = s.toString();
        if (newValue.contentEquals(value) || isEditing) return;

        value = newValue;
        if (newValue.isEmpty()) return;

        int intValue = Integer.parseInt(newValue);
        if (intValue < MIN) {
            value = MIN.toString();
            return;
        }

        if (intValue > MAX) {
            value = MAX.toString();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isEditing) return;

        isEditing = true;

        s.clear();
        s.insert(0, value);

        isEditing = false;
    }
};
