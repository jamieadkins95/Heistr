package com.dawgandpony.pd2skills.BuildObjects;

import android.content.Context;

import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Database.DataSourceSkills;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jamie on 14/07/2015.
 */
public class Build {
    public final static long PD2SKILLS = -2;
    public final static long NEW_BUILD = -1;

    long id;
    String name = "";
    SkillBuild skillBuild;
    WeaponBuild weaponBuild;
    ArrayList<Boolean> infamies;

    ArrayList<Weapon> weaponsFromXML;
    ArrayList<Attachment> attachmentsFromXML;
    
    int perkDeck = 0;
    int armour = 0;



    public Build() {
        infamies = new ArrayList<>();
        for (int i = 0; i < 4; i++){infamies.add(false);};
    }

    public WeaponBuild getWeaponBuild() {
        return weaponBuild;
    }

    public void setWeaponBuild(WeaponBuild weaponBuild) {
        this.weaponBuild = weaponBuild;
    }

    public String getName() {
        return name;
    }

    public SkillBuild getSkillBuild() {
        return skillBuild;
    }

    public void setSkillBuild(SkillBuild skillBuild) {
        this.skillBuild = skillBuild;
    }

    public int getPerkDeck() {
        return perkDeck;
    }

    public void setPerkDeck(int perkDeck) {
        this.perkDeck = perkDeck;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public ArrayList<Weapon> getWeaponsFromXML() {
        return weaponsFromXML;
    }

    public void setWeaponsFromXML(ArrayList<Weapon> weaponsFromXML) {
        this.weaponsFromXML = weaponsFromXML;
    }

    public ArrayList<Attachment> getAttachmentsFromXML() {
        return attachmentsFromXML;
    }

    public void setAttachmentsFromXML(ArrayList<Attachment> attachmentsFromXML) {
        this.attachmentsFromXML = attachmentsFromXML;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void updateInfamy(Context context, int infamy, boolean enabled){
        getInfamies().set(infamy, enabled);
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updateInfamy(id, getInfamyID());
        dataSourceBuilds.close();

    }

    public void updatePerkDeck(Context context, int selected){
        perkDeck = selected;
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updatePerkDeck(id, selected);
        dataSourceBuilds.close();

    }

    public void updateSkillTier(Context context, int skillTree, SkillTier updatedTier){
        int tierNumber = updatedTier.getNumber();
        skillBuild.getSkillTrees().get(skillTree).getTierList().set(tierNumber, updatedTier);

        DataSourceSkills dataSourceSkills = new DataSourceSkills(context);
        dataSourceSkills.open();
        dataSourceSkills.updateSkillTier(id, skillTree, updatedTier);
        dataSourceSkills.close();

    }

    public void updateArmour(Context context, int selected){
        armour = selected;
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updateArmour(id, selected);
        dataSourceBuilds.close();

    }

    public void updateWeaponBuild(Context context, int weaponType, Weapon weapon) {
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updateWeaponBuild(weaponBuild.getId(), weaponType, weapon.getId());
        dataSourceBuilds.close();
        weaponBuild.getWeapons()[weaponType] = weapon;
    }

    public ArrayList<Boolean> getInfamies() {
        return infamies;
    }

    public void setInfamies(ArrayList<Boolean> infamies) {
        this.infamies = infamies;
    }

    /*
    Works out the infamy id from the 4 infamy booleans. Essentially binary encoding.
     */
    public long getInfamyID() {
        int id = 1;
        if (getInfamies().get(Trees.MASTERMIND)){
            id += 8;
        }
        if (getInfamies().get(Trees.ENFORCER)){
            id += 4;
        }
        if (getInfamies().get(Trees.TECHNICIAN)){
            id += 2;
        }
        if (getInfamies().get(Trees.GHOST)){
            id += 1;
        }
        return id;
    }

    public int getDetection(){
        Random r = new Random();
        return r.nextInt(75);
    }

    public boolean infamyReductionInTree(SkillTier tier){
        boolean infamyInThisTree = false;
        if (tier.getSkillTree() < Trees.FUGITIVE){
            infamyInThisTree = getInfamies().get(tier.getSkillTree());
        }
        else{
            if (getInfamyID() > 1){
                infamyInThisTree = true;
            }
        }

        return infamyInThisTree;
    }
}
