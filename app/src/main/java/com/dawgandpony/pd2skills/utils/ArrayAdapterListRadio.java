package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dawgandpony.pd2skills.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterListRadio extends ArrayAdapter<String>{


    List<String> perkDeckStrings;

    int selectedPerkDeck;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_radio, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvName);
        RadioButton radioButton = (RadioButton) rowView.findViewById(R.id.radioListItem);




        textView.setText(perkDeckStrings.get(position));

        if (position == selectedPerkDeck){
            radioButton.setEnabled(true);
            radioButton.setChecked(true);
        }


        return rowView;
    }

    public ArrayAdapterListRadio(Context context, List<String> perkDeckStrings, int selectedDeck){
        super(context, -1, perkDeckStrings);
        this.perkDeckStrings = perkDeckStrings;
        this.selectedPerkDeck = selectedDeck;
        this.context = context;
    }

    public void updateSelectedPerkDeck(int selectedPerkDeck){
        this.selectedPerkDeck = selectedPerkDeck;
    }



}
