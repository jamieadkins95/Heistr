package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterWeaponList extends ArrayAdapter<Weapon>{


    List<Weapon> weapons;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view_weapon, parent, false);
        //CardView cardView = (CardView) rowView.findViewById(R.id.cvBuilds);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvWeaponName = (TextView) rowView.findViewById(R.id.tvPrimaryWeaponName);



        TextView tvDamage = (TextView) rowView.findViewById(R.id.tvDamageValue);
        TextView tvAccuracy = (TextView) rowView.findViewById(R.id.tvAccuracyValue);
        TextView tvStability = (TextView) rowView.findViewById(R.id.tvStabilityValue);
        TextView tvConcealment = (TextView) rowView.findViewById(R.id.tvConcealmentValue);
        TextView tvRof = (TextView) rowView.findViewById(R.id.tvRoFValue);
        TextView tvAmmo = (TextView) rowView.findViewById(R.id.tvAmmoValue);
        TextView tvMag = (TextView) rowView.findViewById(R.id.tvMagValue);


        //Set build name
        tvName.setText(getItem(position).getName());
        tvWeaponName.setText(getItem(position).getWeaponName());


        return rowView;
    }

    public ArrayAdapterWeaponList(Context context, List<Weapon> weapons){
        super(context, -1, weapons);
        this.weapons = weapons;
        this.context = context;
    }



}
