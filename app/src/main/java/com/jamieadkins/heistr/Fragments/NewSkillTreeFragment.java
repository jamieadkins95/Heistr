package com.jamieadkins.heistr.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamieadkins.heistr.Activities.EditBuildActivity;
import com.jamieadkins.heistr.BuildObjects.Build;
import com.jamieadkins.heistr.BuildObjects.NewSkillSubTree;
import com.jamieadkins.heistr.BuildObjects.NewSkillTree;
import com.jamieadkins.heistr.BuildObjects.Skill;
import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.R;
import com.jamieadkins.heistr.utils.SkillCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewSkillTreeFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks {
    private static final String ARG_TREE = "tree";
    private static final String ARG_SUB_TREE = "subtree";

    EditBuildActivity mActivity;
    Build mCurrentBuild;

    private int mSkillTreeIndex;
    private int mSubTreeIndex;
    NewSkillSubTree mCurrentSubTree;

    TextView mTvPointsRemaining;
    SkillCardView[] mSkillCardViews;

    public NewSkillTreeFragment() {
        // Required empty public constructor
    }

    public static NewSkillTreeFragment newInstance(int tree, int subtree) {
        NewSkillTreeFragment fragment = new NewSkillTreeFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TREE, tree);
        args.putInt(ARG_SUB_TREE, subtree);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (EditBuildActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSkillTreeIndex = getArguments().getInt(ARG_TREE);
            mSubTreeIndex = getArguments().getInt(ARG_SUB_TREE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String key = getString(R.string.pref_skill_layout);
        boolean vertical = preferences.getBoolean(key, false);
        int layout = vertical ? R.layout.fragment_new_skill_tree_vertical : R.layout.fragment_new_skill_tree;

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(layout, container, false);

        mTvPointsRemaining = (TextView) rootView.findViewById(R.id.tvPointsRemaining);
        mSkillCardViews = new SkillCardView[Trees.SKILLS_PER_SUBTREE];

        mSkillCardViews[0] = (SkillCardView) rootView.findViewById(R.id.skill_tier1_skill1);
        mSkillCardViews[1] = (SkillCardView) rootView.findViewById(R.id.skill_tier2_skill1);
        mSkillCardViews[2] = (SkillCardView) rootView.findViewById(R.id.skill_tier2_skill2);
        mSkillCardViews[3] = (SkillCardView) rootView.findViewById(R.id.skill_tier3_skill1);
        mSkillCardViews[4] = (SkillCardView) rootView.findViewById(R.id.skill_tier3_skill2);
        mSkillCardViews[5] = (SkillCardView) rootView.findViewById(R.id.skill_tier4_skill1);

        final NewSkillSubTree tree = NewSkillSubTree.newNonDBInstance(0);
        for (Skill skill : tree.getSkillsInSubTree()) {
            skill.setTaken(Skill.NORMAL);
        }

        SkillCardView skillCardView = (SkillCardView) rootView.findViewById(R.id.skill_tier1_skill1);
        skillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getCurrentBuild().updateSubTree(mActivity, 0, tree);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActivity.getCurrentBuild() == null) {
            mActivity.listenIn(this);
        } else {
            onBuildReady();
        }
    }

    @Override
    public void onBuildReady() {
        if (mActivity == null) {
            return;
        }

        mCurrentBuild = mActivity.getCurrentBuild();

        mCurrentSubTree = mCurrentBuild.getSkillBuild().getNewSkillTrees()
                .get(mSkillTreeIndex).getSubTrees().get(mSubTreeIndex);

        initialiseSkillViews();
        updatePointsRemaining();
    }

    private void initialiseSkillViews() {
        for (int i = 0; i < mSkillCardViews.length; i++) {
            final Skill skill = mCurrentSubTree.getSkillsInSubTree().get(i);
            mSkillCardViews[i].setSkillName(skill.getName());
            mSkillCardViews[i].setNormalDescription(skill.getNormalDescription());
            mSkillCardViews[i].setAceDescription(skill.getAceDescription());
            mSkillCardViews[i].setSkillStatus(skill.getTaken());

            boolean unlocked = mCurrentSubTree.getPointsSpentInThisTree(skill.getTier()) >= skill.getUnlockRequirement();
            mSkillCardViews[i].setSkillStatus(unlocked);

            mSkillCardViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkillCardView skillCardView = (SkillCardView) v;

                    int taken = skill.getTaken();
                    int skillCost = 0;

                    switch (taken) {
                        case Skill.NO:
                            skillCost = skill.getNormalCost();
                            break;
                        case Skill.NORMAL:
                            skillCost = skill.getAceCost();
                            break;
                    }
                    final int pointsUsed = mCurrentBuild.getSkillBuild().getPointsUsed();

                    if (mCurrentSubTree.getPointsSpentInThisTree(skill.getTier()) >= skill.getUnlockRequirement()) { //if tier is unlocked
                        if (pointsUsed + skillCost <= mCurrentBuild.getSkillBuild().getPointsAvailable()) { //if there are enough points left

                            switch (taken) {
                                case Skill.NO:
                                    //Set to normal
                                    skill.setTaken(Skill.NORMAL);
                                    skillCardView.setSkillStatus(Skill.NORMAL);
                                    break;
                                case Skill.NORMAL:
                                    //Set to Ace
                                    skill.setTaken(Skill.ACE);
                                    skillCardView.setSkillStatus(Skill.ACE);
                                    break;
                                case Skill.ACE:
                                    //Set to none
                                    skill.setTaken(Skill.NO);
                                    skillCardView.setSkillStatus(Skill.NO);
                                    break;
                            }
                        } else {
                            //Set to none
                            skill.setTaken(Skill.NO);
                            //Toast.makeText(context, "Not enough skill points remaining!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    mCurrentBuild.updateSubTree(mActivity, mSkillTreeIndex, mCurrentSubTree);
                    updateOtherSkills();
                    updatePointsRemaining();
                }
            });
        }
    }

    public void updateOtherSkills() {
        for (int i = 0; i < mCurrentSubTree.getSkillsInSubTree().size(); i++) {
            Skill skill = mCurrentSubTree.getSkillsInSubTree().get(i);
            if (mCurrentSubTree.getPointsSpentInThisTree(skill.getTier()) < skill.getUnlockRequirement()) {
                skill.setTaken(Skill.NO);
                mSkillCardViews[i].setSkillStatus(skill.getTaken());
                mSkillCardViews[i].setSkillStatus(Skill.LOCKED);
            } else {
                mSkillCardViews[i].setSkillStatus(Skill.UNLOCKED);
            }
        }

        mCurrentBuild.updateSubTree(mActivity, mSkillTreeIndex, mCurrentSubTree);
    }

    @Override
    public void onBuildUpdated() {
        updatePointsRemaining();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void updatePointsRemaining() {
        mTvPointsRemaining.setText(mActivity.getCurrentBuild().getSkillBuild().getNewPointsRemaining() +
                "/" + mActivity.getCurrentBuild().getSkillBuild().getNewPointsAvailable());
    }

}
