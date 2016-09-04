package fr.correntin.android.mytools.boatlocation.fragments;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.boatlocation.services.BoatLocationService;
import fr.correntin.android.mytools.services.aidl.IBoatLocationService;

/**
 * Created by Corentin on 08/08/2016.
 */
public class BoatLocationFragment extends Fragment
        implements View.OnClickListener, ServiceConnection
{
    private static final String TAG = "BoatLocationFragment";
    private boolean serviceConnected = false;
    private IBoatLocationService boatLocationService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.boat_location_fragment, container, false);

        final Button startStopService = (Button) view.findViewById(R.id.boat_location_start_stop_service_button);
        startStopService.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Intent intent = new Intent(this.getActivity(), BoatLocationService.class);
        boolean succeed = this.getActivity().bindService(intent, this, Context.BIND_AUTO_CREATE);
        Log.d("CORENTIN", "onResume: " + succeed);
    }

    @Override
    public void onPause()
    {
        Log.d("CORENTIN", "onPause: ");

        this.getActivity().unbindService(this);
        super.onPause();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.boat_location_start_stop_service_button)
        {
            if (this.serviceConnected)
            {
                try
                {
                    if (this.boatLocationService.isTrackingRunning())
                    {
                        final String phoneNumber = ((EditText) this.getView().findViewById(R.id.boat_location_phone_number_edittext)).getText().toString();


                        this.boatLocationService.setPhoneNumber(phoneNumber);
                        this.boatLocationService.startTracking();


                        Log.d("CORENTIN", "onClick: phoneNumber=" + phoneNumber);
                    } else
                    {
                        this.boatLocationService.stopTracking();
                    }
                }
                catch (RemoteException remoteException)
                {
                    Toast.makeText(BoatLocationFragment.this.getActivity(), "Can't communicate with service", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: ", remoteException);
                }
            } else
            {
                Toast.makeText(BoatLocationFragment.this.getActivity(), "Service not connected!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service)
    {
        Log.d("CORENTIN", "onServiceConnected: ");

        this.serviceConnected = true;
        this.getView().findViewById(R.id.boat_location_start_stop_service_button).setEnabled(true);
        this.boatLocationService = IBoatLocationService.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName)
    {
        Log.d("CORENTIN", "onServiceDisconnected: ");

        this.serviceConnected = false;
        this.getView().findViewById(R.id.boat_location_start_stop_service_button).setEnabled(false);
        this.boatLocationService = null;
    }
}
