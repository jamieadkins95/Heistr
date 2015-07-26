package com.dawgandpony.pd2skills.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.R;



import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerkDeckFragment extends Fragment {

    ListView lvPerkDecks;



    EditBuildActivity activity;


    public PerkDeckFragment() {
        // Required empty public constructor
    }

    public static PerkDeckFragment newInstance(int selectedPerkDeck) {

        Bundle args = new Bundle();

        PerkDeckFragment fragment = new PerkDeckFragment();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (EditBuildActivity) getActivity();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_perk_deck, container, false);
        lvPerkDecks = (ListView) rootView.findViewById(R.id.lvPerkDeck);

        ArrayList<String> perkDecks = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.perkDecks)));

        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_single_choice, perkDecks);



        lvPerkDecks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LV", position + " was clicked");

            }
        });

        lvPerkDecks.setAdapter(mAdapter2);



        return  rootView;
    }


}
