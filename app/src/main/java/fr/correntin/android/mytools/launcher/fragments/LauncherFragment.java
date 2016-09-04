package fr.correntin.android.mytools.launcher.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.listapps.model.AndroidApp;
import fr.correntin.android.mytools.launcher.model.LauncherAdapter;
import fr.correntin.android.mytools.utils.AndroidAppsComparator;

/**
 * Created by Corentin on 03/05/16.
 */
public class LauncherFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private GridView gridView;
    private LauncherAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.adapter = new LauncherAdapter(this.getActivity());
        this.loadApps();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.launcher_fragment, null);

        this.gridView = (GridView) view.findViewById(R.id.launcher_fragment_grid);
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
        this.gridView.setOnItemLongClickListener(this);
        this.gridView.setNumColumns(4);

        return view;
    }

    private void loadApps()
    {
        final AppsLoader appsLoader = new AppsLoader(this.getActivity());
        appsLoader.execute();
    }
    @Override
    public void onResume()
    {
        super.onResume();
    }

    void updateAndroidApps(List<AndroidApp> androidApps)
    {
        this.adapter.setAndroidApps(androidApps);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        return false;
    }

    private class AppsLoader extends AsyncTask<Void, Void, List<AndroidApp>>
    {
        private SoftReference<Context> context;

        AppsLoader(final Context context)
        {
            this.context = new SoftReference<>(context);
        }

        @Override
        protected List<AndroidApp> doInBackground(Void... voids)
        {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            final List<ResolveInfo> resolveInfos = this.context.get().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL | PackageManager.GET_META_DATA);
            final List<AndroidApp> androidApps = new ArrayList<>();

            for (ResolveInfo resolveInfo : resolveInfos)
            {
                AndroidApp androidApp = new AndroidApp();
                androidApp.setResolveInfo(resolveInfo);
                androidApps.add(androidApp);
            }

            Collections.sort(androidApps, new AndroidAppsComparator(this.context.get()));
            return androidApps;
        }

        @Override
        protected void onPostExecute(List<AndroidApp> androidApps)
        {
            LauncherFragment.this.updateAndroidApps(androidApps);
        }
    }
}
