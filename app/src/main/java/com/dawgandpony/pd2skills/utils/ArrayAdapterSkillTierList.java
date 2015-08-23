package com.dawgandpony.pd2skills.utils;

import android.app.Activity;
import android.app.Fragment;
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

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.Dialogs.SkillDialog;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterSkillTierList extends ArrayAdapter<SkillTier>{

    Build currentBuild;
    List<SkillTier> skillTiers;
    SkillTree skillTree;
    Context context;

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
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

        if (skillTree.getPointsSpentInThisTree() < skillTiers.get(pos).getPointRequirement()){
            rowView.setBackgroundColor(context.getResources().getColor(R.color.backgroundVeryDark));
            cvs[0].setEnabled(false);
            cvs[1].setEnabled(false);
            cvs[2].setEnabled(false);

        }
        for (int i =0; i < skillTiers.get(pos).getSkillsInTier().size(); i++){

            tvs[i].setText(skillTiers.get(pos).getSkillsInTier().get(i).getAbbreviation());

            switch (skillTiers.get(pos).getSkillsInTier().get(i).getTaken()){
                case Skill.NO:
                    tvs[i].setTextColor(context.getResources().getColor(R.color.textPrimary));
                    cvs[i].setCardBackgroundColor(context.getResources().getColor(R.color.backgroundCard));
                    break;
                case Skill.NORMAL:
                    tvs[i].setTextColor(context.getResources().getColor(R.color.primary));
                    cvs[i].setCardBackgroundColor(context.getResources().getColor(R.color.textPrimary));
                    break;
                case Skill.ACE:
                    tvs[i].setTextColor(context.getResources().getColor(R.color.textPrimary));
                    cvs[i].setCardBackgroundColor(context.getResources().getColor(R.color.primary));
                    break;
            }

            final int skill = i;
            cvs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousPointsUsed = skillTree.getPointsSpentInThisTree();

                    if (currentBuild.getSkillBuild().getPointsUsed() < currentBuild.getSkillBuild().getPointsAvailable()){
                        CardView cv = (CardView) v;

                        switch (skillTiers.get(pos).getSkillsInTier().get(skill).getTaken()){
                            case Skill.NO:
                                //Set to normal
                                tvs[skill].setTextColor(context.getResources().getColor(R.color.primary));
                                cv.setCardBackgroundColor(context.getResources().getColor(R.color.textPrimary));
                                skillTiers.get(pos).getSkillsInTier().get(skill).setTaken(Skill.NORMAL);
                                break;
                            case Skill.NORMAL:
                                //Set to Ace
                                tvs[skill].setTextColor(context.getResources().getColor(R.color.textPrimary));
                                cv.setCardBackgroundColor(context.getResources().getColor(R.color.primary));
                                skillTiers.get(pos).getSkillsInTier().get(skill).setTaken(Skill.ACE);
                                break;
                            case Skill.ACE:
                                //Set to none
                                tvs[skill].setTextColor(context.getResources().getColor(R.color.textPrimary));
                                cv.setCardBackgroundColor(context.getResources().getColor(R.color.backgroundCard));
                                skillTiers.get(pos).getSkillsInTier().get(skill).setTaken(Skill.NO);
                                break;
                        }

                        //Update currentBuild (updates DB)
                        currentBuild.updateSkillTier(context, skillTiers.get(pos).getSkillTree(), skillTiers.get(pos));

                    }
                    else{
                        Toast.makeText(context, "All skill points used", Toast.LENGTH_SHORT).show();
                    }

                    /*if (pos - 1 > 0) {
                        View viewTierAbove = parent.getChildAt(pos - 1);
                        SkillTier tierAbove = skillTiers.get(pos - 1);
                        if (previousPointsUsed < tierAbove.getPointRequirement() && skillTree.getPointsSpentInThisTree() >= tierAbove.getPointRequirement()) {
                            CardView[] cvsAbove = new CardView[3];

                            cvsAbove[0] = (CardView) viewTierAbove.findViewById(R.id.cvSkill1);
                            cvsAbove[1] = (CardView) viewTierAbove.findViewById(R.id.cvSkill2);
                            cvsAbove[2] = (CardView) viewTierAbove.findViewById(R.id.cvSkill3);

                            viewTierAbove.setBackgroundColor(context.getResources().getColor(R.color.backgroundDark));
                            cvsAbove[0].setEnabled(true);
                            cvsAbove[1].setEnabled(true);
                            cvsAbove[2].setEnabled(true);
                        }
                    }*/

                    for (int i = 0; i < parent.getChildCount(); i++){
                        View tierView = parent.getChildAt(i);
                        SkillTier otherTier = skillTiers.get(i);

                        //Get card views from other views
                        CardView[] cvsOther = new CardView[3];
                        cvsOther[0] = (CardView) tierView.findViewById(R.id.cvSkill1);
                        cvsOther[1] = (CardView) tierView.findViewById(R.id.cvSkill2);
                        cvsOther[2] = (CardView) tierView.findViewById(R.id.cvSkill3);

                        //Get text views from other views
                        final TextView[] tvsOther =  new TextView[3];
                        tvsOther[0] = (TextView) tierView.findViewById(R.id.tvSkill1);
                        tvsOther[1] = (TextView) tierView.findViewById(R.id.tvSkill2);
                        tvsOther[2] = (TextView) tierView.findViewById(R.id.tvSkill3);

                        if (skillTree.getPointsSpentInThisTree() >= otherTier.getPointRequirement()) {
                            tierView.setBackgroundColor(context.getResources().getColor(R.color.backgroundDark));
                            cvsOther[0].setEnabled(true);
                            cvsOther[1].setEnabled(true);
                            cvsOther[2].setEnabled(true);
                        }
                        else{
                            tierView.setBackgroundColor(context.getResources().getColor(R.color.backgroundVeryDark));

                            for (int k =0; k < cvsOther.length; k++){
                                cvsOther[k].setEnabled(false);
                                tvsOther[k].setTextColor(context.getResources().getColor(R.color.textPrimary));
                                cvsOther[k].setCardBackgroundColor(context.getResources().getColor(R.color.backgroundCard));
                            }

                            otherTier.ResetSkills();
                            currentBuild.updateSkillTier(context, skillTiers.get(pos).getSkillTree(), otherTier);
                        }
                    }


                }

            });

            cvs[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SkillDialog dialog = SkillDialog.newInstance(skillTiers.get(pos).getSkillsInTier().get(skill));
                    try {
                        Activity activity = (Activity) context;
                        dialog.show(activity.getFragmentManager(), "skills");
                    } catch (Exception e) {
                        Toast.makeText(context, "Cannot show skill details :(", Toast.LENGTH_SHORT).show();
                    }


                    return true;
                }
            });
        }


        return rowView;
    }

    public ArrayAdapterSkillTierList(Context context, Build currentBuild, SkillTree skillTree){
        super(context, -1, skillTree.getTierListInDescendingOrder());
        this.currentBuild = currentBuild;
        this.skillTiers = skillTree.getTierListInDescendingOrder();
        this.skillTree = skillTree;
        this.context = context;
    }



}
