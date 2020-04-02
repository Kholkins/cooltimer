package com.example.cooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.timer_preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
        }
    }
    private void setPreferenceLabel (Preference preference, String value) {
      if (preference instanceof ListPreference) {
          ListPreference listPreference = (ListPreference)preference;
          int index = listPreference.findIndexOfValue(value);
          if (index >= 0) {
              listPreference.setSummary(listPreference.getEntries()[index]);
          }
      }
    }
}
