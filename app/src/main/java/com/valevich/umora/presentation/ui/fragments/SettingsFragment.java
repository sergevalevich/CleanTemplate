package com.valevich.umora.presentation.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.valevich.umora.R;
import com.valevich.umora.presentation.ui.activities.MainActivity;

import javax.inject.Inject;


public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    SharedPreferences preferences;

    private boolean mBindingPreference = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);
        preferences.registerOnSharedPreferenceChangeListener(this);
        bindSummaryToValue(getString(R.string.pref_theme_key));
        bindSummaryToValue(getString(R.string.pref_font_key));
        mBindingPreference = false;
    }

    private void bindSummaryToValue(String key) {
        onSharedPreferenceChanged(preferences, key);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        Preference preference = findPreference(key);
        String value = prefs.getString(key,"");
        if(!mBindingPreference) {
            if (isAdded() && key.equals(getString(R.string.pref_theme_key))) {
                getActivity().recreate();
            }

        }

        if(preference instanceof ListPreference) {

            ListPreference listPreference = (ListPreference) preference;

            int prefIndex = listPreference.findIndexOfValue(value);

            if(prefIndex >= 0) listPreference.setSummary(listPreference.getEntries()[prefIndex]);

        } else {
            preference.setSummary(value);
        }
    }
}
