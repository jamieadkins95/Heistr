package com.dawgandpony.pd2skills.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.Database.DataSourceWeapons;
import com.dawgandpony.pd2skills.Database.MySQLiteHelper;
import com.dawgandpony.pd2skills.Fragments.BlankFragment;
import com.dawgandpony.pd2skills.Fragments.WeaponListFragment;
import com.dawgandpony.pd2skills.R;

/**
 * Created by Jamie on 11/10/2015.
 */
public class EditWeaponActivity extends AppCompatActivity {

    public static final String EXTRA_WEAPON_TYPE = "WeaponType";
    Weapon currentWeapon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weapon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_weapon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = this.getIntent();
                intent.putExtra(WeaponListFragment.EXTRA_WEAPON_ID, -2);
                intent.putExtra(EXTRA_WEAPON_TYPE, 0);
                this.setResult(RESULT_CANCELED, intent);
                finish();
                return true;
            case R.id.action_equip:
                Intent intent2 = this.getIntent();
                intent2.putExtra(WeaponListFragment.EXTRA_WEAPON_ID, -2);
                this.setResult(RESULT_OK, intent2);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        
        adapter.addFragment(new BlankFragment(), "Overview");

        String[] attachment_types = getResources().getStringArray(R.array.attachment_types);
        for (int i = 0; i < MySQLiteHelper.COLUMNS_ATTACHMENTS.length; i++){
            adapter.addFragment(new BlankFragment(), attachment_types[i]);
        }

        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
