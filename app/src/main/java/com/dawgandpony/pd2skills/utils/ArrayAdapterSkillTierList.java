package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterSkillTierList extends ArrayAdapter<SkillTier>{


    List<SkillTier> skillTiers;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_skill_tier, parent, false);

        final int tier = position;

        SkillButton[] btns =  new SkillButton[3];
        btns[0] = (SkillButton) rowView.findViewById(R.id.btnSkill1);
        btns[1] = (SkillButton) rowView.findViewById(R.id.btnSkill2);
        btns[2] = (SkillButton) rowView.findViewById(R.id.btnSkill3);

        for (int i =0; i < skillTiers.get(tier).getSkillsInTier().size(); i++){
            btns[i].setText(skillTiers.get(tier).getSkillsInTier().get(i).getName().substring(0, 3));
            switch (skillTiers.get(tier).getSkillsInTier().get(i).getTaken()){
                case Skill.NO:
                    btns[i].setNormal(false);
                    btns[i].setNormal(false);

                    break;
                case Skill.NORMAL:
                    btns[i].setNormal(true);
                    btns[i].setNormal(false);
                    break;
                case Skill.ACE:
                    btns[i].setNormal(false);
                    btns[i].setNormal(true);
                    break;
            }

            final int skill = i;
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkillButton b = (SkillButton) v;
                    if (!b.isNormal() && !b.isAced()){
                        b.setNormal(true);
                    }
                    else if (b.isNormal() && !b.isAced()){
                        b.setNormal(false);
                        b.setAced(true);
                    }
                    else if (!b.isNormal() && b.isAced()){
                        b.setNormal(false);
                        b.setAced(false);
                    }
                }
            });
        }


        return rowView;
    }

    public ArrayAdapterSkillTierList(Context context, ArrayList<SkillTier> skills){
        super(context, -1, skills);
        this.skillTiers = skills;
        this.context = context;
    }



}
