package com.dawgandpony.pd2skills.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 16/09/2015.
 */
public class RenameBuildDialog extends DialogFragment {
    SparseBooleanArray buildPositions;
    RenameBuildDialogListener mListener;

    public static RenameBuildDialog newInstance(SparseBooleanArray buildPositions) {
        RenameBuildDialog dialog = new RenameBuildDialog();
        dialog.buildPositions = buildPositions.clone();
        return dialog;
    }

    public RenameBuildDialog() {
        super();
    }

    public interface RenameBuildDialogListener {
        public void onDialogRenameBuild(DialogFragment dialog, String name, SparseBooleanArray buildPositions);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_rename_build, null);

        builder.setView(v);

        String title = "Rename Builds";

        builder.setTitle(title)
                .setPositiveButton(R.string.action_rename, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Name
                        EditText etName = (EditText) getDialog().findViewById(R.id.etNewBuildName);
                        mListener.onDialogRenameBuild(RenameBuildDialog.this, etName.getText().toString(), buildPositions);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (RenameBuildDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
