package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterListCheckable extends ArrayAdapter<String>{


    List<String> infamies;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_checkable, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvInfamyName);
        CheckBox cbInfamy = (CheckBox) rowView.findViewById(R.id.cbInfamy);




        textView.setText(infamies.get(position));
        //pointsUsed.setText(builds.get(position).getPointsUsed() + "");
        //primaryWeapon.setText(builds.get(position).getPrimaryWeapon());

        // change the icon for Windows and iPhone


        return rowView;
    }

    public ArrayAdapterListCheckable(Context context, List<String> i){
        super(context, -1, i);
        this.infamies = i;
        this.context = context;
    }



}
