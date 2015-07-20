package com.dawgandpony.pd2skills.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceBuilds;
import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.utils.RVBuildListAdapter;
import com.dawgandpony.pd2skills.utils.RecyclerViewEmptySupport;

import java.util.ArrayList;


public class BuildListActivity extends AppCompatActivity {


    public final static String EXTRA_BUILD_ID = "com.dawgandpony.pd2skills.BUILDID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new BuildListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_build_list, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings){
            Toast.makeText(this, "LOL, no settings!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A fragment containing the list of skill builds
     */
    public static class BuildListFragment extends Fragment {

        RecyclerViewEmptySupport rvBuilds;
        CardView cv;
        FloatingActionButton fab;

        public BuildListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_build_list, container, false);
            this.rvBuilds = (RecyclerViewEmptySupport) rootView.findViewById(R.id.rvBuilds);
            this.rvBuilds.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            this.rvBuilds.setEmptyView(rootView.findViewById(R.id.emptyElement));


            new GetBuildsFromDBTask(rvBuilds).execute();

            fab = (FloatingActionButton) rootView.findViewById(R.id.fabNewBuild);

            //Weird workaround to get the colour of the FAB correct
            ColorStateList csl = new ColorStateList(
                    new int[][] {{android.R.attr.state_pressed},{}},
                    new int[] {Color.rgb(187,222,251), Color.rgb(33,150,243)});
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
            new GetBuildsFromDBTask(rvBuilds).execute();
        }

        private class GetBuildsFromDBTask extends AsyncTask<Void, Integer, ArrayList<Build>>{

            DataSourceBuilds dataSourceBuilds;
            RecyclerViewEmptySupport recyclerViewBuilds;

            public GetBuildsFromDBTask(RecyclerViewEmptySupport rv) {
                super();
                recyclerViewBuilds = rv;

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

                RVBuildListAdapter mAdapter = new RVBuildListAdapter(builds);
                recyclerViewBuilds.setAdapter(mAdapter);
            }
        }
    }
}
