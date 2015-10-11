package com.dawgandpony.pd2skills.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.BuildObjects.WeaponBuild;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Database.DataSourceWeapons;
import com.dawgandpony.pd2skills.Dialogs.PD2SkillsExportDialog;
import com.dawgandpony.pd2skills.Dialogs.RenameBuildDialog;
import com.dawgandpony.pd2skills.Fragments.ArmourFragment;
import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Fragments.BuildListFragment;
import com.dawgandpony.pd2skills.Fragments.InfamyFragment;
import com.dawgandpony.pd2skills.Fragments.PerkDeckFragment;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;
import com.dawgandpony.pd2skills.Fragments.TaskFragment;
import com.dawgandpony.pd2skills.Fragments.WeaponListFragment;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.URLEncoder;

import java.util.ArrayList;
import java.util.List;

public class EditBuildActivity2 extends AppCompatActivity implements TaskFragment.TaskCallbacks, RenameBuildDialog.RenameBuildDialogListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private final static String BUILD_ID = "BuildID";
    private final static String FRAGMENT_INDEX = "FragmentInd";
    private final static String ACTIVITY_START = "StartAct";
    public static final int WEAPON_EDIT_REQUEST = 505;  // The request code
    private static final String TAG = EditBuildActivity2.class.getSimpleName();

    private Intent intent;
    private Build currentBuild;
    private long currentBuildID;
    private TaskFragment mTaskFragment;
    private ArrayList<BuildReadyCallbacks> mListCallbacks;
    private WeaponsCallbacks mWeaponCallbacks = null;
    private ArrayList<Weapon>[] allWeapons;

    private boolean activityStart = true;
    private int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitBuildId(savedInstanceState);

        if (savedInstanceState != null) {
            activityStart = savedInstanceState.getBoolean(ACTIVITY_START);
            currentFragment = savedInstanceState.getInt(FRAGMENT_INDEX);
        }

        setContentView(R.layout.activity_edit_build2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListCallbacks == null){
            mListCallbacks = new ArrayList<>();
        }
        if (activityStart){
            mNavigationView.getMenu().performIdentifierAction(R.id.nav_mastermind, 0);
        }

        setTitle(mNavigationView.getMenu().getItem(currentFragment).getTitle());

        InitRetainedFragment();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(BUILD_ID, currentBuild.getId());
        savedInstanceState.putBoolean(ACTIVITY_START, false);
        savedInstanceState.putInt(FRAGMENT_INDEX, currentFragment);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            finish();
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_build, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_URL:
                DialogFragment dialog = PD2SkillsExportDialog.newInstance(URLEncoder.encodeBuild(this, currentBuild));
                dialog.show(getFragmentManager(), "PD2SkillsExportDialogFragment");
                return true;
            case R.id.action_rename:
                showRenameBuildDialog();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        setTitle(menuItem.getTitle());
                        Fragment fragment;

                        switch (menuItem.getItemId()){
                            case R.id.nav_mastermind:
                                fragment = SkillTreeFragment.newInstance(Trees.MASTERMIND);
                                currentFragment = 0;
                                break;
                            case R.id.nav_enforcer:
                                fragment = SkillTreeFragment.newInstance(Trees.ENFORCER);
                                currentFragment = 1;
                                break;
                            case R.id.nav_tech:
                                fragment = SkillTreeFragment.newInstance(Trees.TECHNICIAN);
                                currentFragment = 2;
                                break;
                            case R.id.nav_ghost:
                                fragment = SkillTreeFragment.newInstance(Trees.GHOST);
                                currentFragment = 3;
                                break;
                            case R.id.nav_fugitive:
                                fragment = SkillTreeFragment.newInstance(Trees.FUGITIVE);
                                currentFragment = 4;
                                break;
                            case R.id.nav_infamy:
                                fragment = InfamyFragment.newInstance();
                                currentFragment = 5;
                                break;
                            case R.id.nav_perk_deck:
                                fragment = PerkDeckFragment.newInstance();
                                currentFragment = 6;
                                break;
                            case R.id.nav_armour:
                                fragment = ArmourFragment.newInstance();
                                currentFragment = 7;
                                break;
                            case R.id.nav_primary:
                                fragment = WeaponListFragment.newInstance(WeaponBuild.PRIMARY);
                                currentFragment = 8;
                                break;
                            case R.id.nav_secondary:
                                fragment = WeaponListFragment.newInstance(WeaponBuild.SECONDARY);
                                currentFragment = 9;
                                break;
                            case R.id.nav_home:
                                fragment = null;
                                currentFragment = 0;
                                Intent intent = new Intent(EditBuildActivity2.this, BuildListActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                fragment = new BlankFragment();
                                currentFragment = 10;
                                break;
                        }

                        mDrawerLayout.closeDrawers();

                        if (fragment != null){
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.replace(R.id.contentFragment, fragment);
                            transaction.commit();
                        }

                        return true;
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == WEAPON_EDIT_REQUEST) {
            // Make sure the request was successful
            int type = intent.getIntExtra(EditWeaponActivity.EXTRA_WEAPON_TYPE, WeaponBuild.PRIMARY);
            int id = mNavigationView.getMenu().getItem(8 + type).getItemId();
            //mNavigationView.getMenu().performIdentifierAction(id, 0);
            if (resultCode == Activity.RESULT_OK) {
                // Equip Weapon
                Toast.makeText(this, "Equipped weapon", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED){
                // Don't equip weapon
                Toast.makeText(this, "Didn't equip weapon", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onDialogRenameBuild(DialogFragment dialog, String name, SparseBooleanArray buildPositions) {
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(this);
        dataSourceBuilds.open();
        dataSourceBuilds.renameBuild(currentBuild.getId(), name);
        dataSourceBuilds.close();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onBuildReady(Build build) {
        currentBuild = build;
        currentBuildID = build.getId();
        if (mListCallbacks == null){
            mListCallbacks = new ArrayList<>();
        }else{
            for (BuildReadyCallbacks b : mListCallbacks){
                b.onBuildReady();
            }
        }

        new GetWeaponsFromDBTask().execute();
    }

    public interface BuildReadyCallbacks{
        void onBuildReady();
        void onBuildUpdated();
    }

    public interface WeaponsCallbacks{
        void onWeaponsReady();
    }

    private void InitRetainedFragment() {
        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If we haven't retained the worker fragment, then create it
        // and set this UIFragment as the TaskFragment's target fragment.
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            mTaskFragment.setCurrentBuildID(currentBuildID);
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }
        //mTaskFragment.start(currentBuildID, newBuildName);
    }

    private void InitBuildId(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            currentBuildID = savedInstanceState.getLong(BUILD_ID);
        }
        else{
            intent = getIntent();
            final String action = intent.getAction();
            if (Intent.ACTION_VIEW.equals(action)) {
                final List<String> segments = intent.getData().getPathSegments();
                Log.d("Intents", intent.getData().toString());
                DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(this);
                dataSourceBuilds.open();
                Build b = dataSourceBuilds.createAndInsertBuild("PD2Skills Build", 0, intent.getData().toString(), -1);
                dataSourceBuilds.close();
                currentBuildID = b.getId();

                showRenameBuildDialog();
            }
            else{
                currentBuildID = intent.getLongExtra(BuildListFragment.EXTRA_BUILD_ID, Build.NEW_BUILD);
            }
        }
    }

    private void showRenameBuildDialog() {
        DialogFragment dialog = RenameBuildDialog.newInstance(true, null);
        dialog.show(getFragmentManager(), "RenameBuildDialogFragment");
    }

    public void listenIn(Fragment f){
        mListCallbacks.add((BuildReadyCallbacks) f);
    }

    public void listenInWeapon(Fragment f){
        mWeaponCallbacks = (WeaponsCallbacks) f;
    }

    public void stopListening(Fragment f){
        mListCallbacks.remove(f);
    }

    public void stopListeningWeapon(){
        mWeaponCallbacks = null;
    }

    public Build getCurrentBuild() {
        return currentBuild;
    }


    public ArrayList<Weapon>[] getAllWeapons() {
        return allWeapons;
    }

    public class GetWeaponsFromDBTask extends AsyncTask<Void, Integer, ArrayList<Weapon>[]> {

        public GetWeaponsFromDBTask() {
            super();
        }

        @Override
        protected ArrayList<Weapon>[] doInBackground(Void... params) {
            ArrayList<Weapon>[] weapons = new ArrayList[3];

            //Get list of skill builds from database.
            DataSourceWeapons dataSourceWeapons = new DataSourceWeapons(EditBuildActivity2.this, currentBuild.getWeaponsFromXML());
            dataSourceWeapons.open();
            weapons[WeaponBuild.PRIMARY] = dataSourceWeapons.getAllWeapons(WeaponBuild.PRIMARY);
            weapons[WeaponBuild.SECONDARY] = dataSourceWeapons.getAllWeapons(WeaponBuild.SECONDARY);
            weapons[WeaponBuild.MELEE] = dataSourceWeapons.getAllWeapons(WeaponBuild.MELEE);
            dataSourceWeapons.close();

            return weapons;
        }

        @Override
        protected void onPostExecute(ArrayList<Weapon>[] weapons) {
            super.onPostExecute(weapons);
            allWeapons = weapons;
            if(mWeaponCallbacks != null){
                mWeaponCallbacks.onWeaponsReady();
            }
        }
    }

}
