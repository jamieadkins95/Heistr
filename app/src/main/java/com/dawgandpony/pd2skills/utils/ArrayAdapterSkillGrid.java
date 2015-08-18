package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class ArrayAdapterSkillGrid extends ArrayAdapter<Skill>{


    List<Skill> skills;
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.grid_item_skill, parent, false);
        Button skillName = (Button) rowView.findViewById(R.id.btnSkillName);

        //skillName.setText(skills.get(position).getName().charAt(0));

        return rowView;
    }

    public ArrayAdapterSkillGrid(Context context, SkillTree tree){
        super(context, -1, tree.getSkillsInListForm());
        this.skills = tree.getSkillsInListForm();
        this.context = context;
    }



}
