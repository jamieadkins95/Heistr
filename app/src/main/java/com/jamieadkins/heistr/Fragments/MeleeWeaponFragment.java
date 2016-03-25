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
import com.jamieadkins.heistr.BuildObjects.MeleeWeapon;
import com.jamieadkins.heistr.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeleeWeaponFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks {

    ListView lvWeapons;
    EditBuildActivity activity;
    MeleeWeapon currentMeleeWeapon;
    ArrayList<MeleeWeapon> meleeWeapons;


    public MeleeWeaponFragment() {
        // Required empty public constructor
    }

    public static MeleeWeaponFragment newInstance() {
        MeleeWeaponFragment fragment = new MeleeWeaponFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (EditBuildActivity) getActivity();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_armour, container, false);
        lvWeapons = (ListView) rootView.findViewById(R.id.lvArmour);

        if (activity.getCurrentBuild() == null) {
            activity.listenIn(this);
        } else {
            onBuildReady();
        }

        return rootView;
    }

    @Override
    public void onBuildReady() {
        currentMeleeWeapon = activity.getCurrentBuild().getWeaponBuild().getMeleeWeapon();
        meleeWeapons = activity.getCurrentBuild().getMeleeWeaponsFromXML();
        ArrayList<String> meleeWeaponNames = new ArrayList<>();
        for (MeleeWeapon weapon : meleeWeapons) {
            meleeWeaponNames.add(weapon.getWeaponName());
        }

        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_single_choice, meleeWeaponNames);
        lvWeapons.setAdapter(mAdapter2);

        for (int i = 0; i < meleeWeapons.size(); i++) {
            if (currentMeleeWeapon != null && currentMeleeWeapon.getPd2().equals(meleeWeapons.get(i).getPd2())) {
                lvWeapons.setItemChecked(i, true);
            }
        }

        lvWeapons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String oldMelee = (currentMeleeWeapon != null) ? currentMeleeWeapon.getPd2() : "none";
                String name = (String) lvWeapons.getItemAtPosition(position);
                MeleeWeapon newMelee = null;
                for (MeleeWeapon meleeWeapon : meleeWeapons) {
                    if (meleeWeapon.getWeaponName().equals(name)) {
                        newMelee = meleeWeapon;
                    }
                }
                if (!newMelee.getPd2().equals(oldMelee)) {
                    currentMeleeWeapon = newMelee;
                } else {
                    currentMeleeWeapon = null;
                    lvWeapons.setItemChecked(position, false);
                }
                activity.getCurrentBuild().updateWeaponBuild(activity, currentMeleeWeapon);
            }
        });
    }

    @Override
    public void onBuildUpdated() {

    }


}
