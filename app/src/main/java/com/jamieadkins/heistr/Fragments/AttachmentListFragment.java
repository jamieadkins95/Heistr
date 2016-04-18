package com.jamieadkins.heistr.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jamieadkins.heistr.Activities.EditBuildActivity;
import com.jamieadkins.heistr.Activities.EditWeaponActivity;
import com.jamieadkins.heistr.BuildObjects.Attachment;
import com.jamieadkins.heistr.Dialogs.AttachmentDetailsDialog;
import com.jamieadkins.heistr.Fragments.ViewPagerFragments.ViewPagerLifecycle;
import com.jamieadkins.heistr.R;

import java.util.ArrayList;

/**
 * Created by Jamie on 18/10/2015.
 */
public class AttachmentListFragment extends Fragment implements EditWeaponActivity.WeaponsCallbacks,
        EditBuildActivity.BuildReadyCallbacks, ViewPagerLifecycle,
        AttachmentDetailsDialog.AttachmentDetailsListener {

    private static final String ARG_ATTACHMENT_TYPE = "AttachmentType";
    ListView lvAttachments;
    EditWeaponActivity mActivity;
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
        mActivity = (EditWeaponActivity) getActivity();
        attachmentType = getArguments().getInt(ARG_ATTACHMENT_TYPE);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_attachment_list, container, false);
        lvAttachments = (ListView) rootView.findViewById(R.id.lvArmour);
        lvAttachments.setEmptyView(rootView.findViewById(R.id.emptyElement));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (EditWeaponActivity) getActivity();
        if (mActivity.getCurrentWeapon() != null) {
            onWeaponReady();
        } else {
            mActivity.listen(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //mActivity.stopListening(this);
        mActivity = null;
    }

    @Override
    public void onWeaponReady() {
        possibleAttachments = mActivity.getPossibleAttachments(attachmentType);

        ArrayList<String> attachments = new ArrayList<>();
        for (Attachment a : possibleAttachments) {
            attachments.add(a.getName());
        }

        int count = 0;
        int index = -1;
        for (Attachment a : possibleAttachments) {
            for (Attachment e : mActivity.getCurrentWeapon().getAttachments()) {
                if (a.getPd2().equals(e.getPd2())) {
                    index = count;
                }
            }
            count++;
        }

        if (index != -1) {
            currentAttachmentIndex = index;
        }

        final ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_single_choice, attachments);
        lvAttachments.setAdapter(mAdapter2);
        lvAttachments.setItemChecked(currentAttachmentIndex, true);
        lvAttachments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                equipAttachment(position);
            }
        });
        lvAttachments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Attachment currentAttachment = null;
                if (currentAttachmentIndex != -1) {
                    currentAttachment = possibleAttachments.get(currentAttachmentIndex);
                }
                AttachmentDetailsDialog dialog = AttachmentDetailsDialog.newInstance(mActivity.getCurrentBuild(),
                        mActivity.getCurrentWeapon(),
                        currentAttachment,
                        possibleAttachments.get((int) id), (int) id);
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                dialog.setTargetFragment(AttachmentListFragment.this, BuildListFragment.DIALOG_FRAGMENT);
                dialog.show(fragmentManager, "AttachmentDetails");
                return true;
            }
        });

    }

    @Override
    public void onEquip(int position) {
        lvAttachments.setItemChecked(position, true);
        equipAttachment(position);
    }

    private void equipAttachment(int position) {
        int oldAttachment = currentAttachmentIndex;
        if (position != currentAttachmentIndex) {
            currentAttachmentIndex = lvAttachments.getCheckedItemPosition();
        } else {
            currentAttachmentIndex = -1;
            lvAttachments.setItemChecked(position, false);
        }
        mActivity.updateCurrentWeapon(attachmentType, oldAttachment, currentAttachmentIndex);
    }

    @Override
    public void onBuildReady() {
    }

    @Override
    public void onBuildUpdated() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {

    }


}
