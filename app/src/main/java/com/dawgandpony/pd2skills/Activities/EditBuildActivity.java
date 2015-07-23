package com.dawgandpony.pd2skills.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Fragments.BuildListFragment;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Jamie on 15/07/2015.
 */
public class EditBuildActivity extends MaterialNavigationDrawer {

    private Build currentBuild;
    private Intent intentFromPreviousActivity;
    DataSourceBuilds dataSourceBuilds;


    @Override
    public void init(Bundle savedInstanceState) {


        InitBuild(GetBuildIdFromIntent());


        setDrawerHeaderImage(R.drawable.payday_2_logo);

        MaterialSection secInfamy = newSection("Infamy", new BlankFragment());

        MaterialSection secMas = newSection("Mastermind", SkillTreeFragment.newInstance(Trees.MASTERMIND));
        MaterialSection secEnf = newSection("Enforcer", SkillTreeFragment.newInstance(Trees.ENFORCER));
        MaterialSection secTech = newSection("Technician", SkillTreeFragment.newInstance(Trees.TECHNICIAN));
        MaterialSection secGhost = newSection("Ghost", SkillTreeFragment.newInstance(Trees.GHOST));
        MaterialSection secFugi = newSection("Fugitive", SkillTreeFragment.newInstance(Trees.FUGITIVE));

        MaterialSection secPD = newSection("Perk Deck", new BlankFragment());

        MaterialSection secPrimary = newSection("Primary Weapon", new BlankFragment());
        MaterialSection secSecondaty = newSection("Secondary Weapon", new BlankFragment());

        MaterialSection secArmour = newSection("Armour", new BlankFragment());

        MaterialSection secAbout = newSection("About", R.drawable.ic_info_black_24dp, new BlankFragment());
        MaterialSection secSettings = newSection("Settings", R.drawable.ic_settings_black_24dp, new BlankFragment());










        this.addSubheader("Skills");
        addSection(secMas);
        addSection(secEnf);
        addSection(secTech);
        addSection(secGhost);
        addSection(secFugi);
        this.addSubheader("Infamy");
        addSection(secInfamy);
        this.addSubheader("Perk Deck");
        addSection(secPD);
        this.addSubheader("Weapons");
        addSection(secPrimary);
        addSection(secSecondaty);
        this.addSubheader("Armour");
        addSection(secArmour);

        addBottomSection(secAbout);
        addBottomSection(secSettings);


    }

    private long GetBuildIdFromIntent() {
        intentFromPreviousActivity = getIntent();
        String buildIDString = intentFromPreviousActivity.getStringExtra(BuildListFragment.EXTRA_BUILD_ID);
        return Long.valueOf(buildIDString).longValue();
    }

    private void InitBuild(long buildID){
        String newBuildName = getIntent().getStringExtra(BuildListFragment.EXTRA_BUILD_NAME);
        dataSourceBuilds = new DataSourceBuilds(this);
        dataSourceBuilds.open();
        currentBuild = dataSourceBuilds.getBuild(buildID, newBuildName);
        dataSourceBuilds.close();

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

    private void setCurrentSkillBuild(SkillBuild build){
        currentBuild.setSkillBuild(build);
    }

}
