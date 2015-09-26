package com.dawgandpony.pd2skills.BuildObjects;

import java.util.ArrayList;

/**
 * Created by Jamie on 20/09/2015.
 */
public class Weapon {

    long id = -1;
    long pd2skillsID = -1;
    String name = "Prim";
    String weaponName = "Prim2";
    int weaponType = WeaponBuild.PRIMARY;

    int rateOfFire = 0;
    int totalAmmo = 0;
    int magSize = 0;
    float damage = 0;
    float accuracy = 0;
    float stability = 0;
    int concealment = 0;
    int threat = 0;
    ArrayList<Long> possibleAttachments;
    ArrayList<Attachment> attachments;

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public int getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPd2skillsID() {
        return pd2skillsID;
    }

    public void setPd2skillsID(long pd2skillsID) {
        this.pd2skillsID = pd2skillsID;
    }

    public int getROF() {
        return rateOfFire;
    }

    public void setROF(int rateOfFire) {
        this.rateOfFire = rateOfFire;
    }

    public int getTotalAmmo() {
        return totalAmmo;
    }

    public void setTotalAmmo(int totalAmmo) {
        this.totalAmmo = totalAmmo;
    }

    public int getMagSize() {
        return magSize;
    }

    public void setMagSize(int magSize) {
        this.magSize = magSize;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getStability() {
        return stability;
    }

    public void setStability(float stability) {
        this.stability = stability;
    }

    public int getConcealment() {
        return concealment;
    }

    public void setConcealment(int concealment) {
        this.concealment = concealment;
    }

    public int getThreat() {
        return threat;
    }

    public void setThreat(int threat) {
        this.threat = threat;
    }

    public ArrayList<Long> getPossibleAttachments() {
        return possibleAttachments;
    }

    public void setPossibleAttachments(ArrayList<Long> possibleAttachments) {
        this.possibleAttachments = possibleAttachments;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

}
