package com.dawgandpony.pd2skills.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;

import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterListCheckable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfamyFragment extends Fragment {

    ListView lvInfamies;


    public InfamyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_infamy, container, false);
        lvInfamies = (ListView) rootView.findViewById(R.id.lvInfamy);

        ArrayList<String> infamies = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.infamies)));

        ArrayAdapterListCheckable mAdapter = new ArrayAdapterListCheckable(getActivity(), infamies);


        lvInfamies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cbInfamy);
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                }
                else{
                    checkBox.setChecked(true);
                }
            }
        });

        lvInfamies.setAdapter(mAdapter);

        return  rootView;
    }


}
