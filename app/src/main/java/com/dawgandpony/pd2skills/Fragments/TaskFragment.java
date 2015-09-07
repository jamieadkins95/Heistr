package com.dawgandpony.pd2skills.Fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;

/**
 * TaskFragment manages a single background task and retains itself across
 * configuration changes.
 */
public class TaskFragment extends Fragment {
    private static final String TAG = TaskFragment.class.getSimpleName();
    private static final boolean DEBUG = true; // Set this to false to disable logs.

    /**
     * Callback interface through which the fragment can report the task's
     * progress and results back to the Activity.
     */
    public static interface TaskCallbacks {
        void onPreExecute();
        void onCancelled();
        void onPostExecute(Build build);
    }

    private TaskCallbacks mCallbacks;
    private GetBuildFromDBTask mTask;
    private boolean mRunning;
    private DataSourceBuilds dataSourceBuilds;

    private long currentBuildID = -1;
    private String newBuildName = "Something Went Wrong :(";
    private int infamies = 0;
    private String pd2URL = "";
    private long templateBuildID = -1;

    private Build currentBuild;



    /**
     * Hold a reference to the target fragment so we can report the task's current
     * progress and results. We do so in {@link #onAttach(Activity)} since it is
     * guaranteed to be the first method called after a configuration change
     * occurs (remember, the UIFragment will be recreated after each configuration
     * change, so we will need to obtain a reference to the new instance).
     */
    @Override
    public void onAttach(Context context) {
        if (DEBUG) Log.i(TAG, "onAttach(Activity)");
        super.onAttach(context);

        mCallbacks = (TaskCallbacks) context;
        Log.d(TAG, "Attached!");
        //Toast.makeText(context, "Attached!", Toast.LENGTH_SHORT).show();
        start(currentBuildID, newBuildName, infamies, pd2URL, templateBuildID);
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
     * Note that this method is <em>not</em> called when the Fragment is being
     * retained across Activity instances. It will, however, be called when its
     * parent Activity is being destroyed for good (such as when the user clicks
     * the back button, etc.).
     */
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
        cancel();
    }




    /*****************************/
    /***** TASK FRAGMENT API *****/
    /*****************************/

    /**
     * Start the background task.
     */
    public void start(long buildID, String newBuildName, int infamies, String url, long templateBuildID) {
        if (currentBuild == null){
            Log.d(TAG, "No build, going to retrieve from DB in background.");

            //Toast.makeText(getActivity(), "No current build!", Toast.LENGTH_SHORT).show();
            if (!mRunning) {
                mTask = new GetBuildFromDBTask(newBuildName, infamies, url, templateBuildID);
                mTask.execute(buildID);
                mRunning = true;
            }
        }
        else /*if (currentBuild.getId() == buildID)*/{
            //Toast.makeText(getActivity(), "Already got the current build!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Already have the current build!. no need to retrieve from DB");
            mCallbacks.onPostExecute(currentBuild);
        }
        /*else {
            Log.d(TAG, "Already have the current build!. no need to retrieve from DB");
            Toast.makeText(getActivity(), "Already got a different build!", Toast.LENGTH_SHORT).show();
        }*/

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

        String name;
        int infamies;
        String url;
        long template;

        public GetBuildFromDBTask(String name, int infamies, String url, long template) {
            super();
            this.name = name;
            this.infamies = infamies;
            this.url = url;
            this.template = template;
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
            if (buildID == Build.NEW_BUILD){
                currentBuild = dataSourceBuilds.createAndInsertBuild(name, infamies, url, template);
            }
            else {
                currentBuild = dataSourceBuilds.getBuild(buildID);
            }
            dataSourceBuilds.close();

            return currentBuild;
        }

        @Override
        protected void onCancelled() {
            // Proxy the call to the Activity.
            mCallbacks.onCancelled();
            mRunning = false;
        }


        @Override
        protected void onPostExecute(Build build) {
            super.onPostExecute(build);
            //Toast.makeText(getApplicationContext(), "Retrieved skill build from DB", Toast.LENGTH_SHORT).show();
            Log.d("DB", "Retrieved skill build from DB");
            currentBuild = build;
            currentBuildID = currentBuild.getId();
            mCallbacks.onPostExecute(build);
            mRunning = false;
        }


    }

    /************************/
    /***** LOGS & STUFF *****/
    /************************/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (DEBUG) Log.i(TAG, "onActivityCreated(Bundle)");
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

    public void setNewBuildName(String newBuildName) {
        this.newBuildName = newBuildName;
    }

    public void setCurrentBuildID(long currentBuildID) {
        this.currentBuildID = currentBuildID;
    }

    public void setInfamies(int infamies) {
        this.infamies = infamies;
    }

    public void setPd2URL(String pd2URL) {
        this.pd2URL = pd2URL;
    }

    public void setTemplateBuildID(long templateBuildID) {
        this.templateBuildID = templateBuildID;
    }
}