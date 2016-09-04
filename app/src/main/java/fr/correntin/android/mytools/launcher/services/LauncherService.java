package fr.correntin.android.mytools.launcher.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import fr.correntin.android.mytools.services.aidl.ILauncherService;

/**
 * Created by Corentin on 04/05/16.
 */
public class LauncherService extends Service
{

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


}
