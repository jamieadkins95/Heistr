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
import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.Fragments.SkillTreeFragment;
import com.jamieadkins.heistr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains Skill Trees in a tabbed layout.
 */
public class SkillTreeParentFragment extends BaseTabFragment {
    public static SkillTreeParentFragment newInstance(int currentTree) {
        SkillTreeParentFragment fragment = new SkillTreeParentFragment();
        Bundle args = new Bundle();
        args.putInt(EditBuildActivity.SKILL_TREE_INDEX, currentTree);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(getString(R.string.skill_trees));
    }

    @Override
    protected void setupViewPager(ViewPager viewPager) {
        for (int i = Trees.MASTERMIND; i <= Trees.FUGITIVE; i++) {
            String title = getResources().getStringArray(R.array.skill_trees)[i];
            mAdapter.addFragment(SkillTreeFragment.newInstance(i), title);
        }

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(getArguments().getInt(EditBuildActivity.SKILL_TREE_INDEX));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activity.updateCurrentSkillTree(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
