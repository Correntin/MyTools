package fr.correntin.android.mytools.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import fr.correntin.android.mytools.services.TrackerService;
import fr.correntin.android.mytools.utils.AppsInstallationsTrackerUtils;

/**
 * Created by corentin on 12/09/15.
 */
public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {

        if (AppsInstallationsTrackerUtils.isActivated(context) == true)
            context.startService(new Intent(context, TrackerService.class));
    }
}
