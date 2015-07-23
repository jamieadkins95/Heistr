package com.dawgandpony.pd2skills.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dawgandpony.pd2skills.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArmourFragment extends Fragment {


    public ArmourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_armour, container, false);
    }


}
