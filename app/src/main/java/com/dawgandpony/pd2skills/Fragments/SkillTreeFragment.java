package com.dawgandpony.pd2skills.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity2;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterSkillTierList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillTreeFragment extends Fragment implements EditBuildActivity2.BuildReadyCallbacks, ArrayAdapterSkillTierList.AdapterEvents{
    // the fragment initialization parameters
    private static final String ARG_TREE = "tree";

    EditBuildActivity2 activity;
    private int skillTreeNum;
    SkillTree currentSkillTree;

    ListView listView;
    CardView cvUnlockTree;
    TextView tvUnlockTree;
    TextView tvPointsRemaining;


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
        activity = (EditBuildActivity2) getActivity();
        if (activity.getCurrentBuild() == null){
            activity.listenIn(this);
        }
        else {
            onBuildReady();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

        cvUnlockTree.setEnabled(false);





        return rootView;
    }




    @Override
    public void onBuildReady() {
        currentSkillTree = activity.getCurrentBuild().getSkillBuild().getSkillTrees().get(skillTreeNum);


        tvPointsRemaining.setText(activity.getCurrentBuild().getSkillBuild().getPointsRemaining() + "/120");
        //tvPointsRemaining.setText(URLEncoder.EncodeBuild(activity, activity.getCurrentBuild()));

        final ArrayAdapterSkillTierList arrayAdapterSkillTiers = new ArrayAdapterSkillTierList(activity, this, activity.getCurrentBuild(), currentSkillTree);

        listView.setAdapter(arrayAdapterSkillTiers);
        listView.setSelection(listView.getCount()-1);
        cvUnlockTree.setEnabled(true);

        if (currentSkillTree.getTierList().get(0).getSkillsInTier().get(0).getTaken() > Skill.NO){
            tvUnlockTree.setTextColor(activity.getResources().getColor(R.color.textPrimary));
            cvUnlockTree.setCardBackgroundColor(activity.getResources().getColor(R.color.primary));

        }
        else {
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
                arrayAdapterSkillTiers.updateTiers();
                updatePointsRemaining();
            }
        });
    }

    @Override
    public void onBuildUpdated() {
        updatePointsRemaining();
    }

    @Override
    public void onSkillTaken(int tierNumber, int skillNumber) {
        updatePointsRemaining();
    }

    private void updatePointsRemaining() {
        tvPointsRemaining.setText(activity.getCurrentBuild().getSkillBuild().getPointsRemaining() + "/120");
    }
}
