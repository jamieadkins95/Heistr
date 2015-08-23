package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
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

        final int pos = position;

        TextView tvTierNumber = (TextView) rowView.findViewById(R.id.tvSkillTierNo);
        tvTierNumber.setText("Tier " + skillTiers.get(pos).getNumber() + ":");

        /*ImageButton[] btns =  new ImageButton[3];
        btns[0] = (ImageButton) rowView.findViewById(R.id.btnSkill1);
        btns[1] = (ImageButton) rowView.findViewById(R.id.btnSkill2);
        btns[2] = (ImageButton) rowView.findViewById(R.id.btnSkill3);*/

        Button[] btns2 =  new Button[3];
        btns2[0] = (Button) rowView.findViewById(R.id.btnSkill1);
        btns2[1] = (Button) rowView.findViewById(R.id.btnSkill2);
        btns2[2] = (Button) rowView.findViewById(R.id.btnSkill3);

        for (int i =0; i < skillTiers.get(pos).getSkillsInTier().size(); i++){

            btns2[i].setText(skillTiers.get(pos).getSkillsInTier().get(i).getName().substring(0,5));

            switch (skillTiers.get(pos).getSkillsInTier().get(i).getTaken()){
                case Skill.NO:
                    //btns2[i].setBackgroundColor(context.getResources().getColor(R.color.black));
                    break;
                case Skill.NORMAL:
                    btns2[i].setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));
                    break;
                case Skill.ACE:
                    btns2[i].setBackgroundColor(context.getResources().getColor(R.color.primary));
                    break;
            }

            final int skill = i;
            btns2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*ImageButton imageButton = (ImageButton) v;
                    imageButton.setBackgroundColor(context.getResources().getColor(R.color.primary));*/
                    Button button = (Button) v;

                    button.getBackground().setColorFilter(context.getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_OVER);
                    Toast.makeText(context, skillTiers.get(pos).getSkillsInTier().get(skill).getName(), Toast.LENGTH_SHORT).show();
                }
            });

            btns2[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    /*ImageButton imageButton = (ImageButton) v;
                    imageButton.setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));*/

                    Button button = (Button) v;
                    button.setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));
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
