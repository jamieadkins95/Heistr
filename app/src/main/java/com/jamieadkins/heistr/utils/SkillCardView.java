package com.jamieadkins.heistr.utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jamieadkins.heistr.R;

/**
 * Card that shows information about skills.
 */
public class SkillCardView extends CardView {
    private TextView mSkillName;
    private TextView mNormalDescription ;
    private TextView mAceDescription;

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
        this.mSkillName = (TextView) findViewById(R.id.tvName);
        this.mNormalDescription = (TextView) findViewById(R.id.tvNormalDescription);
        this.mAceDescription = (TextView) findViewById(R.id.tvAceDescription);
    }
}
