package fr.correntin.android.mytools.listapps.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Corentin on 30/04/16.
 */
public class AndroidApp implements Parcelable
{
    private ResolveInfo resolveInfo;
    private PackageInfo packageInfo;
    private CharSequence labelCache;
    private String [] permissions;

    public AndroidApp()
    {
    }

    AndroidApp(Parcel source)
    {
        this.resolveInfo = source.readParcelable(ResolveInfo.class.getClassLoader());
        this.packageInfo = source.readParcelable(PackageInfo.class.getClassLoader());
        source.readStringArray(this.permissions);
        this.labelCache = source.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(this.resolveInfo, flags);
        dest.writeParcelable(this.packageInfo, flags);
        dest.writeStringArray(this.permissions);
        dest.writeString(this.labelCache.toString());

    }

    public static final Parcelable.Creator<AndroidApp> CREATOR = new Parcelable.Creator<AndroidApp>()
    {
        @Override
        public AndroidApp [] newArray(int size)
        {
            return new AndroidApp[size];
        }

        @Override
        public AndroidApp createFromParcel(Parcel source)
        {
            return new AndroidApp(source);
        }
    };

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

    public PackageInfo getPackageInfo()
    {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo)
    {
        this.packageInfo = packageInfo;
    }
}
