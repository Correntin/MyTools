package fr.correntin.android.mytools.appsinstallationstracker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

/**
 * Created by corentin on 29/11/15.
 */
public class NotificationAppActivity extends Activity
{
    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.d("CORENTIN", "onNewIntent: " + intent.getStringExtra("packagename") + "<");
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);

        Intent intent = getIntent();


    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Intent intent = getIntent();

        Log.d("CORENTIN", "onResume: " + intent.getStringExtra("packagename") + "<");
        Intent intentToLaunch = new Intent(Intent.ACTION_MAIN);
        intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentToLaunch.setComponent(new ComponentName(intent.getStringExtra("packagename"),intent.getStringExtra("classname")));
        startActivity(intentToLaunch);
    }
}
