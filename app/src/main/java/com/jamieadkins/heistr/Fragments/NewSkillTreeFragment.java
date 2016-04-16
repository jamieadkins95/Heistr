package com.jamieadkins.heistr.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamieadkins.heistr.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewSkillTreeFragment extends Fragment {

    private static final String ARG_TEXT = "textArg";

    String text = "";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String key = getString(R.string.pref_skill_layout);
        boolean vertical = preferences.getBoolean(key, false);
        int layout = vertical ? R.layout.fragment_new_skill_tree_vertical : R.layout.fragment_new_skill_tree;

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(layout, container, false);
        return rootView;
    }


}
