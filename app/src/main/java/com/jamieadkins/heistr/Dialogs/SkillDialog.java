package com.jamieadkins.heistr.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.jamieadkins.heistr.BuildObjects.Skill;
import com.jamieadkins.heistr.R;

/**
 * Created by Jamie on 23/08/2015.
 */
public class SkillDialog extends DialogFragment {
    Skill skill;

    public static SkillDialog newInstance(Skill skill) {
        SkillDialog dialog = new SkillDialog();
        dialog.skill = skill;
        return dialog;
    }

    public SkillDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String points = "points";
        if (skill.getNormalPoints() == 1) {
            points = "point";
        }

        builder.setMessage("Normal - " + skill.getNormalPoints() + " " + points + ": " + skill.getNormalDescription() + "\n\nAce - " + skill.getAcePoints() + " points: " + skill.getAceDescription())
                .setTitle(skill.getName())
                .setNegativeButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
