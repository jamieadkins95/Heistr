package com.dawgandpony.pd2skills.Activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

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
import com.dawgandpony.pd2skills.utils.URLEncoder;

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
                final EditText txtURL = new EditText(this);

                txtURL.setHeight(150);
                txtURL.setWidth(340);
                txtURL.setGravity(Gravity.LEFT);
                txtURL.setText(URLEncoder.encodeBuild(this, currentBuild));

                txtURL.setImeOptions(EditorInfo.IME_ACTION_DONE);

                //txtBuildName.setTextAppearance(getActivity(), R.color.abc_search_url_text_normal);
                new AlertDialog.Builder(this)
                        .setTitle("pd2skills.com URL")
                        .setView(txtURL)
                        .setNegativeButton(R.string.got_it, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })

                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
