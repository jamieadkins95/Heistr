package com.jamieadkins.heistr.BuildObjects;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.jamieadkins.heistr.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Jamie on 26/09/2015.
 */
public class Attachment {

    public static final int MOD_AMMO = 0;
    public static final int MOD_BARREL = 1;
    public static final int MOD_BARREL_EXTENSION = 2;
    public static final int MOD_BAYONET = 3;
    public static final int MOD_CUSTOM = 4;
    public static final int MOD_EXTRA = 5;
    public static final int MOD_FOREGRIP = 6;
    public static final int MOD_GADGET = 7;
    public static final int MOD_GRIP = 8;
    public static final int MOD_LOWER_RECEIVER = 9;
    public static final int MOD_MAGAZINE = 10;
    public static final int MOD_SIGHT = 11;
    public static final int MOD_SLIDE = 12;
    public static final int MOD_STOCK = 13;
    public static final int MOD_SUPPRESSOR = 14;
    public static final int MOD_UPPER_RECEIVER = 15;
    public static final int MOD_STAT_BOOST = 16;

    long id = -1;
    long pd2skillsID = -1;
    String pd2 = "attach";
    String name = "attach";
    String subtype = "none";
    int attachmentGroup = -1;
    private int magsize = 100;
    private float damage = 100;
    private float accuracy = 100;
    private float stability = 100;
    private int concealment = 100;
    private int threat = 100;

    //region XML
    public static ArrayList<Attachment> getAttachmentsFromXML(Resources res) {
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
                            if (!(currentAttachment.getName().contains("ERROR")
                                    || currentAttachment.getName().contains("Standard Issue Part")
                                    || currentAttachment.getName().toLowerCase().contains("no modification")
                                    || currentAttachment.getName().contains("attach"))) {

                                if (currentAttachment.damage == 0 &&
                                        currentAttachment.stability == 0 &&
                                        currentAttachment.accuracy == 0 &&
                                        currentAttachment.concealment == 0 && currentAttachment.magsize == 0 &&
                                        !currentAttachment.getName().toLowerCase().contains("laser") &&
                                        !currentAttachment.getPd2().contains("saw")) {
                                    //Log.e("JAMIEA", currentAttachment.getName());
                                    //Log.e("JAMIEA", currentAttachment.getPd2());
                                } else {
                                    attachments.add(currentAttachment);
                                }
                            }
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
                        case "pd2":
                            currentAttachment.setPd2(text);
                            break;
                        case "pd2skills":
                            currentAttachment.setPd2skillsID(Long.parseLong(text));
                            break;
                        case "subtype":
                            currentAttachment.setSubtype(text);
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

    public static int attachmentGroupFromString(String xmlString) {
        int group = -1;
        switch (xmlString) {
            case "ammo":
                group = MOD_AMMO;
                break;
            case "barrel":
                group = MOD_BARREL;
                break;
            case "barrel_ext":
                group = MOD_BARREL_EXTENSION;
                break;
            case "sight":
                group = MOD_SIGHT;
                break;
            case "grip":
                group = MOD_GRIP;
                break;
            case "upper_body":
                group = MOD_UPPER_RECEIVER;
                break;
            case "foregrip":
                group = MOD_FOREGRIP;
                break;
            case "bayonet":
                group = MOD_BAYONET;
                break;
            case "stock_adapter":
                group = MOD_UPPER_RECEIVER;
                break;
            case "lower_receiver":
                group = MOD_LOWER_RECEIVER;
                break;
            case "stock":
                group = MOD_STOCK;
                break;
            case "drag_handle":
                group = MOD_UPPER_RECEIVER;
                break;
            case "extra":
                group = MOD_EXTRA;
                break;
            case "magazine":
                group = MOD_MAGAZINE;
                break;
            case "lower_body":
                group = MOD_UPPER_RECEIVER;
                break;
            case "gadget":
                group = MOD_GADGET;
                break;
            case "lower_reciever":
                group = MOD_LOWER_RECEIVER;
                break;
            case "custom":
                group = MOD_CUSTOM;
                break;
            case "upper_reciever":
                group = MOD_UPPER_RECEIVER;
                break;
            case "sight_special":
                group = MOD_SIGHT;
                break;
            case "vertical_grip":
                group = MOD_GRIP;
                break;
            case "slide":
                group = MOD_SLIDE;
                break;
            case "foregrip_ext":
                group = MOD_FOREGRIP;
                break;
            case "bonus":
                group = MOD_STAT_BOOST;
                break;
            default:
                group = MOD_UPPER_RECEIVER;
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

    public String getName() {
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

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getPd2() {
        return pd2;
    }

    public void setPd2(String pd2) {
        this.pd2 = pd2;
    }

    public String toString(Resources res) {
        String info = "";

        if (magsize != 0) {
            info += res.getString(R.string.weapon_attribute_magazine) + ": " + magsize + "\n";
        }
        if (damage != 0) {
            info += res.getString(R.string.weapon_attribute_damage) + ": " + damage + "\n";
        }
        if (accuracy != 0) {
            info += res.getString(R.string.weapon_attribute_accuracy) + ": " + accuracy + "\n";
        }
        if (stability != 0) {
            info += res.getString(R.string.weapon_attribute_stability) + ": " + stability + "\n";
        }
        if (concealment != 0) {
            info += res.getString(R.string.weapon_attribute_concealment) + ": " + concealment + "\n";
        }

        info += "\n\nThis is just a placeholder. Really sorry about how unhelpful this is.";
        return info;
    }
}
