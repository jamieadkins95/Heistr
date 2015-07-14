package com.dawgandpony.pd2skills;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class SkillBuild {

    public String getName() {
        return name;
    }

    public String getPrimaryWeapon() {
        return primaryWeapon;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    String name;
    String primaryWeapon;
    int pointsUsed;

    public SkillBuild(String n, String pw, int points) {

        name= n;
        primaryWeapon = pw;
        pointsUsed = points;
    }



    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    public static List<SkillBuild> exampleData(){
        ArrayList<SkillBuild> example = new ArrayList<>();
        example.add(new SkillBuild("Dodge", "car", 1));
        example.add(new SkillBuild("TechForcer", "ak", 120));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));
        example.add(new SkillBuild("LOLWAT", "shotgun", 50));


        return example;
    }
}
