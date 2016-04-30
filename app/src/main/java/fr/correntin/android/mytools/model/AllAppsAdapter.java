package fr.correntin.android.mytools.model;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import fr.correntin.android.mytools.R;

/**
 * Created by Corentin on 30/04/16.
 */
public class AllAppsAdapter extends BaseAdapter
{
    private Context context;
    private List<AndroidApp> androidApps;

    public AllAppsAdapter(final Context context)
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
        TextView textViewAppLabel;
        TextView textViewPermissions;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder;

        if (view == null)
        {
            final LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.all_apps_item_fragment, null);
            viewHolder.textViewAppLabel = (TextView) view.findViewById(R.id.all_apps_fragment_list_textview_app_label);
            viewHolder.textViewPermissions = (TextView) view.findViewById(R.id.all_apps_fragment_list_textview_permissions);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (i >= 0 && i < this.androidApps.size())
        {
            final AndroidApp androidApp = this.androidApps.get(i);
            int numPermissions = androidApp.getPermissions(this.context).length;
            String numPermissionsAsString = (numPermissions == 0 ? "No" : String.valueOf(numPermissions));

            viewHolder.textViewAppLabel.setText(androidApp.getLabel(this.context) + " (" + androidApp.getPackageName() + ")");
            viewHolder.textViewPermissions.setText(numPermissionsAsString + " Permissions");

            int color;
            if (numPermissions <= 10)
                color = Color.rgb(58, 137, 35);
            else if (numPermissions <= 20)
                color = Color.rgb(223, 109, 20);
            else
                color = Color.rgb(198, 8, 0);

            viewHolder.textViewPermissions.setTextColor(color);

        }

        return view;
    }
}
