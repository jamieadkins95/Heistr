package com.dawgandpony.pd2skills.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;


import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;

/**
 * Created by Jamie on 25/08/2015.
 */
public abstract class EditBuildFragment extends Fragment implements EditBuildActivity.BuildReadyCallbacks {



    EditBuildActivity activity;
    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private TaskFragment mTaskFragment;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = (EditBuildActivity) getActivity();
        FragmentManager fm = activity.getSupportFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If we haven't retained the worker fragment, then create it
        // and set this UIFragment as the TaskFragment's target fragment.
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            mTaskFragment.setTargetFragment(this, 0);
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }

        if (mTaskFragment.isRunning()) {
            mTaskFragment.cancel();
        } else {
            mTaskFragment.start(activity.getCurrentBuildID(), activity.getNewBuildName());
        }
    }


}
