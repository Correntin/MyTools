package fr.correntin.android.mytools.boatlocation.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Corentin on 12/08/2016.
 */
public class MmsReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("CORENTIN", "onReceive: intent action=" + intent.getAction());
    }
}
