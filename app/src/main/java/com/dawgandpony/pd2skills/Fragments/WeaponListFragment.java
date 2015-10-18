package com.dawgandpony.pd2skills.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.dawgandpony.pd2skills.BuildObjects.Attachment;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.BuildObjects.WeaponBuild;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Database.DataSourceWeapons;
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
    private ArrayList<Weapon> allWeapons;
    ArrayList<Weapon> baseWeaponInfo;
    ArrayList<Attachment> baseAttachmentInfo;
    int weaponType = 0;

    ArrayAdapterWeaponListSmall mOtherAdapter;
    ArrayAdapterWeaponListSmall currentAdapter;

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
        baseAttachmentInfo = new ArrayList<>();

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
                        Toast.makeText(getActivity(), "WIP", Toast.LENGTH_SHORT).show();
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
                        Weapon selected = (Weapon) lvOtherWeapons.getItemAtPosition(checked.keyAt(i));
                        DataSourceWeapons dataSourceBuilds = new DataSourceWeapons(getActivity(), baseWeaponInfo, baseAttachmentInfo);
                        dataSourceBuilds.open();
                        dataSourceBuilds.deleteWeapon(selected.getId());
                        dataSourceBuilds.close();

                        //// TODO: 11/10/2015 refresh weapons


                        Log.d("Context Action", "Delete weapon " + selected.getId());

                        fab.show();

                    }
                }

                new GetWeaponsFromDBTask().execute();
                Toast.makeText(getActivity(), "Weapon(s) deleted", Toast.LENGTH_SHORT).show();
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
                if (allWeapons != null){
                    NewWeaponDialog dialog = NewWeaponDialog.newInstance(allWeapons, baseWeaponInfo);
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
                Bundle bundle = data.getExtras();
                long id = bundle.getLong(EXTRA_WEAPON_ID);
                activity.getCurrentBuild().updateWeaponBuild(getActivity(), weaponType, id);
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
        for (int i = 0; i < lvCurrentWeapon.getAdapter().getCount(); i++){
            lvCurrentWeapon.setItemChecked(i, false);
        }
        for (int i = 0; i < lvOtherWeapons.getAdapter().getCount(); i++){
            lvOtherWeapons.setItemChecked(i, false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity.stopListening(this);
    }

    private void MoveToEditWeaponActivity(long id){
        Intent intent = new Intent(getActivity(), EditWeaponActivity.class);
        intent.putExtra(EXTRA_WEAPON_ID, id);
        intent.putExtra(EditWeaponActivity.EXTRA_WEAPON_TYPE, weaponType);
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
    }

    @Override
    public void onBuildReady() {
        baseWeaponInfo = activity.getCurrentBuild().getWeaponsFromXML();
        baseAttachmentInfo = activity.getCurrentBuild().getAttachmentsFromXML();

        new GetWeaponsFromDBTask().execute();
    }

    @Override
    public void onBuildUpdated() {

    }

    @Override
    public void onDialogNewWeapon(DialogFragment dialog, String name, long baseWeaponID, int templateWeaponPos) {
        new CreateNewWeapon(name, baseWeaponID).execute();
    }


    @Override
    public void onWeaponsReady() {
        ArrayList<Weapon> allWeaponsButEquipped = new ArrayList<>();
        for (Weapon w : allWeapons){
            if (w.getId() != activity.getCurrentBuild().getWeaponBuild().getWeapons()[weaponType].getId()){
                allWeaponsButEquipped.add(w);
            }
        }

        if (mOtherAdapter == null){
            mOtherAdapter = new ArrayAdapterWeaponListSmall(getActivity(), allWeaponsButEquipped);
        } else {
            mOtherAdapter.clear();
            mOtherAdapter.addAll(allWeaponsButEquipped);
            mOtherAdapter.notifyDataSetChanged();
        }


        lvOtherWeapons.setAdapter(mOtherAdapter);

        ArrayList<Weapon> currentWeapon = new ArrayList<>();
        for (Weapon w : allWeapons){
            if (w.getId() == activity.getCurrentBuild().getWeaponBuild().getWeapons()[weaponType].getId()){
                currentWeapon.add(w);
            }
        }

        if (currentAdapter == null){
            currentAdapter = new ArrayAdapterWeaponListSmall(getActivity(), currentWeapon);
        } else {
            currentAdapter.clear();
            currentAdapter.addAll(currentWeapon);
            currentAdapter.notifyDataSetChanged();
        }


        lvCurrentWeapon.setAdapter(currentAdapter);
    }

    public class GetWeaponsFromDBTask extends AsyncTask<Void, Integer, ArrayList<Weapon>> {

        public GetWeaponsFromDBTask() {
            super();
        }

        @Override
        protected ArrayList<Weapon> doInBackground(Void... params) {
            ArrayList<Weapon> weapons = new ArrayList<>();

            //Get list of skill builds from database.
            DataSourceWeapons dataSourceWeapons = new DataSourceWeapons(getActivity(), baseWeaponInfo, baseAttachmentInfo);
            dataSourceWeapons.open();
            weapons = dataSourceWeapons.getAllWeapons(weaponType);
            dataSourceWeapons.close();

            return weapons;
        }

        @Override
        protected void onPostExecute(ArrayList<Weapon> weapons) {
            super.onPostExecute(weapons);
            allWeapons = weapons;
            onWeaponsReady();
        }
    }

    private class CreateNewWeapon extends AsyncTask<Void, Void, Weapon> {

        String name;
        long pd2skillsid;

        public CreateNewWeapon(String name, long pd2skillsid) {
            super();
            this.name = name;
            this.pd2skillsid = pd2skillsid;
        }

        @Override
        protected Weapon doInBackground(Void... params) {
            DataSourceWeapons dataSourceWeapons = new DataSourceWeapons(getActivity(), baseWeaponInfo, baseAttachmentInfo);
            dataSourceWeapons.open();
            Weapon w = dataSourceWeapons.createAndInsertWeapon(name, pd2skillsid, weaponType);
            dataSourceWeapons.close();

            return w;
        }

        @Override
        protected void onPostExecute(Weapon w) {
            super.onPostExecute(w);
            MoveToEditWeaponActivity(w.getId());
        }
    }
}
