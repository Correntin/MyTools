package fr.correntin.android.mytools.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.utils.AppsInstallationsTrackerUtils;

/**
 * Created by Corentin on 28/08/2016.
 */
public class NotificationsUtils
{
    public static void buildAndNotifyNotification(final Context context, final int notificationId, final CharSequence title, final CharSequence contentText, final Icon icon, final PendingIntent contentIntent, Notification.Action... actions)
    {
        final Notification.Builder notificationBuilder = new Notification.Builder(context);

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(contentText);
        notificationBuilder.setContentIntent(contentIntent);
        notificationBuilder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= 23)
        {
            notificationBuilder.setLargeIcon(icon);
            notificationBuilder.setSmallIcon(icon);
        }

        if (Build.VERSION.SDK_INT >= 20 && actions != null)
        {
            for (Notification.Action action : actions)
                notificationBuilder.addAction(action);
        }

        final Notification notification = notificationBuilder.build();

        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}
