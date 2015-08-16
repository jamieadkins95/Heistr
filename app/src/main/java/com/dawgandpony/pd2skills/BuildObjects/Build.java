package com.dawgandpony.pd2skills.BuildObjects;

import android.content.Context;

import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jamie on 14/07/2015.
 */
public class Build {

    public final static long NEW_BUILD = -1;

    long id;
    String name = "";
    long skillBuildID;
    SkillBuild skillBuild;
    ArrayList<Boolean> infamies;
    
    int perkDeck = 0;
    int armour = 0;



    public Build() {
        infamies = new ArrayList<>();
        for (int i = 0; i < 4; i++){infamies.add(false);};
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

    public void setId(long id) {
        this.id = id;
    }

    public long getSkillBuildID() {
        return skillBuildID;
    }

    public void setSkillBuildID(long skillBuildID) {
        this.skillBuildID = skillBuildID;
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

    public void updateArmour(Context context, int selected){
        armour = selected;
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updateArmour(id, selected);
        dataSourceBuilds.close();

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
}
