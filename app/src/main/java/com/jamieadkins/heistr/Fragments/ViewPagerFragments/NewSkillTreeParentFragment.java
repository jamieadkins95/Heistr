package com.jamieadkins.heistr.Fragments.ViewPagerFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jamieadkins.heistr.Activities.EditBuildActivity;
import com.jamieadkins.heistr.Consts.Trees;
import com.jamieadkins.heistr.Fragments.BlankFragment;
import com.jamieadkins.heistr.Fragments.NewSkillTreeFragment;
import com.jamieadkins.heistr.Fragments.SkillTreeFragment;
import com.jamieadkins.heistr.R;

/**
 * Contains tabed layout for breakdown of new skill trees
 */
public class NewSkillTreeParentFragment extends BaseTabFragment {
    private static final String TREE = "Tree";
    private static final String SUB_TREE_INDEX = "SubTreeInd";

    private int mTree = Trees.MASTERMIND;

    public static NewSkillTreeParentFragment newInstance(int currentTree, int currentSubTree) {
        NewSkillTreeParentFragment fragment = new NewSkillTreeParentFragment();
        Bundle args = new Bundle();
        args.putInt(TREE, currentTree);
        args.putInt(SUB_TREE_INDEX, currentSubTree);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTree = getArguments().getInt(TREE);
        getActivity().setTitle(getResources().getStringArray(R.array.skill_trees)
                [mTree]);
    }

    @Override
    protected void setupViewPager(ViewPager viewPager) {
        String[] subTreeNames;
        switch (mTree) {
            case Trees.MASTERMIND:
                subTreeNames = getResources().getStringArray(R.array.mastermind_sub_trees);
                break;
            case Trees.ENFORCER:
                subTreeNames = getResources().getStringArray(R.array.enforcer_sub_trees);
                break;
            case Trees.TECHNICIAN:
                subTreeNames = getResources().getStringArray(R.array.technician_sub_trees);
                break;
            case Trees.GHOST:
                subTreeNames = getResources().getStringArray(R.array.ghost_sub_trees);
                break;
            case Trees.FUGITIVE:
                subTreeNames = getResources().getStringArray(R.array.fugitive_sub_trees);
                break;
            default:
                subTreeNames = getResources().getStringArray(R.array.mastermind_sub_trees);
                break;
        }
        for (int i = 0; i < Trees.SUBTREES_PER_TREE; i++) {
            String title = subTreeNames[i];
            mAdapter.addFragment(NewSkillTreeFragment.newInstance(title), title);
        }

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(getArguments().getInt(SUB_TREE_INDEX));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activity.updateCurrentSkillTreeIndex(mTree, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
