package fr.correntin.android.mytools.boatlocation.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.correntin.android.mytools.services.aidl.IBoatLocationService;

/**
 * Created by Corentin on 21/08/2016.
 */
public class IBoatLocationServiceImplementation extends IBoatLocationService.Stub
        implements LocationListener
{
    private Context context;
    private AtomicBoolean serviceStarted = new AtomicBoolean(false);
    private double lastLongitude, lastLatitude;
    private long lastTime = 0;
    private String phoneNumber = "0676588655";

    public IBoatLocationServiceImplementation(final Context context)
    {
        this.context = context;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws
            RemoteException
    {

    }

    @Override
    public void startTracking() throws RemoteException
    {
        Log.d("CORENTIN", "startTracking: ");

        this.serviceStarted.set(true);
        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
        this.sendSms("Tracker started");
    }

    @Override
    public void stopTracking() throws RemoteException
    {
        Log.d("CORENTIN", "stopTracking: ");

        if (this.locationManager != null)
        {
            this.locationManager.removeUpdates(this);
        }

        this.sendSms("Tracker stopped");
        this.serviceStarted.set(false);
        this.locationManager = null;
    }

    @Override
    public boolean isTrackingRunning()
    {
        return this.serviceStarted.get();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) throws RemoteException
    {
        this.phoneNumber = phoneNumber;
    }

    private LocationManager locationManager;


    @Override
    public void onLocationChanged(Location location)
    {
        Log.d("CORENTIN", "onLocationChanged: " + location + " id=" + Thread.currentThread().getId());
        double currentLongitude = location.getLongitude();
        double currentLatitude = location.getLatitude();

        boolean hasChanged = false;
        if (currentLatitude != this.lastLatitude)
        {
            this.lastLatitude = currentLatitude;
            hasChanged = true;
        }
        if (currentLongitude != this.lastLongitude)
        {
            this.lastLongitude = currentLongitude;
            hasChanged = true;
        }

        long currenTime = System.currentTimeMillis();

        Log.d("CORENTIN", "onLocationChanged: " + (currenTime - this.lastTime));

        if (currenTime - this.lastTime > 60000)
        {
            this.lastTime = currenTime;

            StringBuilder message = new StringBuilder();
            StringBuilder link = new StringBuilder();

            message.append("Lat: ").append(this.lastLatitude).append("\n");
            message.append("Lng: ").append(this.lastLongitude).append("\n");
            message.append("Time: ").append(new Date(location.getTime())).append("\n");
            message.append("Spd: ").append(location.getSpeed() * 3.6f).append("\n");
            message.append("Acc: ").append(location.getAccuracy()).append("\n");
            message.append("Alt: ").append(location.getAltitude()).append("\n");
            link.append("http://google.com/maps/place/").append(this.lastLatitude).append(",").append(this.lastLongitude);


            this.sendSms(message.toString());
            this.sendSms(link.toString());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {
        Log.d("CORENTIN", "onStatusChanged: " + s);
    }

    @Override
    public void onProviderEnabled(String s)
    {
        Log.d("CORENTIN", "onProviderEnabled: " + s);
    }

    @Override
    public void onProviderDisabled(String s)
    {
        Log.d("CORENTIN", "onProviderDisabled: " + s);
    }

    private void sendSms(String message)
    {
        SmsManager sms = SmsManager.getDefault();

        ArrayList<String> parts = sms.divideMessage(message);

        Log.d("CORENTIN", "sendSms: parts=" + parts.size());

        for (String s : parts)
        {
            sms.sendTextMessage(this.phoneNumber, null, s, null, null);
        }
        //sms.sendMultipartTextMessage("0676588655", null, parts, null, null);

    }
}
