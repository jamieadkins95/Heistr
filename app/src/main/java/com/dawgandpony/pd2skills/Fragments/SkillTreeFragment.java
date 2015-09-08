package com.dawgandpony.pd2skills.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterSkillTierList;
import com.dawgandpony.pd2skills.utils.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillTreeFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks, ArrayAdapterSkillTierList.AdapterEvents {
    // the fragment initialization parameters
    private static final String ARG_TREE = "tree";

    EditBuildActivity activity;
    private int skillTreeNum;
    SkillTree currentSkillTree;

    ListView listView;
    CardView cvUnlockTree;
    TextView tvUnlockTree;
    TextView tvPointsRemaining;
    TextView tvSkillInfo;

    Button btnNone;
    Button btnNormal;
    Button btnAce;


    ArrayAdapterSkillTierList mAdapter;
    int currentTier = 1;
    int currentSkill = 0;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param skillTree Which tree we are dealing with, Mastermind etc.
     * @return A new instance of fragment SkillTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillTreeFragment newInstance(int skillTree) {
        SkillTreeFragment fragment = new SkillTreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TREE, skillTree);

        fragment.setArguments(args);
        return fragment;
    }

    public SkillTreeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            skillTreeNum = getArguments().getInt(ARG_TREE);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        activity = (EditBuildActivity) getActivity();
        if (activity.getCurrentBuild() == null) {
            activity.listenIn(this);
        } else {
            onBuildReady();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.stopListening(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_skill_tree, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvSkillTiers);
        cvUnlockTree = (CardView) rootView.findViewById(R.id.cvUnlockSkillTree);
        tvUnlockTree = (TextView) rootView.findViewById(R.id.tvUnlockSkillTree);
        tvPointsRemaining = (TextView) rootView.findViewById(R.id.tvPointsRemaining);
        tvSkillInfo = (TextView) rootView.findViewById(R.id.tvSkillDesc);

        btnNone = (Button) rootView.findViewById(R.id.btnNone);
        btnNormal = (Button) rootView.findViewById(R.id.btnNormal);
        btnAce = (Button) rootView.findViewById(R.id.btnAce);


        cvUnlockTree.setEnabled(false);


        return rootView;
    }


    @Override
    public void onBuildReady() {
        currentSkillTree = activity.getCurrentBuild().getSkillBuild().getSkillTrees().get(skillTreeNum);

        tvSkillInfo.setText(currentSkillTree.getTierList().get(currentTier).getSkillsInTier().get(currentSkill).getDescription());
        tvPointsRemaining.setText(activity.getCurrentBuild().getSkillBuild().getPointsRemaining() + "/120");
        //tvPointsRemaining.setText(URLEncoder.EncodeBuild(activity, activity.getCurrentBuild()));

        mAdapter = new ArrayAdapterSkillTierList(activity, this, activity.getCurrentBuild(), currentSkillTree);

        listView.setAdapter(mAdapter);
        cvUnlockTree.setEnabled(true);

        if (currentSkillTree.getTierList().get(0).getSkillsInTier().get(0).getTaken() > Skill.NO) {
            tvUnlockTree.setTextColor(activity.getResources().getColor(R.color.textPrimary));
            cvUnlockTree.setCardBackgroundColor(activity.getResources().getColor(R.color.primary));

        } else {
            tvUnlockTree.setTextColor(activity.getResources().getColor(R.color.textPrimary));
            cvUnlockTree.setCardBackgroundColor(activity.getResources().getColor(R.color.backgroundCard));
        }

        cvUnlockTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentSkillTree.getTierList().get(0).getSkillsInTier().get(0).getTaken()) {
                    case Skill.NO:
                        //Set to normal
                        tvUnlockTree.setTextColor(activity.getResources().getColor(R.color.textPrimary));
                        cvUnlockTree.setCardBackgroundColor(activity.getResources().getColor(R.color.primary));
                        currentSkillTree.getTierList().get(0).getSkillsInTier().get(0).setTaken(Skill.NORMAL);
                        break;
                    case Skill.NORMAL:
                        //Set to none
                        tvUnlockTree.setTextColor(activity.getResources().getColor(R.color.textPrimary));
                        cvUnlockTree.setCardBackgroundColor(activity.getResources().getColor(R.color.backgroundCard));
                        currentSkillTree.getTierList().get(0).getSkillsInTier().get(0).setTaken(Skill.NO);
                        break;
                }

                //Update currentBuild (updates DB)
                activity.getCurrentBuild().updateSkillTier(activity, skillTreeNum, currentSkillTree.getTierList().get(0));
                mAdapter.updateTiers();
            }
        });

        btnNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSkillToNone();
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSkillToNormal();
            }
        });

        btnAce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSkillToAce();
            }
        });
    }

    private void SetSkillToAce() {
        SkillTier tier = currentSkillTree.getTierList().get(currentTier);

        final int skillCost = tier.getAceCost();
        final int pointsUsed = activity.getCurrentBuild().getSkillBuild().getPointsUsed();

        if (pointsUsed + skillCost <= activity.getCurrentBuild().getSkillBuild().getPointsAvailable()) {
            //Set to Ace
            tier.getSkillsInTier().get(currentSkill).setTaken(Skill.ACE);

        }


        //Update currentBuild (updates DB)
        //currentBuild.updateSkillTier(context, tier.getSkillTree(), tier);

        UpdateSkillDesc();
    }

    private void SetSkillToNormal() {
        SkillTier tier = currentSkillTree.getTierList().get(currentTier);
        int pointsInThisSkill = 0;
        if (tier.getSkillsInTier().get(currentSkill).getTaken() == Skill.ACE) {
            pointsInThisSkill = tier.getAceCost();
        } else if (tier.getSkillsInTier().get(currentSkill).getTaken() == Skill.NORMAL) {
            pointsInThisSkill = tier.getNormalCost();
        }


        final int skillCost = tier.getNormalCost();
        int pointsUsed = activity.getCurrentBuild().getSkillBuild().getPointsUsed();

        if (pointsUsed + skillCost - pointsInThisSkill <= activity.getCurrentBuild().getSkillBuild().getPointsAvailable()) {
            //Set to Normal
            tier.getSkillsInTier().get(currentSkill).setTaken(Skill.NORMAL);

        }


        //Update currentBuild (updates DB)
        //currentBuild.updateSkillTier(context, tier.getSkillTree(), tier);

        UpdateSkillDesc();
    }

    private void SetSkillToNone() {
        SkillTier tier = currentSkillTree.getTierList().get(currentTier);

        //Set to None
        tier.getSkillsInTier().get(currentSkill).setTaken(Skill.NO);

        //Update currentBuild (updates DB)
        //currentBuild.updateSkillTier(context, tier.getSkillTree(), tier);

        UpdateSkillDesc();
    }

    private void UpdateSkillDesc() {
        tvSkillInfo.setText(currentSkillTree.getTierList().get(currentTier).getSkillsInTier().get(currentSkill).getDescription());
        switch (currentSkillTree.getTierList().get(currentTier).getSkillsInTier().get(currentSkill).getTaken()){
            case Skill.NO:
                tvSkillInfo.setTextColor(getResources().getColor(R.color.textPrimary));
                tvSkillInfo.setBackgroundColor(getResources().getColor(R.color.backgroundDark));
                break;
            case Skill.NORMAL:
                tvSkillInfo.setTextColor(getResources().getColor(R.color.primary));
                tvSkillInfo.setBackgroundColor(getResources().getColor(R.color.textPrimary));
                break;
            case Skill.ACE:
                tvSkillInfo.setTextColor(getResources().getColor(R.color.textPrimary));
                tvSkillInfo.setBackgroundColor(getResources().getColor(R.color.primary));
                break;
        }

        mAdapter.updateTiers();

    }

    @Override
    public void onBuildUpdated() {
        tvPointsRemaining.setText(activity.getCurrentBuild().getSkillBuild().getPointsRemaining() + "/120");
    }

    @Override
    public void onSkillSelected(int tierNumber, int skillNumber) {
        currentTier = tierNumber;
        currentSkill = skillNumber;

        UpdateSkillDesc();





        tvPointsRemaining.setText(activity.getCurrentBuild().getSkillBuild().getPointsRemaining() + "/120");
    }
}
