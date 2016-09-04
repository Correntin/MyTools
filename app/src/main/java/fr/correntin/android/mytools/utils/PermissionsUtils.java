package fr.correntin.android.mytools.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by Corentin on 04/09/2016.
 */
public class PermissionsUtils
{
    public static boolean hasPermission(final Context context, final String permission)
    {
        final int hasPermission = context.checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        return (hasPermission == PackageManager.PERMISSION_GRANTED);
    }

    public static void askPermission(final Activity context, final String permission, int requestCode)
    {
        context.requestPermissions(new String[]{permission}, requestCode);
    }

    public static void askPermission(final Fragment context, final String permission, int requestCode)
    {
        context.requestPermissions(new String[]{permission}, requestCode);
    }
}