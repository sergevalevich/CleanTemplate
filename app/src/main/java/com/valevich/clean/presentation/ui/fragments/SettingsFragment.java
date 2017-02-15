package com.valevich.clean.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.valevich.clean.R;


public class SettingsFragment extends PreferenceFragmentCompat
    implements Preference.OnPreferenceChangeListener {

    private boolean mBindingPreference = true;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pref_general, rootKey);
        bindSummaryToValue(findPreference(getString(R.string.pref_theme_key)));
        bindSummaryToValue(findPreference(getString(R.string.pref_font_key)));
        mBindingPreference = false;
    }

    private void bindSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager // immediately set summary when entering fragment not waiting for the preference change
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(),""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String value = o.toString();

        if(!mBindingPreference) {
            if (preference.getKey().equals(getString(R.string.pref_theme_key))) {
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
        return true;
    }
}
