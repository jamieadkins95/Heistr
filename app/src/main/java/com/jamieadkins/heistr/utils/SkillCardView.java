package com.jamieadkins.heistr.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamieadkins.heistr.BuildObjects.Skill;
import com.jamieadkins.heistr.R;

/**
 * Card that shows information about skills.
 */
public class SkillCardView extends CardView {
    private TextView mSkillName;
    private TextView mNormalDescription ;
    private TextView mAceDescription;

    private int mTaken = Skill.NO;

    public SkillCardView(Context context) {
        super(context);
        initialiseViews();
    }

    public SkillCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialiseViews();
    }

    public SkillCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialiseViews();
    }

    private void initialiseViews() {
        inflate(getContext(), R.layout.skill_button, this);
        this.setCardElevation(4.0f);
        this.setClickable(true);

        this.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.selector_build_list));
        this.mSkillName = (TextView) findViewById(R.id.tvName);
        this.mNormalDescription = (TextView) findViewById(R.id.tvNormalDescription);
        this.mAceDescription = (TextView) findViewById(R.id.tvAceDescription);
    }

    public void setSkillStatus(int taken) {
        mTaken = taken;
        int colour;
        switch (mTaken) {
            case Skill.NO:
                colour = ContextCompat.getColor(getContext(), R.color.textHint);
                break;
            case Skill.NORMAL:
                colour = ContextCompat.getColor(getContext(), R.color.textPrimary);
                break;
            case Skill.ACE:
                colour = ContextCompat.getColor(getContext(), R.color.primary);
                break;
            default:
                colour = ContextCompat.getColor(getContext(), R.color.textHint);
                break;
        }

        mSkillName.setTextColor(colour);
    }

    public void setSkillStatus(boolean unlocked) {
        int colour;
        if (unlocked) {
            colour = ContextCompat.getColor(getContext(), R.color.backgroundCard);
        } else {
            colour = ContextCompat.getColor(getContext(), R.color.backgroundVeryDark);
        }
        setBackgroundColor(colour);
    }

    public void onSkillTaken() {
        int colour;
        switch (mTaken) {
            case Skill.NO:
                //Set to normal
                mTaken = Skill.NORMAL;
                colour = ContextCompat.getColor(getContext(), R.color.textPrimary);
                break;
            case Skill.NORMAL:
                //Set to Ace
                mTaken = Skill.ACE;
                colour = ContextCompat.getColor(getContext(), R.color.primary);
                break;
            case Skill.ACE:
                //Set to none
                mTaken = Skill.NO;
                colour = ContextCompat.getColor(getContext(), R.color.textHint);
                break;
            default:
                mTaken = Skill.NO;
                colour = ContextCompat.getColor(getContext(), R.color.textHint);
                break;
        }

        mSkillName.setTextColor(colour);
    }

    public void setSkillName(String name) {
        mSkillName.setText(name);
    }

    public void setNormalDescription(String description) {
        mNormalDescription.setText(getContext().getString(R.string.normal, description));
    }

    public void setAceDescription(String description) {
        mAceDescription.setText(getContext().getString(R.string.ace, description));
    }
}
