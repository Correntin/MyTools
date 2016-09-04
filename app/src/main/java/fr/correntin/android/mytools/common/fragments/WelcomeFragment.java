package fr.correntin.android.mytools.common.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.utils.PermissionsUtils;

/**
 * Created by corentin on 31/10/15.
 */
public class WelcomeFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.welcome_fragment, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

       // Log.d("CORENTIN", "onResume: " + PermissionsUtils.hasPermission(this.getActivity(), Manifest.permission.WRITE_CONTACTS));

        if (PermissionsUtils.hasPermission(this.getActivity(), Manifest.permission.WRITE_CONTACTS) == false)
        {
            PermissionsUtils.askPermission(this, Manifest.permission.WRITE_CONTACTS, 8989);
        }
        //this.insertDummyContactWrapper();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        Log.d("CORENTIN", "onRequestPermissionsResult: " + requestCode);
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void insertDummyContactWrapper()
    {
        int hasWriteContactsPermission = this.getActivity().checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            this.getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        Toast.makeText(this.getActivity(), "OK", Toast.LENGTH_SHORT).show();
    }
}
