package com.jamieadkins.heistr.Fragments.ViewPagerFragments;


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
import com.jamieadkins.heistr.BuildObjects.WeaponBuild;
import com.jamieadkins.heistr.Fragments.MeleeWeaponFragment;
import com.jamieadkins.heistr.Fragments.WeaponListFragment;
import com.jamieadkins.heistr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains primar, secondary and melee weapon list in tabbed layout.
 */
public class WeaponListParentFragment extends BaseTabFragment {

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
        getActivity().setTitle(getString(R.string.weapons));
    }

    @Override
    protected void setupViewPager(ViewPager viewPager) {
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
}
