package com.dawgandpony.pd2skills.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterSkillTierList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillTreeFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks{
    // the fragment initialization parameters
    private static final String ARG_TREE = "tree";

    EditBuildActivity activity;
    private int skillTreeNum;
    SkillTree currentSkillTree;

    ListView listView;



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
        if (activity.getCurrentBuild() == null){
            activity.listenIn(this);
        }
        else {
            onBuildReady();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_skill_tree, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvSkillTiers);



        return rootView;
    }




    @Override
    public void onBuildReady() {
        currentSkillTree = activity.getCurrentBuild().getSkillBuild().getSkillTrees().get(skillTreeNum);
        ArrayAdapterSkillTierList arrayAdapterSkillTiers = new ArrayAdapterSkillTierList(activity, activity.getCurrentBuild(), currentSkillTree);

        listView.setAdapter(arrayAdapterSkillTiers);
    }
}
