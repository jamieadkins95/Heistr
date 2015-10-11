package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterWeaponListSmall extends ArrayAdapter<Weapon>{

    static final int BASE = 0;
    static final int SKILL = 1;
    static final int MOD = 2;
    static final int TOTAL = 3;

    List<Weapon> weapons;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view_weapon_small, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvWeaponName = (TextView) rowView.findViewById(R.id.tvPrimaryWeaponName);

        //region Damage
        TextView tvDamageTotal = (TextView) rowView.findViewById(R.id.tvDamageValueTotal);
        float[] damage = new float[4];
        damage[BASE] = getItem(position).getDamage();
        damage[TOTAL] = getItem(position).getDamage();
        tvDamageTotal.setText(damage[TOTAL] + "");
        //endregion
        //region Acc
        TextView tvAccuracyTotal = (TextView) rowView.findViewById(R.id.tvAccuracyValueTotal);
        float[] accuracy = new float[4];
        accuracy[BASE] = getItem(position).getAccuracy();
        accuracy[TOTAL] = getItem(position).getAccuracy();
        tvAccuracyTotal.setText(accuracy[TOTAL] + "");
        //endregion
        //region Stabi
        TextView tvStabilityTotal = (TextView) rowView.findViewById(R.id.tvStabilityValueTotal);
        float[] stability = new float[4];
        stability[BASE] = getItem(position).getStability();
        stability[TOTAL] = getItem(position).getStability();
        tvStabilityTotal.setText(stability[TOTAL] + "");
        //endregion
        //region Conceal
        TextView tvConcealmentTotal = (TextView) rowView.findViewById(R.id.tvConcealmentValueTotal);
        int[] concealment = new int[4];
        concealment[BASE] = getItem(position).getConcealment();
        concealment[TOTAL] = getItem(position).getConcealment();
        tvConcealmentTotal.setText(concealment[TOTAL] + "");
        //endregion
        //region rof
        TextView tvRofTotal = (TextView) rowView.findViewById(R.id.tvRoFValueTotal);
        int[] rof = new int[4];
        rof[BASE] = getItem(position).getROF();
        rof[TOTAL] = getItem(position).getROF();
        tvRofTotal.setText(rof[TOTAL] + "");
        //endregion
        //region ammo
        TextView tvAmmoTotal = (TextView) rowView.findViewById(R.id.tvAmmoValueTotal);
        int[] ammo = new int[4];
        ammo[BASE] = getItem(position).getTotalAmmo();
        ammo[TOTAL] = getItem(position).getTotalAmmo();
        tvAmmoTotal.setText(ammo[TOTAL] + "");
        //endregion
        //region mag
        TextView tvMagTotal = (TextView) rowView.findViewById(R.id.tvMagValueTotal);
        int[] mag = new int[4];
        mag[BASE] = getItem(position).getMagSize();
        mag[TOTAL] = getItem(position).getMagSize();
        tvMagTotal.setText(mag[TOTAL] + "");
        //endregion


        //Set build name
        tvName.setText(getItem(position).getName());
        tvWeaponName.setText(getItem(position).getWeaponName());


        return rowView;
    }

    public ArrayAdapterWeaponListSmall(Context context, List<Weapon> weapons){
        super(context, -1, weapons);
        this.weapons = weapons;
        this.context = context;
    }



}
