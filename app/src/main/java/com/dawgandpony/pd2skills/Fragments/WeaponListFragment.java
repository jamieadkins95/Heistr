package com.dawgandpony.pd2skills.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.Activities.EditWeaponActivity;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Dialogs.NewWeaponDialog;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterWeaponListSmall;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Jamie on 26/09/2015.
 */
public class WeaponListFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks, EditBuildActivity.WeaponsCallbacks, NewWeaponDialog.NewWeaponDialogListener{

    public final static String EXTRA_WEAPON_ID = "com.dawgandpony.pd2skills.WEAPONID";

    ListView lvCurrentWeapon;
    ListView lvOtherWeapons;
    ArrayList<Weapon> weaponList;
    ArrayList<Weapon> baseWeaponInfo;
    int weaponType = 0;

    EditBuildActivity activity;

    public WeaponListFragment() {

    }

    public static WeaponListFragment newInstance(int weaponType) {

        Bundle args = new Bundle();

        WeaponListFragment fragment = new WeaponListFragment();
        fragment.weaponType = weaponType;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weapon_list, container, false);

        activity = (EditBuildActivity) getActivity();
        baseWeaponInfo = new ArrayList<>();

        this.lvCurrentWeapon = (ListView) rootView.findViewById(R.id.lvCurrentWeapon);
        this.lvOtherWeapons = (ListView) rootView.findViewById(R.id.lvOtherWeapons);
        this.lvOtherWeapons.setEmptyView(rootView.findViewById(R.id.emptyElement));
        this.lvOtherWeapons.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabNewBuild);
        fab.attachToListView(lvOtherWeapons);


        this.lvOtherWeapons.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(lvOtherWeapons.getCheckedItemCount() + " selected weapons");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_weapon_list_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        DeleteWeapons();
                        mode.finish();
                        return true;
                    case R.id.action_rename:
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }


            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            private void DeleteWeapons() {
                SparseBooleanArray checked = lvOtherWeapons.getCheckedItemPositions();

                for (int i = 0; i < checked.size(); i++) {
                    if (checked.valueAt(i)) {
                        Build selectedBuild = (Build) lvOtherWeapons.getItemAtPosition(checked.keyAt(i));
                        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(getActivity());
                        dataSourceBuilds.open();
                        dataSourceBuilds.DeleteBuild(selectedBuild.getId());
                        dataSourceBuilds.close();

                        //// TODO: 11/10/2015 refresh weapons


                        Log.d("Context Action", "Delete build " + selectedBuild.getSkillBuild().getId());

                        fab.show();

                    }
                }

                Toast.makeText(getActivity(), "Build(s) deleted", Toast.LENGTH_SHORT).show();
            }
        });

        this.lvOtherWeapons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Weapon selectedWeapon = (Weapon) lvOtherWeapons.getItemAtPosition(position);
                MoveToEditWeaponActivity(selectedWeapon.getId());
            }
        });

        this.lvCurrentWeapon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Weapon selectedWeapon = (Weapon) lvCurrentWeapon.getItemAtPosition(position);
                MoveToEditWeaponActivity(selectedWeapon.getId());
            }
        });


        //region FAB onClick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weaponList != null){
                    NewWeaponDialog dialog = NewWeaponDialog.newInstance(weaponList, baseWeaponInfo);
                    dialog.setTargetFragment(WeaponListFragment.this, BuildListFragment.NEW_DIALOG_FRAGMENT);
                    dialog.show(getActivity().getSupportFragmentManager(), "NewWeaponDialogFragment");
                }

            }
        });
        //endregion
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EditBuildActivity.WEAPON_EDIT_REQUEST) {
            // Make sure the request was successful
            //int type = data.getIntExtra(EditWeaponActivity.EXTRA_WEAPON_TYPE, WeaponBuild.PRIMARY);
            //int id = mNavigationView.getMenu().getItem(8 + type).getItemId();
            //mNavigationView.getMenu().performIdentifierAction(id, 0);
            if (resultCode == Activity.RESULT_OK) {
                // Equip Weapon
                Toast.makeText(getActivity(), "Changes were saved and weapon was equipped", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED){
                // Don't equip weapon
                Toast.makeText(getActivity(), "Changes were saved and weapon was not equipped", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /*for (int i = 0; i < lvCurrentWeapon.getAdapter().getCount(); i++){
            lvCurrentWeapon.setItemChecked(i, false);
        }
        for (int i = 0; i < lvOtherWeapons.getAdapter().getCount(); i++){
            lvOtherWeapons.setItemChecked(i, false);
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity.stopListening(this);
        activity.stopListeningWeapon();
    }

    private void MoveToEditWeaponActivity(long id){
        Intent intent = new Intent(getActivity(), EditWeaponActivity.class);
        intent.putExtra(EXTRA_WEAPON_ID, id);
        startActivityForResult(intent, EditBuildActivity.WEAPON_EDIT_REQUEST);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity.getCurrentBuild() == null){
            activity.listenIn(this);
        } else {
            onBuildReady();
        }
        if(activity.getAllWeapons() == null){
            activity.listenInWeapon(this);
        } else {
            onWeaponsReady();
        }

    }

    @Override
    public void onBuildReady() {
        for (Weapon w : activity.getCurrentBuild().getWeaponsFromXML()){
            if (w.getWeaponType() == weaponType){
                baseWeaponInfo.add(w);
            }
        }
    }

    @Override
    public void onBuildUpdated() {

    }

    @Override
    public void onDialogNewWeapon(DialogFragment dialog, String name, long baseWeaponID, int templateWeaponPos) {
        Toast.makeText(getActivity(), "WIP!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onWeaponsReady() {
        weaponList = activity.getAllWeapons()[weaponType];
        ArrayList<Weapon> allWeaponsButEquipped = new ArrayList<>();
        for (Weapon w : weaponList){
            if (w.getId() != activity.getCurrentBuild().getWeaponBuild().getWeapons()[weaponType].getId()){
                allWeaponsButEquipped.add(w);
            }
        }

        ArrayAdapterWeaponListSmall itemsAdapter =
                new ArrayAdapterWeaponListSmall(getActivity(), allWeaponsButEquipped);

        lvOtherWeapons.setAdapter(itemsAdapter);

        ArrayList<Weapon> currentWeapon = new ArrayList<>();
        for (Weapon w : weaponList){
            if (w.getId() == activity.getCurrentBuild().getWeaponBuild().getWeapons()[weaponType].getId()){
                currentWeapon.add(w);
            }
        }

        ArrayAdapterWeaponListSmall currentAdapter =
                new ArrayAdapterWeaponListSmall(getActivity(), currentWeapon);

        lvCurrentWeapon.setAdapter(currentAdapter);
    }
}
