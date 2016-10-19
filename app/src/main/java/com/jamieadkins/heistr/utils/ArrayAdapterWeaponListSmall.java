package com.jamieadkins.heistr.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jamieadkins.heistr.BuildObjects.SkillStatChangeManager;
import com.jamieadkins.heistr.BuildObjects.Weapon;
import com.jamieadkins.heistr.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterWeaponListSmall extends ArrayAdapter<Weapon> {

    static final int BASE = 0;
    static final int SKILL = 1;
    static final int MOD = 2;
    static final int TOTAL = 3;

    private final long equipped;
    List<Weapon> weapons;
    Context context;
    SkillStatChangeManager skillStatChangeManager;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view_weapon_small, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvWeaponName = (TextView) rowView.findViewById(R.id.tvPrimaryWeaponName);
        TextView tvEquipped = (TextView) rowView.findViewById(R.id.tvEquipped);

        if (getItem(position).getId() == equipped) {
            tvName.setTextColor(ContextCompat.getColor(context, R.color.primaryAccent));
        }

        //region Damage
        TextView tvDamageTotal = (TextView) rowView.findViewById(R.id.tvDamageValueTotal);
        float[] damage = new float[4];
        damage[BASE] = getItem(position).getBaseDamage();
        damage[TOTAL] = getItem(position).getDamage(skillStatChangeManager);
        tvDamageTotal.setText(damage[TOTAL] + "");
        //endregion
        //region Acc
        TextView tvAccuracyTotal = (TextView) rowView.findViewById(R.id.tvAccuracyValueTotal);
        float[] accuracy = new float[4];
        accuracy[BASE] = getItem(position).getBaseAccuracy();
        accuracy[TOTAL] = getItem(position).getAccuracy(skillStatChangeManager);
        tvAccuracyTotal.setText(accuracy[TOTAL] + "");
        //endregion
        //region Stability
        TextView tvStabilityTotal = (TextView) rowView.findViewById(R.id.tvStabilityValueTotal);
        float[] stability = new float[4];
        stability[BASE] = getItem(position).getBaseStability();
        stability[TOTAL] = getItem(position).getStability(skillStatChangeManager);
        tvStabilityTotal.setText(stability[TOTAL] + "");
        //endregion
        //region Conceal
        TextView tvConcealmentTotal = (TextView) rowView.findViewById(R.id.tvConcealmentValueTotal);
        int[] concealment = new int[4];
        concealment[BASE] = getItem(position).getBaseConcealment();
        concealment[TOTAL] = getItem(position).getConcealment(skillStatChangeManager);
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
        ammo[BASE] = getItem(position).getBaseAmmo();
        ammo[TOTAL] = getItem(position).getTotalAmmo(skillStatChangeManager);
        tvAmmoTotal.setText(ammo[TOTAL] + "");
        //endregion
        //region mag
        TextView tvMagTotal = (TextView) rowView.findViewById(R.id.tvMagValueTotal);
        int[] mag = new int[4];
        mag[BASE] = getItem(position).getBaseMagSize();
        mag[TOTAL] = getItem(position).getMagSize(skillStatChangeManager);
        tvMagTotal.setText(mag[TOTAL] + "");
        //endregion


        //Set build name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = "pref_weapon_nickname_length";
        int maxChars = 100;
        try {
            maxChars = Math.min(Integer.parseInt(preferences.getString(key, "100")), getItem(position).getName().length());
        } catch (NumberFormatException e) {
            Log.e(getClass().getSimpleName(), "Invalid int", e);
        }
        tvName.setText(getItem(position).getName().substring(0, maxChars));

        key = "pref_weapon_name_length";
        maxChars = Math.min(Integer.parseInt(preferences.getString(key, "100")), getItem(position).getWeaponName().length());
        tvWeaponName.setText(getItem(position).getWeaponName().substring(0, maxChars));


        return rowView;
    }

    public ArrayAdapterWeaponListSmall(Context context, List<Weapon> weapons, long equippedID, SkillStatChangeManager manager) {
        super(context, -1, weapons);
        this.weapons = weapons;
        this.context = context;
        this.equipped = equippedID;
        this.skillStatChangeManager = manager;
    }


}
