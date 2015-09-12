package com.dawgandpony.pd2skills.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.Dialogs.PerkDeckDialog;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerkDeckFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks{

    ListView lvPerkDecks;



    EditBuildActivity activity;


    public PerkDeckFragment() {
        // Required empty public constructor
    }

    public static PerkDeckFragment newInstance() {

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


        if (activity.getCurrentBuild() == null){
            activity.listenIn(this);
        }
        else {
            onBuildReady();
        }

        return  rootView;
    }

    @Override
    public void onBuildReady() {

        ArrayList<String> perkDecks = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.perkDecks)));

        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_single_choice, perkDecks);
        lvPerkDecks.setAdapter(mAdapter2);
        lvPerkDecks.setItemChecked(activity.getCurrentBuild().getPerkDeck(), true);
        lvPerkDecks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selected = lvPerkDecks.getCheckedItemPosition();
                if (selected == activity.getCurrentBuild().getPerkDeck()){
                    PerkDeckDialog dialog = PerkDeckDialog.newInstance(selected);
                    try {
                        Activity activity = getActivity();
                        dialog.show(activity.getFragmentManager(), "perkdeck");
                    } catch (Exception e) {
                        Toast.makeText(activity, "Cannot show perkdeck details :(", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    activity.getCurrentBuild().updatePerkDeck(activity, selected);
                }

            }
        });

        lvPerkDecks.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int selected = lvPerkDecks.getCheckedItemPosition();
                PerkDeckDialog dialog = PerkDeckDialog.newInstance(selected);
                try {
                    Activity activity = getActivity();
                    dialog.show(activity.getFragmentManager(), "perkdeck");
                } catch (Exception e) {
                    Toast.makeText(activity, "Cannot show perkdeck details :(", Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });

    }

    @Override
    public void onBuildUpdated() {

    }


}
