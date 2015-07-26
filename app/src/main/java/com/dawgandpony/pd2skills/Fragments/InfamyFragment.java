package com.dawgandpony.pd2skills.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfamyFragment extends Fragment {

    ListView lvInfamies;

    ArrayList<Boolean> infamies;
    EditBuildActivity activity;


    public InfamyFragment() {
        // Required empty public constructor
    }

    public static InfamyFragment newInstance(ArrayList<Boolean> infamies) {

        Bundle args = new Bundle();

        InfamyFragment fragment = new InfamyFragment();
        fragment.infamies = infamies;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (EditBuildActivity) getActivity();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_infamy, container, false);
        lvInfamies = (ListView) rootView.findViewById(R.id.lvInfamy);

        ArrayList<String> infamies = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.infamies)));
        ArrayList<String> infamySubStrings = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.infamyDesc)));


        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_multiple_choice, infamies);



        lvInfamies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Update the DB whenever the infamies change
                SparseBooleanArray checked = lvInfamies.getCheckedItemPositions();
                for (int i = 0; i < checked.size(); i++){
                    activity.updateInfamy(checked.keyAt(i), checked.valueAt(i));

                }

            }
        });

        lvInfamies.setAdapter(mAdapter2);

        //Get infamies from the currentBuild and check the respective check box.
        for (int i = 0; i < activity.getCurrentBuild().getInfamies().size();i++ ){
            lvInfamies.setItemChecked(i, activity.getCurrentBuild().getInfamies().get(i));
        }



        return  rootView;
    }


}
