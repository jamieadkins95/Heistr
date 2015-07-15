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
    private static final String ARG_ID = "ID";


    // Variables for passing parameters to fragment
    private int mTree;
    private long mID;


    /**
     * Use this  method to create a new instance of
     * this fragment. If you don't supply an ID it will make a new tree.
     *
     * @param tree Which skill tree we are dealing with.
     * @return A new instance of fragment SkillTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillTreeFragment newInstance(int tree) {
        SkillTreeFragment fragment = new SkillTreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TREE, tree);
        args.putLong(ARG_ID, -1); // TODO: Generate a new tree, store it in the DB and then pass the id from it to here.
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this  method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tree Which skill tree we are dealing with.
     * @param id ID of skill tree in DB, use -1 for a new tree.
     * @return A new instance of fragment SkillTreeFragment.
     */
    public static SkillTreeFragment newInstance(int tree, long id) {
        SkillTreeFragment fragment = new SkillTreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TREE, tree);
        args.putLong(ARG_ID, id);
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
            mID = getArguments().getLong(ARG_ID);

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
