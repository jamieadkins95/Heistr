package com.dawgandpony.pd2skills.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dawgandpony.pd2skills.Activities.BuildListActivity;
import com.dawgandpony.pd2skills.Activities.EditBuildActivity;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.utils.RVBuildListAdapter;
import com.dawgandpony.pd2skills.utils.RecyclerViewBuildList;

import java.util.ArrayList;

/**
 * A fragment containing the list of skill builds
 */
public class BuildListFragment extends Fragment {

    public final static String EXTRA_BUILD_ID = "com.dawgandpony.pd2skills.BUILDID";

    RecyclerViewBuildList rvBuilds;
    ListView lvBuilds;
    CardView cv;
    FloatingActionButton fab;

    public BuildListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_build_list, container, false);
        this.rvBuilds = (RecyclerViewBuildList) rootView.findViewById(R.id.rvBuilds);
        this.lvBuilds = (ListView) rootView.findViewById(R.id.lvBuilds);

        this.rvBuilds.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.rvBuilds.setEmptyView(rootView.findViewById(R.id.emptyElement));


        new GetBuildsFromDBTask(rvBuilds, lvBuilds).execute();

        fab = (FloatingActionButton) rootView.findViewById(R.id.fabNewBuild);

        //Weird workaround to get the colour of the FAB correct
        ColorStateList csl = new ColorStateList(
                new int[][] {{android.R.attr.state_pressed},{}},
                new int[] {Color.rgb(187, 222, 251), Color.rgb(33,150,243)});
        fab.setBackgroundTintList(csl);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go to the build edit activity with a new build
                Intent intent = new Intent(getActivity(), EditBuildActivity.class);
                intent.putExtra(EXTRA_BUILD_ID, Long.toString(SkillBuild.NEW_SKILL_BUILD));
                startActivity(intent);


            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetBuildsFromDBTask(rvBuilds, lvBuilds).execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle(getString(R.string.menu_build_list_title));
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_build_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        RecyclerViewBuildList info = (RecyclerViewBuildList) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_delete:

                Toast.makeText(getActivity(), "Coming Soon!", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private class GetBuildsFromDBTask extends AsyncTask<Void, Integer, ArrayList<Build>> {

        DataSourceBuilds dataSourceBuilds;
        RecyclerViewBuildList recyclerViewBuilds;
        ListView listViewBuilds;

        public GetBuildsFromDBTask(RecyclerViewBuildList rv, ListView lv) {
            super();
            recyclerViewBuilds = rv;
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
            ArrayList<String> testStrings = new ArrayList<>();
            for (Build b : builds){
                testStrings.add(b.getSkillBuild() + "");
            }

            RVBuildListAdapter mAdapter = new RVBuildListAdapter(builds);

            recyclerViewBuilds.setAdapter(mAdapter);
            registerForContextMenu(recyclerViewBuilds);

            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, testStrings);

            listViewBuilds.setAdapter(itemsAdapter);
            registerForContextMenu(listViewBuilds);
        }
    }
}
