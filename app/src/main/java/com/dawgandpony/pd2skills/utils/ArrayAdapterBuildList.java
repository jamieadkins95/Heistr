package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.Consts.Trees;
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
        //CardView cardView = (CardView) rowView.findViewById(R.id.cvBuilds);
        TextView textView = (TextView) rowView.findViewById(R.id.tvName);
        TextView textViewArmour = (TextView) rowView.findViewById(R.id.tvArmour);
        TextView textViewPerkDeck = (TextView) rowView.findViewById(R.id.tvPerkDeck);
        TextView textViewDetection = (TextView) rowView.findViewById(R.id.tvDetection);

        TextView mastermind = (TextView) rowView.findViewById(R.id.masSkillName);
        TextView mastermindSkillCount = (TextView) rowView.findViewById(R.id.masSkillCount);
        TextView enforcer = (TextView) rowView.findViewById(R.id.enfSkillName);
        TextView enforcerSkillCount = (TextView) rowView.findViewById(R.id.enfSkillCount);
        TextView technician = (TextView) rowView.findViewById(R.id.techSkillName);
        TextView technicianSkillCount = (TextView) rowView.findViewById(R.id.techSkillCount);
        TextView ghost = (TextView) rowView.findViewById(R.id.ghoSkillName);
        TextView ghostSkillCount = (TextView) rowView.findViewById(R.id.ghoSkillCount);
        TextView fugitive = (TextView) rowView.findViewById(R.id.fugSkillName);
        TextView fugitiveSkillCount = (TextView) rowView.findViewById(R.id.fugSkillCount);

        //Set build name
        textView.setText(builds.get(position).getName());

        //Set Armour
        String armour = context.getResources().getTextArray(R.array.armourShortened)[builds.get(position).getArmour()].toString();
        textViewArmour.setText(armour);

        //Set Perk Deck
        String perkDeck = context.getResources().getTextArray(R.array.perkDecks)[builds.get(position).getPerkDeck()].toString();
        textViewPerkDeck.setText(perkDeck);

        //Set Detection
        textViewDetection.setText(builds.get(position).getDetection() + "");

        mastermindSkillCount.setText(builds.get(position).getSkillBuild().getSkillTrees().get(Trees.MASTERMIND).getSkillCount() + "");
        enforcerSkillCount.setText(builds.get(position).getSkillBuild().getSkillTrees().get(Trees.ENFORCER).getSkillCount() + "");
        technicianSkillCount.setText(builds.get(position).getSkillBuild().getSkillTrees().get(Trees.TECHNICIAN).getSkillCount() + "");
        ghostSkillCount.setText(builds.get(position).getSkillBuild().getSkillTrees().get(Trees.GHOST).getSkillCount() + "");
        fugitiveSkillCount.setText(builds.get(position).getSkillBuild().getSkillTrees().get(Trees.FUGITIVE).getSkillCount() + "");

        int highlightedColour = context.getResources().getColor(R.color.textSecondary);

        //Set colours if skills are above 15.
        if (builds.get(position).getSkillBuild().getSkillTrees().get(Trees.MASTERMIND).getSkillCount() >= 15){
            mastermind.setTextColor(highlightedColour);
            mastermindSkillCount.setTextColor(highlightedColour);
        }
        if (builds.get(position).getSkillBuild().getSkillTrees().get(Trees.ENFORCER).getSkillCount() >= 15){
            enforcer.setTextColor(highlightedColour);
            enforcerSkillCount.setTextColor(highlightedColour);
        }
        if (builds.get(position).getSkillBuild().getSkillTrees().get(Trees.TECHNICIAN).getSkillCount() >= 15){
            technician.setTextColor(highlightedColour);
            technicianSkillCount.setTextColor(highlightedColour);
        }
        if (builds.get(position).getSkillBuild().getSkillTrees().get(Trees.GHOST).getSkillCount() >= 15){
            ghost.setTextColor(highlightedColour);
            ghostSkillCount.setTextColor(highlightedColour);
        }
        if (builds.get(position).getSkillBuild().getSkillTrees().get(Trees.FUGITIVE).getSkillCount() >= 15){
            fugitive.setTextColor(highlightedColour);
            fugitiveSkillCount.setTextColor(highlightedColour);
        }


        return rowView;
    }

    public ArrayAdapterBuildList(Context context, List<Build> b){
        super(context, -1, b);
        this.builds = b;
        this.context = context;
    }



}
