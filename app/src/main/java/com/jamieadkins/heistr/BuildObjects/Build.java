package com.jamieadkins.heistr.BuildObjects;

import android.content.Context;

import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.Database.DataSourceBuilds;
import com.jamieadkins.heistr.Database.DataSourceSkills;

import java.util.ArrayList;

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
    ArrayList<MeleeWeapon> meleeWeaponsFromXML;
    ArrayList<Attachment> attachmentsFromXML;
    ArrayList<Attachment> attachmentsOverridesFromXML;

    int perkDeck = 0;
    int armour = 0;


    public Build() {
        infamies = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            infamies.add(false);
        }
        ;
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

    public SkillStatChangeManager getStatChangeManager() {
        return SkillStatChangeManager.getInstance(skillBuild);
    }

    public ArrayList<Weapon> getWeaponsFromXML() {
        return weaponsFromXML;
    }

    public void setWeaponsFromXML(ArrayList<Weapon> weaponsFromXML) {
        this.weaponsFromXML = weaponsFromXML;
    }

    public ArrayList<MeleeWeapon> getMeleeWeaponsFromXML() {
        return meleeWeaponsFromXML;
    }

    public void setMeleeWeaponsFromXML(ArrayList<MeleeWeapon> meleeWeaponsFromXML) {
        this.meleeWeaponsFromXML = meleeWeaponsFromXML;
    }

    public ArrayList<Attachment> getAttachmentsFromXML() {
        return attachmentsFromXML;
    }

    public void setAttachmentsFromXML(ArrayList<Attachment> attachmentsFromXML) {
        this.attachmentsFromXML = attachmentsFromXML;
    }

    public ArrayList<Attachment> getAttachmentsOverridesFromXML() {
        return attachmentsOverridesFromXML;
    }

    public void setAttachmentsOverridesFromXML(ArrayList<Attachment> attachmentsOverridesFromXML) {
        this.attachmentsOverridesFromXML = attachmentsOverridesFromXML;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void updateInfamy(Context context, int infamy, boolean enabled) {
        getInfamies().set(infamy, enabled);
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updateInfamy(id, getInfamyID());
        dataSourceBuilds.close();

        int count = Trees.MASTERMIND;
        for (boolean infamyBonus : infamies) {
            skillBuild.setInfamyBonus(count, infamyBonus);
            count++;

            if (infamyBonus) {
                skillBuild.setInfamyBonus(Trees.FUGITIVE, true);
            }
        }
    }

    public void updatePerkDeck(Context context, int selected) {
        perkDeck = selected;
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updatePerkDeck(id, selected);
        dataSourceBuilds.close();

    }

    public void updateSkillTier(Context context, int skillTree, SkillTier updatedTier) {
        int tierNumber = updatedTier.getNumber();
        skillBuild.getSkillTrees().get(skillTree).getTierList().set(tierNumber, updatedTier);

        DataSourceSkills dataSourceSkills = new DataSourceSkills(context);
        dataSourceSkills.open();
        dataSourceSkills.updateSkillTier(id, skillTree, updatedTier);
        dataSourceSkills.close();

    }

    public void updateSubTree(Context context, int skillTree, NewSkillSubTree updatedSubTree) {
        int subTree = updatedSubTree.getSubTree();
        skillBuild.getNewSkillTrees().get(skillTree).getSubTrees().set(subTree, updatedSubTree);

        DataSourceSkills dataSourceSkills = new DataSourceSkills(context);
        dataSourceSkills.open();
        dataSourceSkills.updateSubTree(id, skillTree, updatedSubTree);
        dataSourceSkills.close();

    }

    public void updateArmour(Context context, int selected) {
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

    public void updateWeaponBuild(Context context, MeleeWeapon meleeWeapon) {
        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(context);
        dataSourceBuilds.open();
        dataSourceBuilds.updateMeleeWeapon(weaponBuild.getId(), meleeWeapon);
        dataSourceBuilds.close();
        weaponBuild.setMeleeWeapon(meleeWeapon);
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
        if (getInfamies().get(Trees.MASTERMIND)) {
            id += 8;
        }
        if (getInfamies().get(Trees.ENFORCER)) {
            id += 4;
        }
        if (getInfamies().get(Trees.TECHNICIAN)) {
            id += 2;
        }
        if (getInfamies().get(Trees.GHOST)) {
            id += 1;
        }
        return id;
    }

    public int getDetection() {
        int concealment = 0;

        if (getWeaponBuild().getPrimaryWeapon() != null) {
            concealment += getWeaponBuild().getPrimaryWeapon().getConcealment(getStatChangeManager());
        } else {
            concealment += 30;
        }

        if (getWeaponBuild().getSecondaryWeapon() != null) {
            concealment += getWeaponBuild().getSecondaryWeapon().getConcealment(getStatChangeManager());
        } else {
            concealment += 30;
        }

        if (getWeaponBuild().getMeleeWeapon() != null) {
            concealment += getWeaponBuild().getMeleeWeapon().getConcealment(getStatChangeManager());
        } else {
            concealment += 30;
        }

        concealment += getArmourConcealment(getStatChangeManager());

        // Blending in concealment bonus
        concealment++;

        return getDetectionFromConcealment(concealment);
    }

    private int getArmourConcealment(SkillStatChangeManager skillStatChangeManager) {
        int con = 0;
        for (SkillStatModifier modifier : skillStatChangeManager.getModifiers()) {
            for (int weaponType : modifier.getWeaponTypes()) {
                if (weaponType == SkillStatChangeManager.BALLISTIC_VESTS) {
                    con += modifier.getConcealment();
                }
            }
        }

        switch (armour) {
            case 1:
                return 30;
            case 2:
                return 26 + con;
            case 3:
                return 23 + con;
            case 4:
                return 21 + con;
            case 5:
                return 18;
            case 6:
                return 12;
            case 7:
                return 1;
            default:
                return 30;
        }
    }

    private int getDetectionFromConcealment(int concealment) {

        if (concealment >= 119) {
            return 3;
        } else if (concealment <= 91) {
            return 75;
        } else {
            switch (concealment) {
                case 118:
                    return 4;
                case 117:
                    return 6;
                case 116:
                    return 7;
                case 115:
                    return 9;
                case 114:
                    return 10;
                case 113:
                    return 12;
                case 112:
                    return 13;
                case 111:
                    return 14;
                case 110:
                    return 16;
                case 109:
                    return 17;
                case 108:
                    return 19;
                case 107:
                    return 20;
                case 106:
                    return 22;
                case 105:
                    return 23;
                case 104:
                    return 26;
                case 103:
                    return 29;
                case 102:
                    return 32;
                case 101:
                    return 35;
                case 100:
                    return 43;
                case 99:
                    return 45;
                case 98:
                    return 46;
                case 97:
                    return 49;
                case 96:
                    return 52;
                case 95:
                    return 55;
                case 94:
                    return 58;
                case 93:
                    return 63;
                case 92:
                    return 69;
                default:
                    return 0;
            }
        }
    }

    public boolean infamyReductionInTree(SkillTier tier) {
        boolean infamyInThisTree = false;
        if (tier.getSkillTree() < Trees.FUGITIVE) {
            infamyInThisTree = getInfamies().get(tier.getSkillTree());
        } else {
            if (getInfamyID() > 1) {
                infamyInThisTree = true;
            }
        }

        return infamyInThisTree;
    }


    public float getEhp() {
        return 0.0f;
    }
}
