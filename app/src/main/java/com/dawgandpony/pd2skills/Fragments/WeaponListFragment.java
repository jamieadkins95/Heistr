package com.dawgandpony.pd2skills.Fragments;

import android.app.Fragment;
import android.widget.ListView;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;

import java.util.ArrayList;

/**
 * Created by Jamie on 26/09/2015.
 */
public class WeaponListFragment extends Fragment {

    ListView lvCurrentWeapon;
    ListView lvOtherWeapons;
    ArrayList<Weapon> weaponList;
}
