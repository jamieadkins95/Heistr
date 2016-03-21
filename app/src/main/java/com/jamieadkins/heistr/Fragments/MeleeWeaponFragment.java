package com.jamieadkins.heistr.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
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
public class MeleeWeaponFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks {

    ListView lvWeapons;
    EditBuildActivity activity;


    public MeleeWeaponFragment() {
        // Required empty public constructor
    }

    public static InfamyFragment newInstance() {
        InfamyFragment fragment = new InfamyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (EditBuildActivity) getActivity();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_infamy, container, false);
        lvWeapons = (ListView) rootView.findViewById(R.id.lvInfamy);

        if (activity.getCurrentBuild() == null) {
            activity.listenIn(this);
        } else {
            onBuildReady();
        }

        return rootView;
    }

    @Override
    public void onBuildReady() {

        ArrayList<String> infamies = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.infamies)));
        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_multiple_choice, infamies);
        lvWeapons.setAdapter(mAdapter2);

        //Get infamies from the currentBuild and check the respective check box.
        for (int i = 0; i < activity.getCurrentBuild().getInfamies().size(); i++) {
            lvWeapons.setItemChecked(i, activity.getCurrentBuild().getInfamies().get(i));
        }

        lvWeapons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Update the DB whenever the infamies change
                SparseBooleanArray checked = lvWeapons.getCheckedItemPositions();
                for (int i = 0; i < checked.size(); i++) {
                    activity.getCurrentBuild().updateInfamy(activity, checked.keyAt(i), checked.valueAt(i));

                }

            }
        });
    }

    @Override
    public void onBuildUpdated() {

    }


}
