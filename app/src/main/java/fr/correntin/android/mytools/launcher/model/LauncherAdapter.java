package fr.correntin.android.mytools.launcher.model;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import fr.correntin.android.mytools.R;
import fr.correntin.android.mytools.listapps.model.AndroidApp;

/**
 * Created by Corentin on 30/04/16.
 */
public class LauncherAdapter extends BaseAdapter
{
    private Context context;
    private List<AndroidApp> androidApps;

    public LauncherAdapter(final Context context)
    {
        this.context = context;
    }

    public void setAndroidApps(List<AndroidApp> androidApps)
    {
        this.androidApps = androidApps;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        if (this.androidApps != null)
            return this.androidApps.size();

        return 0;
    }

    @Override
    public Object getItem(int i)
    {
        if (this.androidApps != null)
            return this.androidApps.get(i);

        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    class ViewHolder
    {
        ImageView appIcon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder;

        if (view == null)
        {
            final LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.launcher_item_fragment, null);
            viewHolder.appIcon = (ImageView) view.findViewById(R.id.launcher_item_fragment_app_icon);
            viewHolder.appIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);

            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (i >= 0 && i < this.androidApps.size())
        {
            final AndroidApp androidApp = this.androidApps.get(i);
            ResolveInfo resolveInfo = androidApp.getResolveInfo();
            final Drawable drawable = resolveInfo.loadIcon(this.context.getPackageManager());

            viewHolder.appIcon.setImageDrawable(drawable);

        }

        return view;
    }
}
