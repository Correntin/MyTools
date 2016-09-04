package fr.correntin.android.mytools.utils;

import android.content.Context;

import java.util.Comparator;

import fr.correntin.android.mytools.listapps.model.AndroidApp;

/**
 * Created by Corentin on 01/05/16.
 */
public class AndroidAppsComparator implements Comparator<AndroidApp>
{
    private Context context;

    public AndroidAppsComparator(Context context)
    {
        this.context = context;
    }

    @Override
    public int compare(AndroidApp androidApp1, AndroidApp androidApp2)
    {
        if (androidApp1 != null && androidApp2 == null)
            return 1;
        else if (androidApp1 == null && androidApp2 != null)
            return -1;
        else if (androidApp1 == null && androidApp2 == null)
            return 0;

        final String androidApp1Label = androidApp1.getLabel(this.context).toString();
        final String androidApp2Label = androidApp2.getLabel(this.context).toString();

        return androidApp1Label.compareToIgnoreCase(androidApp2Label);
    }
}
