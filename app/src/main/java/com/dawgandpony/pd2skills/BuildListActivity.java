package com.dawgandpony.pd2skills;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListView;

import com.dawgandpony.pd2skills.utils.RVAdapter;
import com.dawgandpony.pd2skills.utils.RecyclerViewEmptySupport;

import java.util.ArrayList;


public class BuildListActivity extends ActionBarActivity {

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A fragment containing the list of skill builds
     */
    public static class BuildListFragment extends Fragment {

        RecyclerViewEmptySupport rvBuilds;
        CardView cv;

        public BuildListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_build_list, container, false);

            RVAdapter mAdapter = new RVAdapter(SkillBuild.exampleData());


            this.rvBuilds = (RecyclerViewEmptySupport) rootView.findViewById(R.id.rvBuilds);
            this.rvBuilds.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            this.rvBuilds.setEmptyView(rootView.findViewById(R.id.emptyElement));
            this.rvBuilds.setAdapter(mAdapter);
            return rootView;
        }
    }
}
