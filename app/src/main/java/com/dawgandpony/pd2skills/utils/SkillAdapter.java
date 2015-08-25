package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dawgandpony.pd2skills.R;

/**
 * Created by Jamie on 26/07/2015.
 */
public class SkillAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.cable_guy, R.drawable.cable_guy,
            R.drawable.cable_guy,
    };

    // Constructor
    public SkillAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageButton imageView = new ImageButton(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
            }
        });



        return imageView;
    }
}
