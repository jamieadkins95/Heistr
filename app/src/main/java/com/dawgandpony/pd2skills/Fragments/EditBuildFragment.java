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
public abstract class EditBuildFragment extends Fragment implements TaskFragment.TaskCallbacks {




    private Build currentBuild;
    EditBuildActivity activity;
    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private TaskFragment mTaskFragment;

    private long buildID = -1;

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


    @Override
    public void onPreExecute() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(Build build) {
        setCurrentBuild(build);
    }



    public Build getCurrentBuild() {
        return currentBuild;
    }

    public void setCurrentBuild(Build currentBuild) {
        this.currentBuild = currentBuild;
        this.buildID = currentBuild.getId();
    }

    public void updateInfamy(int infamy, boolean infamyEnabled){
        currentBuild.updateInfamy(getActivity(), infamy, infamyEnabled);

    }

    public void updatePerkDeck(int selected){
        currentBuild.updatePerkDeck(getActivity(), selected);

    }

    public void updateArmour(int selected){
        currentBuild.updateArmour(getActivity(), selected);

    }
}
