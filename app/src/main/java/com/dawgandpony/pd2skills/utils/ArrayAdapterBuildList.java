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
        View rowView = inflater.inflate(R.layout.card_view_build, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvName);
        TextView textViewArmour = (TextView) rowView.findViewById(R.id.tvArmour);
        TextView textViewPerkDeck = (TextView) rowView.findViewById(R.id.tvPerkDeck);

        //Set build name
        textView.setText(builds.get(position).getName());

        //Set Armour
        String armour = context.getResources().getTextArray(R.array.armourShortened)[builds.get(position).getArmour()].toString();
        textViewArmour.setText(armour);

        //Set Perk Deck
        String perkDeck = context.getResources().getTextArray(R.array.perkDecks)[builds.get(position).getPerkDeck()].toString();
        textViewPerkDeck.setText(perkDeck);





        return rowView;
    }

    public ArrayAdapterBuildList(Context context, List<Build> b){
        super(context, -1, b);
        this.builds = b;
        this.context = context;
    }



}
