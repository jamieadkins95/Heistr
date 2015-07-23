package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterBuildList extends ArrayAdapter<Build>{


    List<Build> builds;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view_build_2, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvName);
        TextView primaryWeapon = (TextView)rowView.findViewById(R.id.tvPrimaryWeapon);
        TextView pointsUsed = (TextView)rowView.findViewById(R.id.tvPointsUsed);

        textView.setText(builds.get(position).getName());
        //pointsUsed.setText(builds.get(position).getPointsUsed() + "");
        //primaryWeapon.setText(builds.get(position).getPrimaryWeapon());

        // change the icon for Windows and iPhone


        return rowView;
    }

    public ArrayAdapterBuildList(Context context, List<Build> b){
        super(context, -1, b);
        this.builds = b;
        this.context = context;
    }



}
