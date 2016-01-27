package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.SkillStatChangeManager;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.R;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterWeaponList extends ArrayAdapter<Weapon>{

    static final int BASE = 0;
    static final int SKILL = 1;
    static final int MOD = 2;
    static final int TOTAL = 3;

    List<Weapon> weapons;
    Context context;
    SkillStatChangeManager skillStatChangeManager;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view_weapon, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvWeaponName = (TextView) rowView.findViewById(R.id.tvPrimaryWeaponName);

        //region Damage
        TextView tvDamage = (TextView) rowView.findViewById(R.id.tvDamageValue);
        TextView tvDamageMod = (TextView) rowView.findViewById(R.id.tvDamageValueMod);
        TextView tvDamageSkill = (TextView) rowView.findViewById(R.id.tvDamageValueSkill);
        TextView tvDamageTotal = (TextView) rowView.findViewById(R.id.tvDamageValueTotal);
        float[] damage = new float[4];
        damage[BASE] = getItem(position).getBaseDamage();
        damage[MOD] = getItem(position).getAttachmentDamage();
        damage[SKILL] = getItem(position).getSkillDamage(skillStatChangeManager);
        damage[TOTAL] = getItem(position).getDamage(skillStatChangeManager);
        tvDamage.setText(damage[BASE] + "");
        tvDamageMod.setText(damage[MOD] + "");
        tvDamageSkill.setText(damage[SKILL] + "");
        tvDamageTotal.setText(damage[TOTAL] + "");
        //endregion
        //region Acc
        TextView tvAccuracy = (TextView) rowView.findViewById(R.id.tvAccuracyValue);
        TextView tvAccuracyMod = (TextView) rowView.findViewById(R.id.tvAccuracyValueMod);
        TextView tvAccuracySkill = (TextView) rowView.findViewById(R.id.tvAccuracyValueSkill);
        TextView tvAccuracyTotal = (TextView) rowView.findViewById(R.id.tvAccuracyValueTotal);
        float[] accuracy = new float[4];
        accuracy[BASE] = getItem(position).getBaseAccuracy();
        accuracy[MOD] = getItem(position).getAttachmentAccuracy();
        accuracy[SKILL] = getItem(position).getSkillAccuracy(skillStatChangeManager);
        accuracy[TOTAL] = getItem(position).getAccuracy(skillStatChangeManager);
        tvAccuracy.setText(accuracy[BASE] + "");
        tvAccuracyMod.setText(accuracy[MOD] + "");
        tvAccuracySkill.setText(accuracy[SKILL] + "");
        tvAccuracyTotal.setText(accuracy[TOTAL] + "");
        //endregion
        //region Stabi
        TextView tvStability = (TextView) rowView.findViewById(R.id.tvStabilityValue);
        TextView tvStabilityTotal = (TextView) rowView.findViewById(R.id.tvStabilityValueTotal);
        float[] stability = new float[4];
        stability[BASE] = getItem(position).getBaseStability();
        stability[TOTAL] = getItem(position).getStability(skillStatChangeManager);
        tvStability.setText(stability[BASE] + "");
        tvStabilityTotal.setText(stability[TOTAL] + "");
        //endregion
        //region Conceal
        TextView tvConcealment = (TextView) rowView.findViewById(R.id.tvConcealmentValue);
        TextView tvConcealmentTotal = (TextView) rowView.findViewById(R.id.tvConcealmentValueTotal);
        int[] concealment = new int[4];
        concealment[BASE] = getItem(position).getBaseConcealment();
        concealment[TOTAL] = getItem(position).getConcealment();
        tvConcealment.setText(concealment[BASE] + "");
        tvConcealmentTotal.setText(concealment[TOTAL] + "");
        //endregion
        //region rof
        TextView tvRof = (TextView) rowView.findViewById(R.id.tvRoFValue);
        TextView tvRofTotal = (TextView) rowView.findViewById(R.id.tvRoFValueTotal);
        int[] rof = new int[4];
        rof[BASE] = getItem(position).getROF();
        rof[TOTAL] = getItem(position).getROF();
        tvRof.setText(rof[BASE] + "");
        tvRofTotal.setText(rof[TOTAL] + "");
        //endregion
        //region ammo
        TextView tvAmmo = (TextView) rowView.findViewById(R.id.tvAmmoValue);
        TextView tvAmmoTotal = (TextView) rowView.findViewById(R.id.tvAmmoValueTotal);
        int[] ammo = new int[4];
        ammo[BASE] = getItem(position).getTotalAmmo();
        ammo[TOTAL] = getItem(position).getTotalAmmo();
        tvAmmo.setText(ammo[BASE] + "");
        tvAmmoTotal.setText(ammo[TOTAL] + "");
        //endregion
        //region mag
        TextView tvMag = (TextView) rowView.findViewById(R.id.tvMagValue);
        TextView tvMagMod = (TextView) rowView.findViewById(R.id.tvMagValueMod);
        TextView tvMagSkill = (TextView) rowView.findViewById(R.id.tvMagValueSkill);
        TextView tvMagTotal = (TextView) rowView.findViewById(R.id.tvMagValueTotal);
        int[] mag = new int[4];
        mag[BASE] = getItem(position).getBaseMagSize();
        mag[MOD] = getItem(position).getAttachmentMagSize();
        mag[SKILL] = getItem(position).getSkillMagSize(skillStatChangeManager);
        mag[TOTAL] = getItem(position).getMagSize(skillStatChangeManager);
        tvMag.setText(mag[BASE] + "");
        tvMagMod.setText(mag[MOD] + "");
        tvMagSkill.setText(mag[SKILL] + "");
        tvMagTotal.setText(mag[TOTAL] + "");
        //endregion


        //Set build name
        tvName.setText(getItem(position).getName());
        tvWeaponName.setText(getItem(position).getWeaponName());


        return rowView;
    }

    public ArrayAdapterWeaponList(Context context, List<Weapon> weapons, SkillStatChangeManager manager){
        super(context, -1, weapons);
        this.weapons = weapons;
        this.context = context;
        this.skillStatChangeManager = manager;
    }



}
