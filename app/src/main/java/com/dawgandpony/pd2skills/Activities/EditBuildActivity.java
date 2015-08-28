package com.dawgandpony.pd2skills.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Fragments.ArmourFragment;
import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Fragments.BuildListFragment;
import com.dawgandpony.pd2skills.Fragments.InfamyFragment;
import com.dawgandpony.pd2skills.Fragments.PerkDeckFragment;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;
import com.dawgandpony.pd2skills.Fragments.TaskFragment;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Jamie on 15/07/2015.
 */
public class EditBuildActivity extends MaterialNavigationDrawer implements TaskFragment.TaskCallbacks{

    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private final static String BUILD_ID = "BuildID";
    private static final String TAG = EditBuildActivity.class.getSimpleName();

    private Intent intentFromPreviousActivity;

    private Build currentBuild;

    private long currentBuildID;
    private String newBuildName;

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
        addSection(secPrimary);
        addSection(secSecondaty);
        addSection(secMelee);
        this.addDivisor();

        addSection(secArmour);

        addBottomSection(secHome);
        addBottomSection(secAbout);
        addBottomSection(secSettings);



    }

    public void InitBuildRetrieval() {
        if (mTaskFragment.isRunning()) {
            mTaskFragment.cancel();
        } else {
            mTaskFragment.start(currentBuildID, newBuildName);
        }
    }

    private void InitRetainedFragment() {
        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If we haven't retained the worker fragment, then create it
        // and set this UIFragment as the TaskFragment's target fragment.
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            mTaskFragment.setCurrentBuildID(currentBuildID);
            mTaskFragment.setNewBuildName(newBuildName);
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
            intentFromPreviousActivity = getIntent();
            currentBuildID = intentFromPreviousActivity.getLongExtra(BuildListFragment.EXTRA_BUILD_ID, Build.NEW_BUILD);
            newBuildName =  getIntent().getStringExtra(BuildListFragment.EXTRA_BUILD_NAME);
        }



    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(BUILD_ID, currentBuild.getId());

        super.onSaveInstanceState(savedInstanceState);
    }

    public long getCurrentBuildID() {
        return currentBuildID;
    }

    public String getNewBuildName() {
        return newBuildName;
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

    public static interface BuildReadyCallbacks{
        void onBuildReady();
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
