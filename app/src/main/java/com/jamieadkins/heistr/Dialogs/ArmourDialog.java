package com.jamieadkins.heistr.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.jamieadkins.heistr.R;

/**
 * Shows base stats of armour.
 */
public class ArmourDialog extends DialogFragment {
    int armour;

    public static ArmourDialog newInstance(int perkDeck) {
        ArmourDialog dialog = new ArmourDialog();
        dialog.armour = perkDeck;
        return dialog;
    }

    public ArmourDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String desc = getActivity().getResources().getStringArray(R.array.armourDetails)[armour];
        String title =
                getActivity().getResources().getStringArray(R.array.armour)[armour] + " Base Stats";

        builder.setMessage(desc)
                .setTitle(title)
                .setNegativeButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
