package com.jamieadkins.heistr.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamieadkins.heistr.BuildObjects.Build;
import com.jamieadkins.heistr.BuildObjects.SkillStatChangeManager;
import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterBuildList extends ArrayAdapter<Build> {


    List<Build> builds;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view_build, parent, false);
        //CardView cardView = (CardView) rowView.findViewById(R.id.cvBuilds);
        TextView tv = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvArmour = (TextView) rowView.findViewById(R.id.tvArmour);
        TextView tvPerkDeck = (TextView) rowView.findViewById(R.id.tvPerkDeck);
        TextView tvDetection = (TextView) rowView.findViewById(R.id.tvDetection);

        TextView tvPrimaryWeapon = (TextView) rowView.findViewById(R.id.tvPrimaryWeaponName);
        TextView tvSecondaryWeapon = (TextView) rowView.findViewById(R.id.tvSecondaryWeaponName);

        TextView tvPrimaryDamage = (TextView) rowView.findViewById(R.id.tvDamageValuePrimary);
        TextView tvPrimaryAccuracy = (TextView) rowView.findViewById(R.id.tvAccuracyValuePrimary);
        TextView tvPrimaryStability = (TextView) rowView.findViewById(R.id.tvStabilityValuePrimary);
        TextView tvPrimaryConcealment = (TextView) rowView.findViewById(R.id.tvConcealmentValuePrimary);

        TextView tvSecondaryDamage = (TextView) rowView.findViewById(R.id.tvDamageValueSecondary);
        TextView tvSecondaryAccuracy = (TextView) rowView.findViewById(R.id.tvAccuracyValueSecondary);
        TextView tvSecondaryStability = (TextView) rowView.findViewById(R.id.tvStabilityValueSecondary);
        TextView tvSecondaryConcealment = (TextView) rowView.findViewById(R.id.tvConcealmentValueSecondary);

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = "pref_build_name_length";
        int maxChars = Math.min(Integer.parseInt(preferences.getString(key, "100")), getItem(position).getName().length());
        tv.setText(getItem(position).getName().substring(0, maxChars));

        //Set Armour
        String armour = context.getResources().getTextArray(R.array.armourShortened)[getItem(position).getArmour()].toString();
        tvArmour.setText(armour);

        //Set Perk Deck
        String perkDeck = context.getResources().getTextArray(R.array.perkDecks)[getItem(position).getPerkDeck()].toString();
        tvPerkDeck.setText(perkDeck);

        SkillStatChangeManager skillStatChangeManager = getItem(position).getStatChangeManager();

        //Set Weapons
        if (getItem(position).getWeaponBuild().getPrimaryWeapon() != null) {
            key = "pref_weapon_name_length";
            maxChars = Math.min(Integer.parseInt(preferences.getString(key, "100")), getItem(position).getWeaponBuild().getPrimaryWeapon().getWeaponName().length());
            tvPrimaryWeapon.setText(getItem(position).getWeaponBuild().getPrimaryWeapon().getWeaponName().substring(0, maxChars));
            tvPrimaryDamage.setText(getItem(position).getWeaponBuild().getPrimaryWeapon().getDamage(skillStatChangeManager) + "");
            tvPrimaryAccuracy.setText(getItem(position).getWeaponBuild().getPrimaryWeapon().getAccuracy(skillStatChangeManager) + "");
            tvPrimaryStability.setText(getItem(position).getWeaponBuild().getPrimaryWeapon().getStability(skillStatChangeManager) + "");
            tvPrimaryConcealment.setText(getItem(position).getWeaponBuild().getPrimaryWeapon().getConcealment() + "");
        } else {
            LinearLayout llPrimary = (LinearLayout) rowView.findViewById(R.id.llPrimary);
            llPrimary.setVisibility(View.GONE);
        }

        if (getItem(position).getWeaponBuild().getSecondaryWeapon() != null) {
            key = "pref_weapon_name_length";
            maxChars = Math.min(Integer.parseInt(preferences.getString(key, "100")), getItem(position).getWeaponBuild().getSecondaryWeapon().getWeaponName().length());
            tvSecondaryWeapon.setText(getItem(position).getWeaponBuild().getSecondaryWeapon().getWeaponName().substring(0, maxChars));
            tvSecondaryDamage.setText(getItem(position).getWeaponBuild().getSecondaryWeapon().getDamage(skillStatChangeManager) + "");
            tvSecondaryAccuracy.setText(getItem(position).getWeaponBuild().getSecondaryWeapon().getAccuracy(skillStatChangeManager) + "");
            tvSecondaryStability.setText(getItem(position).getWeaponBuild().getSecondaryWeapon().getStability(skillStatChangeManager) + "");
            tvSecondaryConcealment.setText(getItem(position).getWeaponBuild().getSecondaryWeapon().getConcealment() + "");
        } else {
            LinearLayout llSecondary = (LinearLayout) rowView.findViewById(R.id.llSecondary);
            llSecondary.setVisibility(View.GONE);
        }

        //Set Detection
        tvDetection.setText(getItem(position).getDetection() + "");

        mastermindSkillCount.setText(getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.MASTERMIND).getSkillCount() + "");
        enforcerSkillCount.setText(getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.ENFORCER).getSkillCount() + "");
        technicianSkillCount.setText(getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.TECHNICIAN).getSkillCount() + "");
        ghostSkillCount.setText(getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.GHOST).getSkillCount() + "");
        fugitiveSkillCount.setText(getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.FUGITIVE).getSkillCount() + "");

        int highlightedColour = context.getResources().getColor(R.color.textSecondary);

        //Set colours if skills are above 15.
        if (getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.MASTERMIND).getSkillCount() >= 15) {
            mastermind.setTextColor(highlightedColour);
            mastermindSkillCount.setTextColor(highlightedColour);
        }
        if (getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.ENFORCER).getSkillCount() >= 15) {
            enforcer.setTextColor(highlightedColour);
            enforcerSkillCount.setTextColor(highlightedColour);
        }
        if (getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.TECHNICIAN).getSkillCount() >= 15) {
            technician.setTextColor(highlightedColour);
            technicianSkillCount.setTextColor(highlightedColour);
        }
        if (getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.GHOST).getSkillCount() >= 15) {
            ghost.setTextColor(highlightedColour);
            ghostSkillCount.setTextColor(highlightedColour);
        }
        if (getItem(position).getSkillBuild().getNewSkillTrees().get(Trees.FUGITIVE).getSkillCount() >= 15) {
            fugitive.setTextColor(highlightedColour);
            fugitiveSkillCount.setTextColor(highlightedColour);
        }


        return rowView;
    }

    public ArrayAdapterBuildList(Context context, List<Build> b) {
        super(context, -1, b);
        this.builds = b;
        this.context = context;
    }


}
