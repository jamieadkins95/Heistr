package com.jamieadkins.heistr.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.jamieadkins.heistr.BuildObjects.Attachment;
import com.jamieadkins.heistr.BuildObjects.Build;
import com.jamieadkins.heistr.BuildObjects.Weapon;
import com.jamieadkins.heistr.R;

/**
 * Created by Jamie on 15/04/2016.
 */
public class AttachmentDetailsDialog extends DialogFragment {
    Build mBuild;
    Weapon mCurrentWeapon;
    Attachment mCurrentAttachment;
    Attachment mNewAttachment;
    int mSelectedPosition;

    public static AttachmentDetailsDialog newInstance(Build currentBuild, Weapon currentWeapon, Attachment currentAttachment, Attachment mNewAttachment, int selectedPosition) {
        AttachmentDetailsDialog dialog = new AttachmentDetailsDialog();
        dialog.mBuild = currentBuild;
        dialog.mCurrentWeapon = currentWeapon;
        dialog.mNewAttachment = mNewAttachment;
        dialog.mCurrentAttachment = currentAttachment;
        dialog.mSelectedPosition = selectedPosition;
        return dialog;
    }

    public interface AttachmentDetailsListener {
        void onEquip(int newAttachmentPosition);
    }

    AttachmentDetailsListener mListener;

    public AttachmentDetailsDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_attachment_details, null);
        builder.setView(v);

        TextView currentAttachmentName = (TextView) v.findViewById(R.id.current_attachment);
        if (mCurrentAttachment == null) {
            currentAttachmentName.setVisibility(View.GONE);
        }

        int green = ContextCompat.getColor(getActivity(), R.color.green);
        int red = ContextCompat.getColor(getActivity(), R.color.red);

        //region TextView setup
        {
            // Total Ammo
            TextView attribute = (TextView) v.findViewById(R.id.weapon_attribute_ammo);
            TextView currentWeapon = (TextView) v.findViewById(R.id.current_weapon_ammo);
            currentWeapon.setText(mCurrentWeapon.getTotalAmmo(mBuild.getStatChangeManager()) + "");

            TextView currentAttachment = (TextView) v.findViewById(R.id.current_attachment_ammo);
            if (mCurrentAttachment != null) {
                currentAttachment.setText(mCurrentAttachment.getTotalAmmo() + "");
            } else {
                currentAttachment.setVisibility(View.GONE);
            }

            TextView newAttachment = (TextView) v.findViewById(R.id.new_attachment_ammo);
            newAttachment.setText(mNewAttachment.getTotalAmmo() + "");

            if (mCurrentAttachment != null && mNewAttachment.getTotalAmmo() != mCurrentAttachment.getTotalAmmo()) {
                boolean better = mNewAttachment.getTotalAmmo() > mCurrentAttachment.getTotalAmmo();
                newAttachment.setTextColor(better ? green : red);
            }

            boolean hide = mNewAttachment.getTotalAmmo() == 0;
            if (mCurrentAttachment != null) {
                hide = hide && mCurrentAttachment.getTotalAmmo() == 0;
            }

            if (hide) {
                attribute.setVisibility(View.GONE);
                currentWeapon.setVisibility(View.GONE);
                currentAttachment.setVisibility(View.GONE);
                newAttachment.setVisibility(View.GONE);
            }
        }

        // Mag size
        {
            TextView attribute = (TextView) v.findViewById(R.id.weapon_attribute_magazine);
            TextView currentWeapon = (TextView) v.findViewById(R.id.current_weapon_magazine);
            currentWeapon.setText(mCurrentWeapon.getMagSize(mBuild.getStatChangeManager()) + "");

            TextView currentAttachment = (TextView) v.findViewById(R.id.current_attachment_magazine);
            if (mCurrentAttachment != null) {
                currentAttachment.setText(mCurrentAttachment.getMagsize() + "");
            } else {
                currentAttachment.setVisibility(View.GONE);
            }

            TextView newAttachment = (TextView) v.findViewById(R.id.new_attachment_magazine);
            newAttachment.setText(mNewAttachment.getMagsize() + "");

            if (mCurrentAttachment != null && mNewAttachment.getMagsize() != mCurrentAttachment.getMagsize()) {
                boolean better = mNewAttachment.getMagsize() > mCurrentAttachment.getMagsize();
                newAttachment.setTextColor(better ? green : red);
            }

            boolean hide = mNewAttachment.getMagsize() == 0;
            if (mCurrentAttachment != null) {
                hide = hide && mCurrentAttachment.getMagsize() == 0;
            }

            if (hide) {
                attribute.setVisibility(View.GONE);
                currentWeapon.setVisibility(View.GONE);
                currentAttachment.setVisibility(View.GONE);
                newAttachment.setVisibility(View.GONE);
            }
        }

        // Damage
        {
            TextView attribute = (TextView) v.findViewById(R.id.weapon_attribute_damage);
            TextView currentWeapon = (TextView) v.findViewById(R.id.current_weapon_damage);
            currentWeapon.setText(mCurrentWeapon.getDamage(mBuild.getStatChangeManager()) + "");

            TextView currentAttachment= (TextView) v.findViewById(R.id.current_attachment_damage);
            if (mCurrentAttachment != null) {
                currentAttachment.setText(mCurrentAttachment.getDamage() + "");
            } else {
                currentAttachment.setVisibility(View.GONE);
            }

            TextView newAttachment = (TextView) v.findViewById(R.id.new_attachment_damage);
            newAttachment.setText(mNewAttachment.getDamage() + "");

            if (mCurrentAttachment != null && mNewAttachment.getDamage() != mCurrentAttachment.getDamage()) {
                boolean better = mNewAttachment.getDamage() > mCurrentAttachment.getDamage();
                newAttachment.setTextColor(better ? green : red);
            }

            boolean hide = mNewAttachment.getDamage() == 0;
            if (mCurrentAttachment != null) {
                hide = hide && mCurrentAttachment.getDamage() == 0;
            }

            if (hide) {
                attribute.setVisibility(View.GONE);
                currentWeapon.setVisibility(View.GONE);
                currentAttachment.setVisibility(View.GONE);
                newAttachment.setVisibility(View.GONE);
            }
        }

        // Accuracy
        {
            TextView attribute = (TextView) v.findViewById(R.id.weapon_attribute_accuracy);
            TextView currentWeapon = (TextView) v.findViewById(R.id.current_weapon_accuracy);
            currentWeapon.setText(mCurrentWeapon.getAccuracy(mBuild.getStatChangeManager()) + "");

            TextView currentAttachment= (TextView) v.findViewById(R.id.current_attachment_accuracy);
            if (mCurrentAttachment != null) {
                currentAttachment.setText(mCurrentAttachment.getAccuracy() + "");
            } else {
                currentAttachment.setVisibility(View.GONE);
            }

            TextView newAttachment = (TextView) v.findViewById(R.id.new_attachment_accuracy);
            newAttachment.setText(mNewAttachment.getAccuracy() + "");

            if (mCurrentAttachment != null && mNewAttachment.getAccuracy() != mCurrentAttachment.getAccuracy()) {
                boolean better = mNewAttachment.getAccuracy() > mCurrentAttachment.getAccuracy();
                newAttachment.setTextColor(better ? green : red);
            }

            boolean hide = mNewAttachment.getAccuracy() == 0;
            if (mCurrentAttachment != null) {
                hide = hide && mCurrentAttachment.getAccuracy() == 0;
            }

            if (hide) {
                attribute.setVisibility(View.GONE);
                currentWeapon.setVisibility(View.GONE);
                currentAttachment.setVisibility(View.GONE);
                newAttachment.setVisibility(View.GONE);
            }
        }

        // Stability
        {
            TextView attribute = (TextView) v.findViewById(R.id.weapon_attribute_stability);
            TextView currentWeapon = (TextView) v.findViewById(R.id.current_weapon_stability);
            currentWeapon.setText(mCurrentWeapon.getStability(mBuild.getStatChangeManager()) + "");

            TextView currentAttachment= (TextView) v.findViewById(R.id.current_attachment_stability);
            if (mCurrentAttachment != null) {
                currentAttachment.setText(mCurrentAttachment.getStability() + "");
            } else {
                currentAttachment.setVisibility(View.GONE);
            }

            TextView newAttachment = (TextView) v.findViewById(R.id.new_attachment_stability);
            newAttachment.setText(mNewAttachment.getStability() + "");

            if (mCurrentAttachment != null && mNewAttachment.getStability() != mCurrentAttachment.getStability()) {
                boolean better = mNewAttachment.getStability() > mCurrentAttachment.getStability();
                newAttachment.setTextColor(better ? green : red);
            }

            boolean hide = mNewAttachment.getStability() == 0;
            if (mCurrentAttachment != null) {
                hide = hide && mCurrentAttachment.getStability() == 0;
            }

            if (hide) {
                attribute.setVisibility(View.GONE);
                currentWeapon.setVisibility(View.GONE);
                currentAttachment.setVisibility(View.GONE);
                newAttachment.setVisibility(View.GONE);
            }
        }

        // Concealment
        {
            TextView attribute = (TextView) v.findViewById(R.id.weapon_attribute_concealment);
            TextView currentWeapon = (TextView) v.findViewById(R.id.current_weapon_concealment);
            currentWeapon.setText(mCurrentWeapon.getConcealment(mBuild.getStatChangeManager()) + "");

            TextView currentAttachment= (TextView) v.findViewById(R.id.current_attachment_concealment);
            if (mCurrentAttachment != null) {
                currentAttachment.setText(mCurrentAttachment.getConcealment() + "");
            } else {
                currentAttachment.setVisibility(View.GONE);
            }

            TextView newAttachment = (TextView) v.findViewById(R.id.new_attachment_concealment);
            newAttachment.setText(mNewAttachment.getConcealment() + "");

            if (mCurrentAttachment != null && mNewAttachment.getConcealment() != mCurrentAttachment.getConcealment()) {
                boolean better = mNewAttachment.getConcealment() > mCurrentAttachment.getConcealment();
                newAttachment.setTextColor(better ? green : red);
            }

            boolean hide = mNewAttachment.getConcealment() == 0;
            if (mCurrentAttachment != null) {
                hide = hide && mCurrentAttachment.getConcealment() == 0;
            }

            if (hide) {
                attribute.setVisibility(View.GONE);
                currentWeapon.setVisibility(View.GONE);
                currentAttachment.setVisibility(View.GONE);
                newAttachment.setVisibility(View.GONE);
            }
        }
        //endregion

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setTitle(mNewAttachment.getName())
                .setPositiveButton(R.string.equip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onEquip(mSelectedPosition);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        // Create the AlertDialog object and return it
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the Listener so we can send events to the host
            mListener = (AttachmentDetailsListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement AttachmentDetailsListener");
        }
    }
}
