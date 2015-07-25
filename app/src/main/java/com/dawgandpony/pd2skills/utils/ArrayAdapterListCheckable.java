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


    List<String> infamyStrings;
    List<String> infamySubStrings;
    List<Boolean> infamyBools;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_checkable, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvInfamyName);
        TextView textView1 = (TextView) rowView.findViewById(R.id.tvInfamyDesc);
        CheckBox cbInfamy = (CheckBox) rowView.findViewById(R.id.cbInfamy);




        textView.setText(infamyStrings.get(position));
        textView1.setText(infamySubStrings.get(position));
        if (infamyBools.get(position)){
            cbInfamy.setChecked(true);
        }


        return rowView;
    }

    public ArrayAdapterListCheckable(Context context, List<String> infamyStrings, List<String> infamySubStrings, List<Boolean> infamyBools){
        super(context, -1, infamyStrings);
        this.infamyStrings = infamyStrings;
        this.infamySubStrings = infamySubStrings;

        this.infamyBools = infamyBools;
        this.context = context;
    }



}
