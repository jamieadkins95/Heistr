package com.dawgandpony.pd2skills.Activities;

import android.os.Bundle;

import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.Fragments.SkillTreeFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Jamie on 15/07/2015.
 */
public class EditBuildActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {
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









        this.addSubheader("Infamy & Perk Deck");
        addSection(secInfamy);
        addSection(secPD);
        this.addSubheader("Skills");
        addSection(secMas);
        addSection(secEnf);
        addSection(secTech);
        addSection(secGhost);
        addSection(secFugi);
        //this.addSubheader("Perk Deck");
        //addSection(secPD);
        this.addSubheader("Weapons");
        addSection(secPrimary);
        addSection(secSecondaty);
        this.addSubheader("Armour");
        addSection(secArmour);

        addBottomSection(secAbout);
        addBottomSection(secSettings);
    }
}
