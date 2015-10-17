package com.dawgandpony.pd2skills.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterWeaponList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 04/09/2015.
 */
public class NewWeaponDialog extends DialogFragment {

    ArrayList<Weapon> userWeaponList;
    ArrayList<Weapon> allWeapons;
    ArrayList<Weapon> templates;

    public static NewWeaponDialog newInstance(ArrayList<Weapon> userWeapons, ArrayList<Weapon> allWeapons) {
        NewWeaponDialog dialog = new NewWeaponDialog();
        dialog.userWeaponList = new ArrayList<>();
        dialog.userWeaponList.addAll(userWeapons);
        dialog.allWeapons = allWeapons;
        dialog.templates = new ArrayList<>();
        return dialog;
    }

    public interface NewWeaponDialogListener {
        void onDialogNewWeapon(DialogFragment dialog, String name, long baseWeaponID, int templateWeaponPos);
    }

    NewWeaponDialogListener mListener;


    public NewWeaponDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_new_weapon, null);

        builder.setView(v);


        Spinner spBase = (Spinner) v.findViewById(R.id.spBaseWeapon);
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list2.add("Select Template");

        for (Weapon w : allWeapons){
            list.add(w.getWeaponName());
        }

        Spinner spTemplates = (Spinner) v.findViewById(R.id.spTemplate);
        for (Weapon w : userWeaponList){
            if (w.getPd2skillsID() == allWeapons.get(0).getPd2skillsID()){
                templates.add(w);
                list2.add(w.getName());
            }
        }
        final ArrayAdapter<String> adapterTemplates = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list2);
        adapterTemplates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> adapterBase = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapterBase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapterTemplates.clear();
                adapterTemplates.add("Select Template");
                for (Weapon w : userWeaponList){
                    if (w.getPd2skillsID() == allWeapons.get((int) id).getPd2skillsID()){
                        adapterTemplates.add(w.getName());
                    }
                }
                adapterTemplates.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBase.setAdapter(adapterBase);
        spTemplates.setAdapter(adapterTemplates);



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setTitle("New Weapon")
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Name
                        EditText etName = (EditText) getDialog().findViewById(R.id.etNewWeaponName);
                        final String name = etName.getText().toString();

                        //Template
                        Spinner spBaseWeapon = (Spinner) getDialog().findViewById(R.id.spBaseWeapon);
                        int selectedBase = spBaseWeapon.getSelectedItemPosition(); // -1 for templete text

                        //Template
                        Spinner spTemplate = (Spinner) getDialog().findViewById(R.id.spTemplate);
                        int selected = spTemplate.getSelectedItemPosition() - 1; // -1 for templete text


                        mListener.onDialogNewWeapon(NewWeaponDialog.this, name, allWeapons.get(selectedBase).getPd2skillsID(), selected);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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
            mListener = (NewWeaponDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
