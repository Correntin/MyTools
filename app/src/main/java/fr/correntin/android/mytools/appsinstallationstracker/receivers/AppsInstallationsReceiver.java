package fr.correntin.android.mytools.appsinstallationstracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import fr.correntin.android.mytools.appsinstallationstracker.services.TrackerService;
import fr.correntin.android.mytools.utils.AppsInstallationsTrackerUtils;
import fr.correntin.android.mytools.utils.Constants;


/**
 * Created by corentin on 12/09/15.
 */
public class AppsInstallationsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (AppsInstallationsTrackerUtils.isActivated(context) == true)
        {
            final Intent intentPackage = new Intent(context, TrackerService.class);
            final Bundle bundle = intent.getExtras();

            bundle.putString(Constants.APPS_INSTALLATIONS_TRACKER_ANDROID_INTENT_ACTION, intent.getAction());

            intentPackage.setData(intent.getData());
            intentPackage.putExtras(bundle);

            context.startService(intentPackage);
        }
    }
}
