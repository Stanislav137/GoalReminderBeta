package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goalreminderbeta.sa.goalreminderbeta.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Config extends PreferenceFragment {

    private static String[]days = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private static Set<String> selectedDays = new HashSet<>(Arrays.asList(days));
    private static Set<String> selected = new HashSet<>();
    //private MultiSelectListPreference preference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config);

        Preference.OnPreferenceChangeListener daysListener
                = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
                try {
                    selected = (Set<String>) value;
                } catch (ClassCastException ignored) {
                }
                return true;
            }
        };

        //preference.setOnPreferenceChangeListener(daysListener);
    }
}
