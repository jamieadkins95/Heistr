package com.dawgandpony.pd2skills.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.Dialogs.NewBuildDialog;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.ArrayAdapterBuildList;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * A fragment containing the list of skill builds
 */
public class BuildListFragment extends Fragment implements NewBuildDialog.NewBuildDialogListener{

    public final static String EXTRA_BUILD_ID = "com.dawgandpony.pd2skills.BUILDID";
    public final static String EXTRA_BUILD_NAME = "com.dawgandpony.pd2skills.BUILDNAME";
    public final static String EXTRA_BUILD_INFAMIES = "com.dawgandpony.pd2skills.INFAMIES";
    public final static String EXTRA_BUILD_URL = "com.dawgandpony.pd2skills.URL";
    public final static String EXTRA_BUILD_TEMPLATE = "com.dawgandpony.pd2skills.TEMPLATE";

    ListView lvBuilds;
    ArrayList<Build> buildList;
    CardView cv;

    public static final int DIALOG_FRAGMENT = 1;


    public BuildListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_build_list, container, false);

        this.lvBuilds = (ListView) rootView.findViewById(R.id.lvBuilds);
        this.lvBuilds.setEmptyView(rootView.findViewById(R.id.emptyElement));
        this.lvBuilds.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabNewBuild);
        fab.attachToListView(lvBuilds);


        this.lvBuilds.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(lvBuilds.getCheckedItemCount() + " selected builds");

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_build_list_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        DeleteBuilds();
                        mode.finish();
                        return true;
                    default:
                        RenameBuilds();
                        mode.finish();
                        return false;
                }

            }

            private void DeleteBuilds() {
                SparseBooleanArray checked = lvBuilds.getCheckedItemPositions();

                for (int i = 0; i < checked.size();i++){
                    if (checked.valueAt(i)){
                        Build selectedBuild = (Build) lvBuilds.getItemAtPosition(checked.keyAt(i));
                        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(getActivity());
                        dataSourceBuilds.open();
                        dataSourceBuilds.DeleteBuild(selectedBuild.getId());
                        dataSourceBuilds.close();

                        new GetBuildsFromDBTask(lvBuilds).execute();


                        Log.d("Context Action", "Delete build " + selectedBuild.getSkillBuildID());

                        fab.show();

                    }
                }

                Toast.makeText(getActivity(), "Build(s) deleted", Toast.LENGTH_SHORT).show();
            }

            private void RenameBuilds() {
                SparseBooleanArray checked = lvBuilds.getCheckedItemPositions();

                String newName = "new name";

                for (int i = 0; i < checked.size();i++){
                    if (checked.valueAt(i)){
                        Build selectedBuild = (Build) lvBuilds.getItemAtPosition(checked.keyAt(i));
                        DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(getActivity());
                        dataSourceBuilds.open();
                        dataSourceBuilds.RenameBuild(selectedBuild.getId(), newName);
                        dataSourceBuilds.close();

                        new GetBuildsFromDBTask(lvBuilds).execute();




                        fab.show();

                    }


                }

                Toast.makeText(getActivity(), "Build(s) renamed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        this.lvBuilds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Build selectedBuild = (Build) lvBuilds.getItemAtPosition(position);
                MoveToEditBuildActivity(selectedBuild.getId());
            }
        });


        new GetBuildsFromDBTask(lvBuilds).execute();

        //fab = (FloatingActionButton) rootView.findViewById(R.id.fabNewBuild);

        //Weird workaround to get the colour of the FAB correct
        ColorStateList csl = new ColorStateList(
                new int[][] {{android.R.attr.state_pressed},{}},
                new int[] {Color.rgb(187, 222, 251), Color.rgb(33,150,243)});
        //fab.setBackgroundTintList(csl);
        //region FAB onClick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (buildList != null){
                    // Create an instance of the dialog fragment and show it
                    DialogFragment dialog = NewBuildDialog.newInstance(buildList);
                    dialog.setTargetFragment(BuildListFragment.this, DIALOG_FRAGMENT);
                    dialog.show(getActivity().getFragmentManager(), "NewBuildDialogFragment");
                }



            }
        });
        //endregion

        return rootView;
    }


    private void MoveToEditBuildActivity(long id){

        for (int i = 0; i < lvBuilds.getAdapter().getCount(); i++){
            lvBuilds.setItemChecked(i, false);
        }
        Intent intent = new Intent(getActivity(), EditBuildActivity.class);
        intent.putExtra(EXTRA_BUILD_ID, id);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetBuildsFromDBTask(lvBuilds).execute();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, int infamies, String pd2SkillsURL, int templateBuildPos) {
        long templateBuildID = -1;
        if (templateBuildPos > -1){
            templateBuildID = buildList.get(templateBuildPos).getId();
        }


        //Toast.makeText(getActivity(),name + " - " + infamies + " - " + templateBuildID,Toast.LENGTH_LONG).show();
        //MoveToEditBuildActivity(name, infamies, pd2SkillsURL, templateBuildID);
        new CreateNewBuild(name, infamies, pd2SkillsURL, templateBuildID).execute();
    }

    private class CreateNewBuild extends AsyncTask<Void, Void, Build>{

        String name;
        int infamies;
        String url;
        long templateID;

        public CreateNewBuild(String name, int infamies, String pd2SkillsURL, long templateBuildID) {
            super();
            this.name=  name;
            this.infamies = infamies;
            this.url = pd2SkillsURL;
            this.templateID = templateBuildID;
        }

        @Override
        protected Build doInBackground(Void... params) {
            DataSourceBuilds dataSourceBuilds = new DataSourceBuilds(getActivity());
            dataSourceBuilds.open();
            Build b = dataSourceBuilds.createAndInsertBuild(name, infamies, url, templateID);
            dataSourceBuilds.close();

            return b;
        }

        @Override
        protected void onPostExecute(Build build) {
            super.onPostExecute(build);

            MoveToEditBuildActivity(build.getId());


        }
    }


    private class GetBuildsFromDBTask extends AsyncTask<Void, Integer, ArrayList<Build>> {

        DataSourceBuilds dataSourceBuilds;

        ListView listViewBuilds;

        public GetBuildsFromDBTask(ListView lv) {
            super();

            listViewBuilds = lv;

        }

        @Override
        protected ArrayList<Build> doInBackground(Void... params) {

            ArrayList<Build> builds;

            //Get list of skill builds from database.
            dataSourceBuilds = new DataSourceBuilds(getActivity());
            dataSourceBuilds.open();
            builds = dataSourceBuilds.getAllBuilds();
            dataSourceBuilds.close();


            return builds;
        }

        @Override
        protected void onPostExecute(ArrayList<Build> builds) {
            super.onPostExecute(builds);



            buildList = builds;
            ArrayAdapterBuildList itemsAdapter =
                    new ArrayAdapterBuildList(getActivity(), builds);

            listViewBuilds.setAdapter(itemsAdapter);

        }
    }
}
