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
    List<Boolean> infamyBools;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_checkable, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvInfamyName);
        CheckBox cbInfamy = (CheckBox) rowView.findViewById(R.id.cbInfamy);




        textView.setText(infamies.get(position));
        if (infamyBools.get(position)){
            cbInfamy.setChecked(true);
        }


        return rowView;
    }

    public ArrayAdapterListCheckable(Context context, List<String> infamyStrings, List<Boolean> infamyBools){
        super(context, -1, infamyStrings);
        this.infamies = infamyStrings;
        this.infamyBools = infamyBools;
        this.context = context;
    }



}
