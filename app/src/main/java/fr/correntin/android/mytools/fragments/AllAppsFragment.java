package fr.correntin.android.mytools.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.correntin.android.mytools.MainActivity;
import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.SimpleDialog;
import fr.correntin.android.mytools.model.AllAppsAdapter;
import fr.correntin.android.mytools.model.AndroidApp;
import fr.correntin.android.mytools.utils.AndroidAppsComparator;

/**
 * Created by Corentin on 30/04/16.
 */
public class AllAppsFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private ListView listView;
    private AllAppsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.adapter = new AllAppsAdapter(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.all_apps_fragment, null);

        this.listView = (ListView) view.findViewById(R.id.all_apps_fragment_list);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(this);
        this.listView.setOnItemLongClickListener(this);

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
        this.loadApps();
    }

    void updateAndroidApps(List<AndroidApp> androidApps)
    {
        this.adapter.setAndroidApps(androidApps);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        final AndroidApp androidApp = (AndroidApp) this.adapter.getItem(i);

        final Intent intentApp = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intentApp.setData(Uri.parse("package:" + androidApp.getPackageName()));

        this.getActivity().startActivity(intentApp);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        final AndroidApp androidApp = (AndroidApp) this.adapter.getItem(i);
        final StringBuilder stringBuilder = new StringBuilder();

        for (String permission : androidApp.getPermissions(this.getActivity()))
            stringBuilder.append(permission).append("\n");

        final Intent intent = new Intent(this.getActivity(), SimpleDialog.class);
        intent.putExtra(SimpleDialog.TEXT_EXTRA, stringBuilder.toString());

        this.getActivity().startActivity(intent);
        return true;
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
            AllAppsFragment.this.updateAndroidApps(androidApps);
        }
    }
}
