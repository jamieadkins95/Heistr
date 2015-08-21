package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.dawgandpony.pd2skills.R;

/**
 * Created by Jamie on 21/08/2015.
 */
public class SkillButton extends Button {

    private static final int[] STATE_NORMAL = {R.attr.state_normal};
    private static final int[] STATE_ACED = {R.attr.state_ace};

    private boolean mIsNormal = false;
    private boolean mIsAced = false;

    public SkillButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNormal(boolean isNormal) {mIsNormal = isNormal;}
    public void setAced(boolean isAced) {mIsAced = isAced;}

    public boolean isNormal() {
        return mIsNormal;
    }

    public boolean isAced() {
        return mIsAced;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (mIsNormal) {
            mergeDrawableStates(drawableState, STATE_NORMAL);
        }
        if (mIsAced) {
            mergeDrawableStates(drawableState, STATE_ACED);
        }
        return drawableState;
    }
}
