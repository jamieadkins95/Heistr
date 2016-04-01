package com.jamieadkins.heistr.Fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.jamieadkins.heistr.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Don't need to do anything.
    }
}
