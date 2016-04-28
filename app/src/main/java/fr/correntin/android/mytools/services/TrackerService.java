package fr.correntin.android.mytools.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.utils.AppsInstallationsTrackerUtils;
import fr.correntin.android.mytools.utils.Constants;

/**
 * Created by corentin on 12/09/15.
 */
public class TrackerService extends Service
{

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent.getDataString() != null)
            this.handleNewApp(intent.getDataString(), intent.getExtras());
        return START_NOT_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        if (intent.getDataString() != null)
            this.handleNewApp(intent.getDataString(), intent.getExtras());
    }

    private void handleNewApp(String packageName, final Bundle bundle)
    {
        if (AppsInstallationsTrackerUtils.isActivated(this.getApplicationContext()) == false)
            return;

        final String androidIntentAction = bundle.getString(Constants.APPS_INSTALLATIONS_TRACKER_ANDROID_INTENT_ACTION);

        Log.d("CORENTIN", "handleNewApp: action=" + androidIntentAction);

        for (String key : bundle.keySet())
        {
            Log.d("CORENTIN", "handleNewApp: key=" + key + " value=" + bundle.get(key));
        }

        packageName = packageName.substring(8);

        final PackageManager packageManager = this.getPackageManager();

        ApplicationInfo applicationInfo = null;

        try
        {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        }
        catch (Exception e)
        {
            Toast.makeText(this, packageName + " : Not Found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            applicationInfo = null;
        }

        if (Intent.ACTION_PACKAGE_ADDED.equals(androidIntentAction))
        {
            this.handlePackageAdded(applicationInfo);
        }
        else if (Intent.ACTION_PACKAGE_CHANGED.equals(androidIntentAction))
        {
            this.handlePackageChanged(applicationInfo);
        }
        else if (Intent.ACTION_PACKAGE_REPLACED.equals(androidIntentAction))
        {

        }
        else if (Intent.ACTION_PACKAGE_RESTARTED.equals(androidIntentAction))
        {

        }
        else if (Intent.ACTION_PACKAGE_REMOVED.equals(androidIntentAction))
        {

        }
        else if (Intent.ACTION_PACKAGE_FULLY_REMOVED.equals(androidIntentAction))
        {

        }
        else if (Intent.ACTION_PACKAGE_DATA_CLEARED.equals(androidIntentAction))
        {

        }
        else if (Intent.ACTION_PACKAGE_FIRST_LAUNCH.equals(androidIntentAction))
        {

        }


    }

    private void handlePackageChanged(ApplicationInfo applicationInfo)
    {
        final PackageManager packageManager = this.getPackageManager();

        final PendingIntent androidDetailsAppsIntent = AppsInstallationsTrackerUtils.buildAndroidDetailsAppsIntent(this.getApplicationContext(), applicationInfo.packageName);
        final Icon appIcon = AppsInstallationsTrackerUtils.buildCurrentAppIcon(this.getApplicationContext(), applicationInfo);
        final Notification.Action action = AppsInstallationsTrackerUtils.buildNotificationActionToLaunchApp(this.getApplicationContext(), applicationInfo);

        final Notification.Builder notificationBuilder = new Notification.Builder(this);

        notificationBuilder.setContentTitle(packageManager.getApplicationLabel(applicationInfo));
        notificationBuilder.setContentText(this.getString(R.string.apps_installations_tracker_title_package_changed) + " : " + applicationInfo.packageName);
        notificationBuilder.setLargeIcon(appIcon);
        notificationBuilder.setSmallIcon(appIcon);
        notificationBuilder.setContentIntent(androidDetailsAppsIntent);
        notificationBuilder.setAutoCancel(true);

        if (action != null)
            notificationBuilder.addAction(action);

        final Notification notification = notificationBuilder.build();

        AppsInstallationsTrackerUtils.addNotification(this.getApplicationContext(), notification, applicationInfo.uid);
    }

    private void handlePackageAdded(ApplicationInfo applicationInfo)
    {
        final PackageManager packageManager = this.getPackageManager();

        final PendingIntent androidDetailsAppsIntent = AppsInstallationsTrackerUtils.buildAndroidDetailsAppsIntent(this.getApplicationContext(), applicationInfo.packageName);
        final Icon appIcon = AppsInstallationsTrackerUtils.buildCurrentAppIcon(this.getApplicationContext(), applicationInfo);
        final Notification.Action action = AppsInstallationsTrackerUtils.buildNotificationActionToLaunchApp(this.getApplicationContext(), applicationInfo);

        final Notification.Builder notificationBuilder = new Notification.Builder(this);

        notificationBuilder.setContentTitle(packageManager.getApplicationLabel(applicationInfo));
        notificationBuilder.setContentText(this.getString(R.string.apps_installations_tracker_title_package_changed) + " : " + applicationInfo.packageName);
        notificationBuilder.setLargeIcon(appIcon);
        notificationBuilder.setSmallIcon(appIcon);
        notificationBuilder.setContentIntent(androidDetailsAppsIntent);
        notificationBuilder.setAutoCancel(true);

        if (action != null)
            notificationBuilder.addAction(action);

        final Notification notification = notificationBuilder.build();

        AppsInstallationsTrackerUtils.addNotification(this.getApplicationContext(), notification, applicationInfo.uid);
    }
}
