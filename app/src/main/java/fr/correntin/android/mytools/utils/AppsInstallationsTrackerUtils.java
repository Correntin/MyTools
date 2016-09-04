package fr.correntin.android.mytools.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import fr.correntin.android.mytools.appsinstallationstracker.NotificationAppActivity;
import fr.correntin.android.mytools.R;

/**
 * Created by corentin on 13/11/15.
 */
public class AppsInstallationsTrackerUtils
{
    public static boolean isActivated(final Context context)
    {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("fr.correntin.android.mytools_preferences", Context.MODE_PRIVATE);
        final boolean activated = sharedPreferences.getBoolean(context.getString(R.string.apps_tracker_preferences_activation_key), true);

        Log.d("CORENTIN", "AppsInstallationsTrackerUtils.isActivated()=" + activated);

        return activated;
    }

    public static Notification.Action buildNotificationActionToLaunchApp(final Context context, ApplicationInfo applicationInfo)
    {
        if (applicationInfo.packageName == null || applicationInfo.className == null)
            return null;

        Intent intent = new Intent(context, NotificationAppActivity.class);
        intent.putExtra("packagename", applicationInfo.packageName);
        intent.putExtra("classname", applicationInfo.className);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return AppsInstallationsTrackerUtils.buildNotificationAction(context, android.R.drawable.ic_media_play, "Start App", pendingIntent);
    }

    public static Notification.Action buildNotificationAction(final Context context, final int icon, final String title, final PendingIntent pendingIntent)
    {
        if (Build.VERSION.SDK_INT >= 23)
            return (new Notification.Action.Builder(Icon.createWithResource(context, icon), title, pendingIntent)).build();
        return null;
    }

    public static PendingIntent buildAndroidDetailsAppsIntent(final Context context, final String packageName)
    {
        final Intent intentApp = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intentApp.setData(Uri.parse("package:" + packageName));

        return PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intentApp, 0);
    }

    public static Icon buildCurrentAppIcon(Context applicationContext, ApplicationInfo applicationInfo)
    {
        if (Build.VERSION.SDK_INT >= 23)
            return Icon.createWithBitmap(((BitmapDrawable) applicationContext.getPackageManager().getApplicationIcon(applicationInfo)).getBitmap());
        return Icon.createWithResource(applicationContext, applicationInfo.icon);
    }

}
