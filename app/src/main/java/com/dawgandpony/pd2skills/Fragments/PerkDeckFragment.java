package com.dawgandpony.pd2skills.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterListCheckable;
import com.dawgandpony.pd2skills.utils.ArrayAdapterListRadio;

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


        final ArrayAdapterListRadio mAdapter = new ArrayAdapterListRadio(activity, perkDecks, 0);


        lvPerkDecks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RadioButton radioButton = (RadioButton) view.findViewById(R.id.radioListItem);
                if (!radioButton.isChecked()) {
                    for (int i = 0; i < lvPerkDecks.getCount(); i++){
                        if (i != position){
                            View v = lvPerkDecks.getChildAt(i);
                            if (v != null){
                                RadioButton radioButtonToUncheck = (RadioButton)v.findViewById(R.id.radioListItem);
                                radioButtonToUncheck.setEnabled(false);
                                radioButtonToUncheck.setChecked(false);
                            }

                        }

                    }
                    radioButton.setChecked(true);
                    radioButton.setEnabled(true);
                    mAdapter.updateSelectedPerkDeck(position);
                }

            }
        });

        lvPerkDecks.setAdapter(mAdapter);



        return  rootView;
    }


}
