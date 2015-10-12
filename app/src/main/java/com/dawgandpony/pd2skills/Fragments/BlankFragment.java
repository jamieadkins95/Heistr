package com.dawgandpony.pd2skills.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dawgandpony.pd2skills.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private static final String ARG_TEXT = "textArg";

    String text = "";

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String text){
        BlankFragment fragment = new BlankFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.tvBlank);
        if (getArguments() != null){
            textView.setText(getArguments().getString(ARG_TEXT));
        }

        return rootView;
    }


}
