package com.jamieadkins.heistr.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jamieadkins.heistr.Activities.EditBuildActivity;
import com.jamieadkins.heistr.Activities.EditWeaponActivity;
import com.jamieadkins.heistr.BuildObjects.Weapon;
import com.jamieadkins.heistr.R;
import com.jamieadkins.heistr.utils.ArrayAdapterWeaponList;

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
        activity = (EditWeaponActivity) getActivity();
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

        activity = (EditWeaponActivity) getActivity();
        if (activity.getCurrentWeapon() != null) {
            onWeaponReady();
        } else {
            activity.listen(this);
        }

        onShow();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onBuildReady() {
        buildReady = true;
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

        setAdapter();
    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {
        if (activity.getCurrentBuild() != null) {
            onBuildReady();
        } else {
            activity.listen(this);
        }

        if (activity.getCurrentWeapon() != null) {
            onWeaponReady();
        } else {
            activity.listen(this);
        }
    }
}
