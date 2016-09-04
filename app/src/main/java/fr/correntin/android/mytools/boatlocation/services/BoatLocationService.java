package fr.correntin.android.mytools.boatlocation.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Corentin on 08/08/2016.
 */
public class BoatLocationService extends Service
{
    private IBoatLocationServiceImplementation iBoatLocationServiceImplementation;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d("CORENTIN", "onCreate: iBoatLocationServiceImplementation=" + this.iBoatLocationServiceImplementation + " this=" + this);

        this.iBoatLocationServiceImplementation = new IBoatLocationServiceImplementation(this.getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d("CORENTIN", "onBind: iBoatLocationServiceImplementation=" + this.iBoatLocationServiceImplementation + " this=" + this);

        return this.iBoatLocationServiceImplementation;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("CORENTIN", "onStartCommand: iBoatLocationServiceImplementation=" + this.iBoatLocationServiceImplementation + " this=" + this);

        if (intent != null && intent.getAction() != null && intent.getAction().equals("LAUNCH_BY_SMS"))
        {
            String command = intent.getStringExtra("command");

            if (TextUtils.isEmpty(command))
            {
                Log.d("CORENTIN", "onStartCommand: invalid command=" + command);
            }
            else
            {
                try
                {
                    if (command.equals("start-service"))
                    {
                        this.iBoatLocationServiceImplementation.startTracking();
                    } else if (command.equals("stop-service"))
                    {
                        this.iBoatLocationServiceImplementation.stopTracking();
                    }
                }
                catch (RemoteException remoteException)
                {
                    Log.e("CORENTIN", "onStartCommand: ", remoteException);
                }
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        Log.d("CORENTIN", "onStart: iBoatLocationServiceImplementation=" + this.iBoatLocationServiceImplementation + " this=" + this);
    }

    @Override
    public void onDestroy()
    {
        Log.d("CORENTIN", "onDestroy: iBoatLocationServiceImplementation=" + this.iBoatLocationServiceImplementation + " this=" + this);
        super.onDestroy();
    }

}
