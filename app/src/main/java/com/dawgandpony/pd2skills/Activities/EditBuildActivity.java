package com.dawgandpony.pd2skills.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Fragments.ArmourFragment;
import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Fragments.BuildListFragment;
import com.dawgandpony.pd2skills.Fragments.InfamyFragment;
import com.dawgandpony.pd2skills.Fragments.PerkDeckFragment;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Jamie on 15/07/2015.
 */
public class EditBuildActivity extends MaterialNavigationDrawer {

    private static final String TAG = EditBuildActivity.class.getSimpleName();
    private Build currentBuild;
    private Intent intentFromPreviousActivity;
    DataSourceBuilds dataSourceBuilds;


    @Override
    public void init(Bundle savedInstanceState) {


        InitBuild(GetBuildIdFromIntent());


        setDrawerHeaderImage(R.drawable.payday_2_logo);

        MaterialSection secInfamy = newSection("Infamy", InfamyFragment.newInstance(currentBuild.getInfamies()));

        MaterialSection secMas = newSection("Mastermind", new BlankFragment());
        MaterialSection secEnf = newSection("Enforcer", new BlankFragment());
        MaterialSection secTech = newSection("Technician", new BlankFragment());
        MaterialSection secGhost = newSection("Ghost", new BlankFragment());
        MaterialSection secFugi = newSection("Fugitive", new BlankFragment());

        /*MaterialSection secMas = newSection("Mastermind", SkillTreeFragment.newInstance(Trees.MASTERMIND));
        MaterialSection secEnf = newSection("Enforcer", SkillTreeFragment.newInstance(Trees.ENFORCER));
        MaterialSection secTech = newSection("Technician", SkillTreeFragment.newInstance(Trees.TECHNICIAN));
        MaterialSection secGhost = newSection("Ghost", SkillTreeFragment.newInstance(Trees.GHOST));
        MaterialSection secFugi = newSection("Fugitive", SkillTreeFragment.newInstance(Trees.FUGITIVE));*/

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

    private long GetBuildIdFromIntent() {
        intentFromPreviousActivity = getIntent();
        Long buildID;
        buildID = intentFromPreviousActivity.getLongExtra(BuildListFragment.EXTRA_BUILD_ID, Build.NEW_BUILD);
        return buildID;
    }

    private void InitBuild(long buildID){
        String newBuildName = getIntent().getStringExtra(BuildListFragment.EXTRA_BUILD_NAME);

        dataSourceBuilds = new DataSourceBuilds(this);
        if (buildID == Build.NEW_BUILD){
            dataSourceBuilds.open();
            currentBuild = dataSourceBuilds.createAndInsertBuild(newBuildName);
            dataSourceBuilds.close();
        }
        else {
            dataSourceBuilds.open();
            currentBuild = dataSourceBuilds.getBuild(buildID);
            dataSourceBuilds.close();
        }




        new GetSkillsFromXMLandDBTask().execute(currentBuild.getSkillBuildID());
    }

    private class GetSkillsFromXMLandDBTask extends AsyncTask<Long, Integer, SkillBuild> {



        @Override
        protected SkillBuild doInBackground(Long... ids) {

            SkillBuild skillBuildFromXML = SkillBuild.getSkillBuildFromXML(getResources());
            SkillBuild skillBuildFromDB = SkillBuild.getSkillBuildFromDB(ids[0], getApplication());
            SkillBuild mergedSkillBuild = SkillBuild.mergeBuilds(skillBuildFromXML, skillBuildFromDB);
            return mergedSkillBuild;
        }


        @Override
        protected void onPostExecute(SkillBuild skillBuild) {
            super.onPostExecute(skillBuild);

            setCurrentSkillBuild(skillBuild);
        }


    }

    public void updateInfamy(int infamy, boolean infamyEnabled){
        currentBuild.updateInfamy(this, infamy, infamyEnabled);

    }

    public void updatePerkDeck(int selected){
        currentBuild.updatePerkDeck(this, selected);

    }

    public void updateArmour(int selected){
        currentBuild.updateArmour(this, selected);

    }

    private void setCurrentSkillBuild(SkillBuild build){
        currentBuild.setSkillBuild(build);
    }

    public Build getCurrentBuild() {
        return currentBuild;
    }
}
