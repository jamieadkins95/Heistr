package com.jamieadkins.heistr.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamieadkins.heistr.Activities.EditBuildActivity;
import com.jamieadkins.heistr.BuildObjects.Weapon;
import com.jamieadkins.heistr.BuildObjects.WeaponBuild;
import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 13/10/2015.
 */
public class WeaponListParentFragment extends Fragment {

    Adapter mAdapter;
    EditBuildActivity activity;

    public static WeaponListParentFragment newInstance(int currentWeaponList) {
        WeaponListParentFragment fragment = new WeaponListParentFragment();
        Bundle args = new Bundle();
        args.putInt(EditBuildActivity.WEAPON_LIST_INDEX, currentWeaponList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (EditBuildActivity) getActivity();
        getActivity().setTitle(getString(R.string.weapons));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Adapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_with_viewpager, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        String title = getResources().getString(R.string.primary);
        mAdapter.addFragment(WeaponListFragment.newInstance(WeaponBuild.PRIMARY), title);

        title = getResources().getString(R.string.secondary);
        mAdapter.addFragment(WeaponListFragment.newInstance(WeaponBuild.SECONDARY), title);

        title = getResources().getString(R.string.melee);
        mAdapter.addFragment(MeleeWeaponFragment.newInstance(), title);

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(getArguments().getInt(EditBuildActivity.WEAPON_LIST_INDEX));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activity.updateCurrentWeaponList(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
