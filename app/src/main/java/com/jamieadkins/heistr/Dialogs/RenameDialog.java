package com.jamieadkins.heistr.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jamieadkins.heistr.R;

/**
 * Created by Jamie on 16/09/2015.
 */
public class RenameDialog extends DialogFragment {
    SparseBooleanArray buildPositions;
    RenameDialogListener mListener;
    boolean callingFromActivity;

    public static RenameDialog newInstance(boolean callingFromActivity, SparseBooleanArray buildPositions) {
        RenameDialog dialog = new RenameDialog();
        dialog.buildPositions = null;
        if (buildPositions != null) {
            dialog.buildPositions = buildPositions.clone();
        }

        dialog.callingFromActivity = callingFromActivity;
        return dialog;
    }

    public RenameDialog() {
        super();
    }

    public interface RenameDialogListener {
        public void onDialogRenameBuild(DialogFragment dialog, String name, SparseBooleanArray buildPositions);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_rename_build, null);

        builder.setView(v);

        String title = "Rename";

        builder.setTitle(title)
                .setPositiveButton(R.string.action_rename, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Name
                        EditText etName = (EditText) getDialog().findViewById(R.id.etNewBuildName);
                        mListener.onDialogRenameBuild(RenameDialog.this, etName.getText().toString(), buildPositions);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            if (callingFromActivity) {
                mListener = (RenameDialogListener) context;
            } else {
                mListener = (RenameDialogListener) getTargetFragment();
            }

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
