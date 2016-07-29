package com.jamieadkins.heistr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jamieadkins.heistr.Activities.EditBuildActivity;
import com.jamieadkins.heistr.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArmourFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks {

    ListView lvArmour;

    EditBuildActivity activity;


    public ArmourFragment() {
        // Required empty public constructor
    }

    public static ArmourFragment newInstance() {

        Bundle args = new Bundle();

        ArmourFragment fragment = new ArmourFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (EditBuildActivity) getActivity();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_armour, container, false);
        lvArmour = (ListView) rootView.findViewById(R.id.lvArmour);

        if (activity.getCurrentBuild() == null) {
            activity.listenIn(this);
        } else {
            onBuildReady();
        }

        return rootView;
    }

    @Override
    public void onBuildReady() {

        ArrayList<String> armours = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.armour)));

        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_single_choice, armours);
        lvArmour.setAdapter(mAdapter2);
        lvArmour.setItemChecked(activity.getCurrentBuild().getArmour(), true);
        lvArmour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selected = lvArmour.getCheckedItemPosition();
                activity.getCurrentBuild().updateArmour(activity, selected);

            }
        });

    }

    @Override
    public void onBuildUpdated() {

    }
}
