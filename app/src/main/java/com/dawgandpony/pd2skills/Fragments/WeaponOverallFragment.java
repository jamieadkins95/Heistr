package com.dawgandpony.pd2skills.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.Activities.EditWeaponActivity;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterWeaponList;

import java.util.ArrayList;

/**
 * Created by Jamie on 20/01/2016.
 */
public class WeaponOverallFragment extends Fragment implements EditWeaponActivity.WeaponsCallbacks, EditBuildActivity.BuildReadyCallbacks, EditWeaponActivity.ViewPagerLifecycle {

    ListView currentWeaponView;
    EditWeaponActivity activity;
    ArrayAdapterWeaponList mAdapter;

    boolean weaponReady = false;
    boolean buildReady = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weapon_overall, container, false);
        activity = (EditWeaponActivity) getActivity();

        this.currentWeaponView = (ListView) rootView.findViewById(R.id.lvWeapon);
        this.currentWeaponView.setEmptyView(rootView.findViewById(R.id.emptyElement));
        this.currentWeaponView.setClickable(false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        onShow();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity.stopListening(this);
    }

    @Override
    public void onBuildReady() {
        buildReady = true;

        setAdapter();
    }

    @Override
    public void onBuildUpdated() {

    }

    private void setAdapter() {
        ArrayList<Weapon> currentWeapon = new ArrayList<>();
        currentWeapon.add(activity.getCurrentWeapon());

        mAdapter = new ArrayAdapterWeaponList(getActivity(), currentWeapon, activity.getCurrentBuild().getStatChangeManager());
        currentWeaponView.setAdapter(mAdapter);
    }

    @Override
    public void onWeaponReady() {
        weaponReady = true;
    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {
        if (activity.getCurrentBuild() != null){
            onBuildReady();
        } else {
            activity.listen(this);
        }
    }
}
