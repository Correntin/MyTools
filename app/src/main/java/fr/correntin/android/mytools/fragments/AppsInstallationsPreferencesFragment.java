package fr.correntin.android.mytools.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.services.TrackerService;
import fr.correntin.android.mytools.utils.AppsInstallationsTrackerUtils;

/**
 * Created by corentin on 31/10/15.
 */
public class AppsInstallationsPreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_installations_tracker_preferences);

        if (AppsInstallationsTrackerUtils.isActivated(this.getActivity()) == true)
            this.getActivity().startService(new Intent(this.getActivity(), TrackerService.class));

        this.updateSubPreferences();
    }

    private void updateSubPreferences()
    {
        final boolean activated = AppsInstallationsTrackerUtils.isActivated(this.getActivity());

        for (int i = 1; i < this.getPreferenceScreen().getPreferenceCount(); i++)
            this.getPreferenceScreen().getPreference(i).setEnabled(activated);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
    {
        if (preference.getKey().equals(this.getString(R.string.apps_tracker_preferences_activation_key)))
            this.updateSubPreferences();

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
