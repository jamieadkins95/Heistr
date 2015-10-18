package com.dawgandpony.pd2skills.BuildObjects;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.dawgandpony.pd2skills.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Jamie on 26/09/2015.
 */
public class Attachment {

    public static final int MOD_BARREL= 0;
    public static final int MOD_BARREL_EXTENSION = 1;
    public static final int MOD_BAYONET = 2;
    public static final int MOD_CUSTOM = 3;
    public static final int MOD_EXTRA = 4;
    public static final int MOD_FOREGRIP = 5;
    public static final int MOD_GADGET = 6;
    public static final int MOD_GRIP = 7;
    public static final int MOD_LOWER_RECEIVER = 8;
    public static final int MOD_MAGAZINE = 9;
    public static final int MOD_SIGHT = 10;
    public static final int MOD_SLIDE = 11;
    public static final int MOD_STOCK = 12;
    public static final int MOD_SUPPRESSOR = 13;
    public static final int MOD_UPPER_RECEIVER = 14;

    long id = -1;
    long pd2skillsID = -1;
    String name = "attach";
    int attachmentGroup = -1;
    private int magsize = 100;
    private float damage = 100;
    private float accuracy = 100;
    private float stability = 100;
    private int concealment = 100;
    private int threat = 100;

    //region XML
    public static ArrayList<Attachment> getAttachmentsFromXML(Resources res){
        ArrayList<Attachment> attachments = new ArrayList<>();
        XmlResourceParser xmlParser = res.getXml(R.xml.attachments);

        try {
            int eventType = xmlParser.getEventType();

            Attachment currentAttachment = null;
            String currentTag = "";
            String attachmentGroup = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_DOCUMENT) {
                    //Log.d("XML", "Start Document");
                } else if (eventType == XmlPullParser.START_TAG) {

                    //Log.d("XML", "Start tag " + xmlParser.getName());
                    currentTag = xmlParser.getName();

                    switch (currentTag) {
                        case "attachment":
                            currentAttachment = new Attachment();
                            break;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    //Log.d("XML", "End tag " + xmlParser.getName());
                    currentTag = xmlParser.getName();
                    switch (currentTag) {
                        case "attachment":
                            currentAttachment.setAttachmentGroup(attachmentGroupFromString(attachmentGroup));
                            attachments.add(currentAttachment);
                            break;
                    }


                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xmlParser.getText();
                    //Log.d("XML", "Text " + text);
                    //Log.d("Current Tag", currentTag + "");

                    switch (currentTag) {
                        case "group_name":
                            attachmentGroup = text;
                            break;
                        case "pd2skills":
                            currentAttachment.setPd2skillsID(Long.parseLong(text));
                            break;
                        case "name":
                            currentAttachment.setName(text);
                            break;
                        case "magsize":
                            currentAttachment.setMagsize(Integer.parseInt(text));
                        case "damage":
                            currentAttachment.setDamage(Float.parseFloat(text));
                            break;
                        case "accuracy":
                            currentAttachment.setAccuracy(Float.parseFloat(text));
                            break;
                        case "stability":
                            currentAttachment.setStability(Float.parseFloat(text));
                            break;
                        case "concealment":
                            currentAttachment.setConcealment(Integer.parseInt(text));
                            break;
                        case "threat":
                            currentAttachment.setThreat(Integer.parseInt(text));
                            break;
                        default:
                            //Log.d("XML", "currentTag didnt match anything!");
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


        xmlParser.close();
        //Log.d("Result from XML", skillBuildFromXML.toString());
        return attachments;
    }

    public static int attachmentGroupFromString(String xmlString){
        int group = -1;
        switch (xmlString){
            case "Barrel":
                group = MOD_BARREL;
                break;
            case "BarrelExt":
                group = MOD_BARREL_EXTENSION;
                break;
        }

        return group;
    }
    //endregion

    public void setAttachmentGroup(int attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPd2skillsID(long pd2skillsID) {
        this.pd2skillsID = pd2skillsID;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public void setStability(float stability) {
        this.stability = stability;
    }

    public void setConcealment(int concealment) {
        this.concealment = concealment;
    }

    public void setThreat(int threat) {
        this.threat = threat;
    }

    public float getDamage() {
        return damage;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public float getStability() {
        return stability;
    }

    public int getConcealment() {
        return concealment;
    }

    public int getThreat() {
        return threat;
    }

    public long getPd2skillsID() {
        return pd2skillsID;
    }

    public String getName(){
        return name;
    }

    public int getAttachmentType() {
        return attachmentGroup;
    }

    public int getMagsize() {
        return magsize;
    }

    public void setMagsize(int magsize) {
        this.magsize = magsize;
    }
}
