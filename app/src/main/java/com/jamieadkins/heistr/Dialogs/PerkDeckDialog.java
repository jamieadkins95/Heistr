package com.jamieadkins.heistr.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.jamieadkins.heistr.R;

/**
 * Created by Jamie on 23/08/2015.
 */
public class PerkDeckDialog extends DialogFragment {
    int perkDeck;

    public static PerkDeckDialog newInstance(int perkDeck) {
        PerkDeckDialog dialog = new PerkDeckDialog();
        dialog.perkDeck = perkDeck;
        return dialog;
    }

    public PerkDeckDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String desc = getActivity().getResources().getStringArray(R.array.perkDeckDescs)[perkDeck];
        String title = getActivity().getResources().getStringArray(R.array.perkDecks)[perkDeck];

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
