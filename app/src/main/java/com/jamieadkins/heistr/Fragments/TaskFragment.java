package com.jamieadkins.heistr.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jamieadkins.heistr.BuildObjects.Build;
import com.jamieadkins.heistr.Database.DataSourceBuilds;

/**
 * TaskFragment manages a single background task and retains itself across configuration changes.
 */
public class TaskFragment extends Fragment {
    private static final String TAG = TaskFragment.class.getSimpleName();
    private static final String EXTRA_CURRENT_BUILD_ID = "CURRENT_BUILD_ID";
    private static final boolean DEBUG = true; // Set this to false to disable logs.

    /**
     * Callback interface through which the fragment can report the task's progress and results back
     * to the Activity.
     */
    public interface TaskCallbacks {
        void onPreExecute();

        void onCancelled();

        void onBuildReady(Build build);
    }

    private TaskCallbacks mCallbacks;
    private GetBuildFromDBTask mTask;
    private boolean mRunning;
    private DataSourceBuilds dataSourceBuilds;

    private long currentBuildID = -1;

    private Build currentBuild;


    /**
     * Hold a reference to the target fragment so we can report the task's current progress and
     * results. We do so in {@link #onAttach(Activity)} since it is guaranteed to be the first
     * method called after a configuration change occurs (remember, the UIFragment will be recreated
     * after each configuration change, so we will need to obtain a reference to the new instance).
     */
    @Override
    public void onAttach(Context context) {
        if (DEBUG) Log.i(TAG, "onAttach(Activity)");
        super.onAttach(context);

        mCallbacks = (TaskCallbacks) context;
        Log.d(TAG, "Attached!");
        //Toast.makeText(context, "Attached!", Toast.LENGTH_SHORT).show();
        start(currentBuildID);
    }

    /**
     * This method is called once when the Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    /**
     * Note that this method is <em>not</em> called when the Fragment is being retained across
     * Activity instances. It will, however, be called when its parent Activity is being destroyed
     * for good (such as when the user clicks the back button, etc.).
     */
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
        cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(EXTRA_CURRENT_BUILD_ID, currentBuildID);
        super.onSaveInstanceState(outState);
    }


    /*****************************/
    /***** TASK FRAGMENT API *****/
    /*****************************/

    /**
     * Start the background task.
     */
    public void start(long buildID) {
        if (currentBuild == null) {
            Log.d(TAG, "No build, going to retrieve from DB in background.");
            if (!mRunning) {
                mTask = new GetBuildFromDBTask();
                mTask.execute(buildID);
                mRunning = true;
            }
        } else /*if (currentBuild.getId() == buildID)*/ {
            Log.d(TAG, "Already have the current build!. no need to retrieve from DB");
            mCallbacks.onBuildReady(currentBuild);
        }

    }

    /**
     * Cancel the background task.
     */
    public void cancel() {
        if (mRunning) {
            mTask.cancel(false);
            mTask = null;
            mRunning = false;
        }
    }

    /**
     * Returns the current state of the background task.
     */
    public boolean isRunning() {
        return mRunning;
    }

    /***************************/
    /***** BACKGROUND TASK *****/
    /***************************/


    private class GetBuildFromDBTask extends AsyncTask<Long, Integer, Build> {

        public GetBuildFromDBTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            // Proxy the call to the Activity.
            mCallbacks.onPreExecute();
            mRunning = true;
        }

        @Override
        protected Build doInBackground(Long... ids) {

            Build currentBuild;

            long buildID = ids[0];

            dataSourceBuilds = new DataSourceBuilds(getActivity());
            dataSourceBuilds.open();
            currentBuild = dataSourceBuilds.getBuild(buildID);
            dataSourceBuilds.close();

            return currentBuild;
        }

        @Override
        protected void onCancelled() {
            // Proxy the call to the Activity.
            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }
            mRunning = false;
        }


        @Override
        protected void onPostExecute(Build build) {
            super.onPostExecute(build);
            //Toast.makeText(getApplicationContext(), "Retrieved skill build from DB", Toast.LENGTH_SHORT).show();
            Log.d("DB", "Retrieved skill build from DB");
            currentBuild = build;
            currentBuildID = currentBuild.getId();
            mRunning = false;
            mCallbacks.onBuildReady(build);
        }


    }


    /************************/
    /***** LOGS & STUFF *****/
    /************************/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (DEBUG) Log.i(TAG, "onActivityCreated(Bundle)");
        if (savedInstanceState != null) {
            currentBuildID = savedInstanceState.getLong(EXTRA_CURRENT_BUILD_ID);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        if (DEBUG) Log.i(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        if (DEBUG) Log.i(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        if (DEBUG) Log.i(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        if (DEBUG) Log.i(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        if (DEBUG) Log.i(TAG, "onDetach()");
        super.onDetach();
        mCallbacks = null;
    }


    public void setCurrentBuildID(long currentBuildID) {
        this.currentBuildID = currentBuildID;
    }


}