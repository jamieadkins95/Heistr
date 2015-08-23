package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
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

        CardView[] cvs = new CardView[3];

        cvs[0] = (CardView) rowView.findViewById(R.id.cvSkill1);
        cvs[1] = (CardView) rowView.findViewById(R.id.cvSkill2);
        cvs[2] = (CardView) rowView.findViewById(R.id.cvSkill3);

        final TextView[] tvs =  new TextView[3];
        tvs[0] = (TextView) rowView.findViewById(R.id.tvSkill1);
        tvs[1] = (TextView) rowView.findViewById(R.id.tvSkill2);
        tvs[2] = (TextView) rowView.findViewById(R.id.tvSkill3);

        for (int i =0; i < skillTiers.get(pos).getSkillsInTier().size(); i++){

            tvs[i].setText(skillTiers.get(pos).getSkillsInTier().get(i).getAbbreviation());

            switch (skillTiers.get(pos).getSkillsInTier().get(i).getTaken()){
                case Skill.NO:
                    //btns2[i].setBackgroundColor(context.getResources().getColor(R.color.black));
                    break;
                case Skill.NORMAL:
                    tvs[i].setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));
                    break;
                case Skill.ACE:
                    tvs[i].setBackgroundColor(context.getResources().getColor(R.color.primary));
                    break;
            }

            final int skill = i;
            cvs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*ImageButton imageButton = (ImageButton) v;
                    imageButton.setBackgroundColor(context.getResources().getColor(R.color.primary));*/
                    CardView cv = (CardView) v;
                    tvs[skill].setTextColor(context.getResources().getColor(R.color.primary));
                    cv.setCardBackgroundColor(context.getResources().getColor(R.color.textPrimary));

                    Toast.makeText(context, skillTiers.get(pos).getSkillsInTier().get(skill).getName(), Toast.LENGTH_SHORT).show();
                }
            });

            cvs[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    /*ImageButton imageButton = (ImageButton) v;
                    imageButton.setBackgroundColor(context.getResources().getColor(R.color.primaryAccent));*/

                    CardView cv = (CardView) v;
                    tvs[skill].setTextColor(context.getResources().getColor(R.color.textPrimary));
                    cv.setCardBackgroundColor(context.getResources().getColor(R.color.primary));
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
