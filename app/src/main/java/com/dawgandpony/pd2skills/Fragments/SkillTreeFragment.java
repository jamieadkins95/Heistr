package com.dawgandpony.pd2skills.Fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dawgandpony.pd2skills.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillTreeFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_TREE = "Tree";


    // Variables for passing parameters to fragment
    private int mTree;



    /**
     * Use this  method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tree Which skill tree we are dealing with.

     * @return A new instance of fragment SkillTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillTreeFragment newInstance(int tree) {
        SkillTreeFragment fragment = new SkillTreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TREE, tree);
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
            mTree = getArguments().getInt(ARG_TREE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_skill_tree, container, false);

        //Get the name of the skill tree and display it in the header.
        Resources res = getResources();
        String[] treeNames = res.getStringArray(R.array.skill_trees);
        TextView tvTreeName = (TextView) rootView.findViewById(R.id.tvSkillTreeName);
        tvTreeName.setText(treeNames[mTree]);

        return rootView;
    }


}
