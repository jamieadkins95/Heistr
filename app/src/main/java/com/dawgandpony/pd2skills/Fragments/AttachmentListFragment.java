package com.dawgandpony.pd2skills.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.Activities.EditWeaponActivity;
import com.dawgandpony.pd2skills.BuildObjects.Attachment;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jamie on 18/10/2015.
 */
public class AttachmentListFragment extends Fragment implements EditWeaponActivity.WeaponsCallbacks, EditBuildActivity.BuildReadyCallbacks{

    private static final String ARG_ATTACHMENT_TYPE = "AttachmentType";
    ListView lvAttachments;
    EditWeaponActivity activity;
    int attachmentType;
    ArrayList<Attachment> possibleAttachments;
    int currentAttachmentIndex = -1;


    public AttachmentListFragment() {
        // Required empty public constructor
    }

    public static AttachmentListFragment newInstance(int attachmentType) {
        Bundle args = new Bundle();
        args.putInt(ARG_ATTACHMENT_TYPE, attachmentType);
        AttachmentListFragment fragment = new AttachmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (EditWeaponActivity) getActivity();
        attachmentType = getArguments().getInt(ARG_ATTACHMENT_TYPE);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_armour, container, false);
        lvAttachments = (ListView) rootView.findViewById(R.id.lvArmour);

        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = (EditWeaponActivity) getActivity();
        if (activity.getCurrentWeapon() != null){
            onWeaponReady();
        } else {
            activity.listen(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    public void onWeaponReady() {
        possibleAttachments = activity.getPossibleAttachments(attachmentType);

        ArrayList<String> attachments = new ArrayList<>();
        for (Attachment a : possibleAttachments){
            attachments.add(a.getName());
        }

        int count = 0;
        int index = -1;
        for (Attachment a : possibleAttachments){
            for (Attachment e : activity.getCurrentWeapon().getAttachments()){
                if (a.getPd2skillsID() ==  e.getPd2skillsID()){
                    index = count;
                }
            }
            count++;
        }

        if (index != -1){
            currentAttachmentIndex = index;
        }

        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_single_choice, attachments);
        lvAttachments.setAdapter(mAdapter2);
        lvAttachments.setItemChecked(currentAttachmentIndex, true);
        lvAttachments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentAttachmentIndex = lvAttachments.getCheckedItemPosition();
                activity.updateCurrentWeapon(attachmentType, currentAttachmentIndex);
                Toast.makeText(getActivity(), possibleAttachments.get(currentAttachmentIndex).getPd2skillsID() + "", Toast.LENGTH_SHORT).show();
            }
        });
        lvAttachments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Attachment selectedAttachment = possibleAttachments.get((int) id);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(selectedAttachment.toString(getResources()))
                        .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .create()
                        .show();


                return true;
            }
        });

    }

    @Override
    public void onBuildReady() {
    }

    @Override
    public void onBuildUpdated() {

    }
}
