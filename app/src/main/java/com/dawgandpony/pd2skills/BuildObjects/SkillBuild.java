package com.dawgandpony.pd2skills.BuildObjects;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceSkills;
import com.dawgandpony.pd2skills.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class SkillBuild {
    long id = -1;

    ArrayList<SkillTree> skillTrees;
    int pointsUsed;
    int pointsAvailable;



    @Override
    public String toString() {
        String text = "";
        for (SkillTree st : skillTrees){
            text += "\n" + st.toString();
        }
        return text;
    }

    public ArrayList<SkillTree> getSkillTrees() {
        return skillTrees;
    }

    public SkillBuild(){
        skillTrees = new ArrayList<SkillTree>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public static SkillBuild mergeBuilds(SkillBuild skillBuildFromXML, SkillBuild skillBuildFromDB) {

        SkillBuild mergedSkillBuild = new SkillBuild();

        for (int tree = Trees.MASTERMIND; tree <= Trees.FUGITIVE; tree++){
            SkillTree newTree = new SkillTree();
            SkillTree treeFromXML = skillBuildFromXML.getSkillTrees().get(tree);
            SkillTree treeFromDB = skillBuildFromDB.getSkillTrees().get(tree);

            newTree.setSkillBuildID(treeFromDB.getSkillBuildID());
            newTree.setName(treeFromXML.getName());

            for (int tier = Trees.TIER0; tier <= Trees.TIER6; tier++){
                SkillTier newSkillTier = new SkillTier();
                SkillTier tierFromXML = treeFromXML.getTierList().get(tier);
                SkillTier tierFromDB = treeFromDB.getTierList().get(tier);

                newSkillTier.setId(tierFromDB.getId());
                newSkillTier.setSkillBuildID(tierFromDB.getSkillBuildID());
                newSkillTier.setPointRequirement(tierFromXML.getPointRequirement());
                newSkillTier.setNumber(tierFromXML.getNumber());


                for (int skill = 0; skill < tierFromXML.getSkillsInTier().size(); skill++){
                    Skill newSkill = new Skill();
                    Skill skillFromXML = tierFromXML.getSkillsInTier().get(skill);
                    Skill skillFromDB = tierFromDB.getSkillsInTier().get(skill);

                    newSkill.setName(skillFromXML.getName());

                    newSkill.setNormalDescription(skillFromXML.getNormalDescription());
                    newSkill.setAceDescription(skillFromXML.getAceDescription());
                    newSkill.setNormalPoints(skillFromXML.getNormalPoints());
                    newSkill.setAcePoints(skillFromXML.getAcePoints());

                    newSkill.setTaken(skillFromDB.getTaken());

                    newSkillTier.getSkillsInTier().add(newSkill);
                }

                newTree.getTierList().add(newSkillTier);
            }

            mergedSkillBuild.getSkillTrees().add(newTree);
        }

        Log.d("SkillTree Merge", "Successful merge!");
        return mergedSkillBuild;

    }

    public static SkillBuild getSkillBuildFromDB(long id, Context context) {

        SkillBuild skillBuildFromDB = null;
        DataSourceSkills dataSourceSkills = new DataSourceSkills(context);
        dataSourceSkills.open();

        try {

            skillBuildFromDB = dataSourceSkills.getSkillBuild(id);

        }
        catch(Exception e){
            Log.e("Error", e.toString());
        }
        finally {
            dataSourceSkills.close();
            return skillBuildFromDB;
        }
    }


    public static SkillBuild getSkillBuildFromXML(Resources res) {
        SkillBuild skillBuildFromXML = new SkillBuild();
        XmlResourceParser xmlParser = null;

        //Go get the xml for all the trees
        for (int i = Trees.MASTERMIND; i <= Trees.FUGITIVE; i++) {
            //Get the xml for the correct tree
            switch (i) {
                case Trees.MASTERMIND:
                    xmlParser = res.getXml(R.xml.mastermind);
                    break;
                case Trees.ENFORCER:
                    xmlParser = res.getXml(R.xml.enforcer);
                    break;
                case Trees.TECHNICIAN:
                    xmlParser = res.getXml(R.xml.technician);
                    break;
                case Trees.GHOST:
                    xmlParser = res.getXml(R.xml.ghost);
                    break;
                case Trees.FUGITIVE:
                    xmlParser = res.getXml(R.xml.fugitive);
                    break;
                default:
                    Log.e("Error", "Something went wrong while we were retrieving the skills, defaulting to Mastermind");
                    break;
            }


            SkillTree currentSkillTree = null;

            try {
                int eventType = xmlParser.getEventType();

                SkillTier currentSkillTier = null;
                Skill currentSkill = null;
                String currentTag = "";

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        //Log.d("XML", "Start Document");
                    } else if (eventType == XmlPullParser.START_TAG) {

                        //Log.d("XML", "Start tag " + xmlParser.getName());
                        currentTag = xmlParser.getName();

                        switch (xmlParser.getName()){
                            case "skill_tree":
                                currentSkillTree = new SkillTree();
                                break;
                            case "tier":
                                currentSkillTier = new SkillTier();
                                break;
                            case "skill":
                                currentSkill = new Skill();
                                break;
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {
                        //Log.d("XML", "End tag " + xmlParser.getName());
                        currentTag = xmlParser.getName();
                        switch (xmlParser.getName()){
                            case "skill_tree":
                                skillBuildFromXML.getSkillTrees().add(currentSkillTree);
                                break;
                            case "tier":
                                currentSkillTree.getTierList().add(currentSkillTier);
                                break;
                            case "skill":
                                currentSkillTier.getSkillsInTier().add(currentSkill);
                                break;
                        }


                    } else if (eventType == XmlPullParser.TEXT) {
                        String text = xmlParser.getText();
                        //Log.d("XML", "Text " + text);
                        //Log.d("Current Tag", currentTag + "");
                        switch (currentTag.toString()){
                            case "tree_name":
                                currentSkillTree.setName(text);
                                break;
                            case "tierNumber":
                                currentSkillTier.setNumber(Integer.parseInt(text));
                                break;
                            case "point_requirement":
                                currentSkillTier.setPointRequirement(Integer.parseInt(text));
                                break;
                            case "name":
                                currentSkill.setName(text);
                                break;
                            case "normal":
                                currentSkill.setNormalDescription(text);
                                break;
                            case "ace":
                                currentSkill.setAceDescription(text);
                                break;
                            default:
                                Log.d("XML", "currentTag didnt match anything!");
                                break;


                        }
                    }
                    eventType = xmlParser.next();
                }

               //Log.d("XML", "End Document");
            } catch (Exception e) {
                Log.e("Error", e.toString());
                xmlParser.close();
                //Toast.makeText(getBaseContext(), "Something went wrong while we were retrieving the skills", Toast.LENGTH_SHORT).show();
            }

        }
        xmlParser.close();
        //Log.d("Result from XML", skillBuildFromXML.toString());
        return skillBuildFromXML;
    }
}
