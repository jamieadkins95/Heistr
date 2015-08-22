package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

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



        ImageButton[] btns =  new ImageButton[3];
        btns[0] = (ImageButton) rowView.findViewById(R.id.btnSkill1);
        btns[1] = (ImageButton) rowView.findViewById(R.id.btnSkill2);
        btns[2] = (ImageButton) rowView.findViewById(R.id.btnSkill3);

        for (int i =0; i < skillTiers.get(tier).getSkillsInTier().size(); i++){

            switch (skillTiers.get(tier).getSkillsInTier().get(i).getTaken()){
                case Skill.NO:
                    btns[i].setBackgroundColor(context.getResources().getColor(R.color.black));
                    break;
                case Skill.NORMAL:
                    btns[i].setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));
                    break;
                case Skill.ACE:
                    btns[i].setBackgroundColor(context.getResources().getColor(R.color.primary));
                    break;
            }

            final int skill = i;
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton imageButton = (ImageButton) v;
                    imageButton.setBackgroundColor(context.getResources().getColor(R.color.primary));
                    Toast.makeText(context, skill + "", Toast.LENGTH_SHORT).show();
                }
            });

            btns[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ImageButton imageButton = (ImageButton) v;
                    imageButton.setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));
                    return true;
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
