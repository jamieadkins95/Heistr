package com.jamieadkins.heistr.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jamieadkins.heistr.BuildObjects.Build;
import com.jamieadkins.heistr.BuildObjects.Skill;
import com.jamieadkins.heistr.BuildObjects.SkillTier;
import com.jamieadkins.heistr.BuildObjects.SkillTree;
import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.Dialogs.SkillDialog;
import com.jamieadkins.heistr.R;

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
    ViewGroup parentView;
    private AdapterEvents mListener;

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_skill_tier, parent, false);

        parentView = parent;

        final SkillTier tier = getItem(position);
        //Log.d("getView", "Adapter position " + position + "");
        //Log.d("getView", "getItem(i) tier number = " + tier.getNumber() + "");
        //Log.d("getView", "index of that tier = " + skillTiers.indexOf(getItem(position)) + "");
        //Log.d("getView", "index of correct tier = " + skillTiers.get(skillTiers.indexOf(getItem(position))).getNumber() + "");

        final int pos = skillTiers.indexOf(getItem(position));

        TextView tvTierNumber = (TextView) rowView.findViewById(R.id.tvSkillTierNo);
        tvTierNumber.setText("Tier " + skillTiers.get(pos).getNumber() + ":");

        TextView tvPointsRequired = (TextView) rowView.findViewById(R.id.tvPointsRequired);

        int pointReq =  tier.getPointRequirement(currentBuild.infamyReductionInTree(tier)) - skillTree.getPointsSpentInThisTree(tier.getNumber());
        if (pointReq < 0 ){
            pointReq = 0;
        }
        tvPointsRequired.setText(pointReq + " more to unlock");


        CardView[] cvs = new CardView[3];

        cvs[0] = (CardView) rowView.findViewById(R.id.cvSkill1);
        cvs[1] = (CardView) rowView.findViewById(R.id.cvSkill2);
        cvs[2] = (CardView) rowView.findViewById(R.id.cvSkill3);

        final TextView[] tvs =  new TextView[3];
        tvs[0] = (TextView) rowView.findViewById(R.id.tvSkill1);
        tvs[1] = (TextView) rowView.findViewById(R.id.tvSkill2);
        tvs[2] = (TextView) rowView.findViewById(R.id.tvSkill3);

        if (skillTree.getPointsSpentInThisTree(tier.getNumber()) < tier.getPointRequirement(currentBuild.infamyReductionInTree(tier))){
            rowView.setBackgroundColor(context.getResources().getColor(R.color.backgroundVeryDark));
            //cvs[0].setEnabled(false);
            //cvs[1].setEnabled(false);
            //cvs[2].setEnabled(false);
            tier.ResetSkills();

        }
        for (int i =0; i < tier.getSkillsInTier().size(); i++){

            if (tier.getSkillsInTier().get(i).getName().length() > 17){
                tvs[i].setText(tier.getSkillsInTier().get(i).getAbbreviation());
            }
            else{
                tvs[i].setText(tier.getSkillsInTier().get(i).getName());
            }


            switch (tier.getSkillsInTier().get(i).getTaken()){
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
                    CardView cv = (CardView) v;

                    int taken = tier.getSkillsInTier().get(skill).getTaken();
                    int skillCost = 0;

                    switch (taken) {
                        case Skill.NO:
                            skillCost = tier.getNormalCost();
                            break;
                        case Skill.NORMAL:
                            skillCost = tier.getAceCost();
                            break;
                    }
                    final int pointsUsed = currentBuild.getSkillBuild().getPointsUsed();

                    if (skillTree.getPointsSpentInThisTree(tier.getNumber()) >= tier.getPointRequirement(currentBuild.infamyReductionInTree(tier))){ //if tier is unlocked
                        if (pointsUsed + skillCost <= currentBuild.getSkillBuild().getPointsAvailable()) { //if there are enough points left


                            switch (taken) {
                                case Skill.NO:
                                    //Set to normal
                                    tier.getSkillsInTier().get(skill).setTaken(Skill.NORMAL);
                                    break;
                                case Skill.NORMAL:
                                    //Set to Ace
                                    tier.getSkillsInTier().get(skill).setTaken(Skill.ACE);
                                    break;
                                case Skill.ACE:
                                    //Set to none
                                    tier.getSkillsInTier().get(skill).setTaken(Skill.NO);
                                    break;
                            }


                        } else {
                            //Set to none
                            tier.getSkillsInTier().get(skill).setTaken(Skill.NO);
                            //Toast.makeText(context, "Not enough skill points remaining!", Toast.LENGTH_SHORT).show();
                        }
                    }


                    //Update currentBuild (updates DB)
                    currentBuild.updateSkillTier(context, tier.getSkillTree(), tier);
                    updateTiers();
                    mListener.onSkillTaken(tier.getNumber(), skill);


                }

            });

            cvs[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SkillDialog dialog = SkillDialog.newInstance(tier.getSkillsInTier().get(skill));
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

    public ArrayAdapterSkillTierList(Context context, Fragment listener, Build currentBuild, SkillTree skillTree){
        super(context, -1, skillTree.getTierListInDescendingOrder());
        this.currentBuild = currentBuild;
        this.skillTiers = skillTree.getTierListInDescendingOrder();
        this.skillTree = skillTree;
        this.context = context;
        this.mListener= (AdapterEvents) listener;
    }

    public void updateTiers(){

        for (int i = 0; i < skillTree.getTierList().size(); i++){
            if (skillTree.getPointsSpentInThisTree(
                    skillTree.getTierList().get(i).getNumber()) <
                    skillTree.getTierList().get(i).getPointRequirement(currentBuild.infamyReductionInTree(skillTree.getTierList().get(i)))){

                skillTree.getTierList().get(i).ResetSkills();
                currentBuild.updateSkillTier(context, skillTree.getTierList().get(i).getSkillTree(), skillTree.getTierList().get(i));
            }

        }

        notifyDataSetChanged();
    }


    public interface AdapterEvents{
        void onSkillTaken(int tierNumber, int skillNumber);
    }


}
