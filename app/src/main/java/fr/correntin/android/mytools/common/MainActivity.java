package fr.correntin.android.mytools.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.launcher.fragments.LauncherFragment;
import fr.correntin.android.mytools.listapps.fragments.AllAppsFragment;
import fr.correntin.android.mytools.appsinstallationstracker.fragments.AppsInstallationsPreferencesFragment;
import fr.correntin.android.mytools.boatlocation.fragments.BoatLocationFragment;
import fr.correntin.android.mytools.common.fragments.WelcomeFragment;
import fr.correntin.android.mytools.boatlocation.services.BoatLocationService;
import fr.correntin.android.mytools.common.threads.ThreadPool;
import fr.correntin.android.mytools.common.threads.ThreadPoolTaskListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private FloatingActionButton floatingActionButton;

    private void addFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    ThreadPool<Integer, Void> threadPool;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("CORENTIN", "onClick: ");
                threadPool.execute(i++);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });


        this.addFragment(new WelcomeFragment());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        threadPool = ThreadPool.create(2);
        threadPool.registerListener(new ThreadPoolTaskListener()
        {
            @Override
            public void onThreadPoolTaskStarted(Object paramValue)
            {

            }

            @Override
            public void onThreadPoolTaskFinished(Object returnValue)
            {

            }
        });

        Intent startServiceIntent = new Intent(this, BoatLocationService.class);
        this.startService(startServiceIntent);

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            this.replaceFragment(new WelcomeFragment());
        }
        else if (id == R.id.nav_apps_installations)
        {
            this.replaceFragment(new AppsInstallationsPreferencesFragment());
        }
        else if (id == R.id.nav_share)
        {
            Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.nav_my_work)
        {
            Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.nav_list_apps)
        {
            this.replaceFragment(new AllAppsFragment());
        }
        else if (id == R.id.nav_boat_location)
        {
            this.replaceFragment(new BoatLocationFragment());
        }
        else if (id == R.id.nav_launcher)
        {
            this.replaceFragment(new LauncherFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
