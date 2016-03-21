package com.jamieadkins.heistr.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jamieadkins.heistr.R;

/**
 * Created by Jamie on 16/09/2015.
 */
public class PD2SkillsExportDialog extends DialogFragment {
    String URL;

    public static PD2SkillsExportDialog newInstance(String URL) {
        PD2SkillsExportDialog dialog = new PD2SkillsExportDialog();
        dialog.URL = URL;

        return dialog;
    }

    public PD2SkillsExportDialog() {
        super();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_rename_build, null);
        EditText etName = (EditText) v.findViewById(R.id.etNewBuildName);
        etName.setText(URL);
        etName.setHint("URL was here");

        builder.setView(v);

        String title = "PD2skills.com URL";

        builder.setTitle(title)
                .setNegativeButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
