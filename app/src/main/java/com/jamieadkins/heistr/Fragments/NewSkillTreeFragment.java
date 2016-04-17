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
import com.jamieadkins.heistr.BuildObjects.NewSkillSubTree;
import com.jamieadkins.heistr.BuildObjects.Skill;
import com.jamieadkins.heistr.R;
import com.jamieadkins.heistr.utils.SkillCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewSkillTreeFragment extends Fragment {

    private static final String ARG_TEXT = "textArg";

    EditBuildActivity mActivity;

    public NewSkillTreeFragment() {
        // Required empty public constructor
    }

    public static NewSkillTreeFragment newInstance(String text) {
        NewSkillTreeFragment fragment = new NewSkillTreeFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (EditBuildActivity) getActivity();
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

        final NewSkillSubTree tree = NewSkillSubTree.newNonDBInstance(0);
        for (Skill skill : tree.getSkillsInTier()) {
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


}
