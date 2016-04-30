package fr.correntin.android.mytools.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * Created by Corentin on 30/04/16.
 */
public class AndroidApp
{
    private ResolveInfo resolveInfo;
    private CharSequence labelCache;
    private String [] permissions;

    public void setResolveInfo(ResolveInfo resolveInfo)
    {
        this.resolveInfo = resolveInfo;
    }

    public ResolveInfo getResolveInfo()
    {
        return resolveInfo;
    }

    public String getPackageName()
    {
        return resolveInfo.activityInfo.packageName;
    }

    public CharSequence getLabel(final Context context)
    {
        if (this.labelCache == null)
            this.labelCache = this.resolveInfo.loadLabel(context.getPackageManager());

        return this.labelCache;
    }

    public String [] getPermissions(final Context context)
    {
        if (this.permissions == null)
        {
            try
            {
                this.permissions = context.getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
                if (this.permissions == null)
                    this.permissions = new String [0];
            }
            catch (PackageManager.NameNotFoundException exception)
            {
                exception.printStackTrace();
            }
        }
        return this.permissions;
    }
}
