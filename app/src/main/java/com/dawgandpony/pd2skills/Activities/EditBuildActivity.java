package com.dawgandpony.pd2skills.Activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Dialogs.PD2SkillsExportDialog;
import com.dawgandpony.pd2skills.Dialogs.RenameBuildDialog;
import com.dawgandpony.pd2skills.Fragments.ArmourFragment;
import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Fragments.BuildListFragment;
import com.dawgandpony.pd2skills.Fragments.InfamyFragment;
import com.dawgandpony.pd2skills.Fragments.PerkDeckFragment;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;
import com.dawgandpony.pd2skills.Fragments.TaskFragment;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.URLEncoder;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Jamie on 15/07/2015.
 */
public class EditBuildActivity extends MaterialNavigationDrawer implements TaskFragment.TaskCallbacks, RenameBuildDialog.RenameBuildDialogListener{

    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private final static String BUILD_ID = "BuildID";
    private static final String TAG = EditBuildActivity.class.getSimpleName();

    private Intent intent;

    private Build currentBuild;

    private long currentBuildID;

    private TaskFragment mTaskFragment;
    private ArrayList<BuildReadyCallbacks> mListCallbacks;

    @Override
    protected void onResume() {
        super.onResume();
        if (mListCallbacks == null){
            mListCallbacks = new ArrayList<>();
        }
        InitRetainedFragment();


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        InitBuildId(savedInstanceState);

        if (isDrawerOpen()){
            closeDrawer();
        }


    }

    @Override
    public void init(Bundle savedInstanceState) {


        InitBuildId(savedInstanceState);




        setDrawerHeaderImage(R.drawable.payday_2_logo);


        MaterialSection secInfamy = newSection("Infamy", InfamyFragment.newInstance());


        MaterialSection secMas = newSection("Mastermind", SkillTreeFragment.newInstance(Trees.MASTERMIND));
        MaterialSection secEnf = newSection("Enforcer", SkillTreeFragment.newInstance(Trees.ENFORCER));
        MaterialSection secTech = newSection("Technician", SkillTreeFragment.newInstance(Trees.TECHNICIAN));
        MaterialSection secGhost = newSection("Ghost", SkillTreeFragment.newInstance(Trees.GHOST));
        MaterialSection secFugi = newSection("Fugitive", SkillTreeFragment.newInstance(Trees.FUGITIVE));

        MaterialSection secPD = newSection("Perk Deck", PerkDeckFragment.newInstance());

        MaterialSection secPrimary = newSection("Primary Weapon", new BlankFragment());
        MaterialSection secSecondaty = newSection("Secondary Weapon", new BlankFragment());
        MaterialSection secMelee = newSection("Melee Weapon", new BlankFragment());

        MaterialSection secArmour = newSection("Armour", ArmourFragment.newInstance());

        MaterialSection secAbout = newSection("About", R.drawable.ic_info_black_24dp, new BlankFragment());
        MaterialSection secSettings = newSection("Settings", R.drawable.ic_settings_black_24dp, new BlankFragment());
        Intent intent = new Intent(this, BuildListActivity.class);
        MaterialSection secHome = newSection("Home", R.drawable.ic_home_white_24dp, intent);


        addSection(secMas);
        addSection(secEnf);
        addSection(secTech);
        addSection(secGhost);
        addSection(secFugi);
        this.addDivisor();
        addSection(secInfamy);
        this.addDivisor();
        addSection(secPD);
        this.addDivisor();
        addSection(secArmour);
        this.addDivisor();
        addSection(secPrimary);
        addSection(secSecondaty);
        addSection(secMelee);
        //this.addDivisor();


        addBottomSection(secHome);
        addBottomSection(secAbout);
        addBottomSection(secSettings);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_build, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_URL:
                DialogFragment dialog = PD2SkillsExportDialog.newInstance(URLEncoder.encodeBuild(this, currentBuild));
                dialog.show(getFragmentManager(), "PD2SkillsExportDialogFragment");
                return true;

            case R.id.action_rename:
                showRenameBuildDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showRenameBuildDialog() {
        DialogFragment dialog = RenameBuildDialog.newInstance(true, null);
        dialog.show(getFragmentManager(), "RenameBuildDialogFragment");
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


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(BUILD_ID, currentBuild.getId());

        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public void onPreExecute() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(Build build) {
        currentBuild = build;
        currentBuildID = build.getId();
        if (mListCallbacks == null){
            mListCallbacks = new ArrayList<>();
        }else{
            for (BuildReadyCallbacks b : mListCallbacks){
                b.onBuildReady();
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

    public interface BuildReadyCallbacks{
        void onBuildReady();
        void onBuildUpdated();
    }



    public void listenIn(Fragment f){
        mListCallbacks.add((BuildReadyCallbacks) f);
    }

    public void stopListening(Fragment f){
        mListCallbacks.remove(f);
    }



    public Build getCurrentBuild() {
        return currentBuild;
    }
}
