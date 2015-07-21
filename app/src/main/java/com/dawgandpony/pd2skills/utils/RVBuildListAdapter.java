package com.dawgandpony.pd2skills.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dawgandpony.pd2skills.R;
import com.dawgandpony.pd2skills.BuildObjects.Build;

import java.util.List;

/**
 * Created by Jamie on 14/07/2015.
 */
public class RVBuildListAdapter extends RecyclerView.Adapter<RVBuildListAdapter.BuildViewHolder>{


    List<Build> builds;

    public RVBuildListAdapter(List<Build> b){
        this.builds = b;
    }



    public static class BuildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView buildName;
        TextView primaryWeapon;
        TextView pointsUsed;
        View mView;



        BuildViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            cv = (CardView)itemView.findViewById(R.id.cvBuilds);
            buildName = (TextView)itemView.findViewById(R.id.tvName);
            primaryWeapon = (TextView)itemView.findViewById(R.id.tvPrimaryWeapon);
            pointsUsed = (TextView)itemView.findViewById(R.id.tvPointsUsed);
        }

        private void onLongClick() {
            mView.showContextMenu();
        }

        @Override
        public void onClick(View v) {

        }
    }





    @Override
    public BuildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.build_card_view, parent, false);
        BuildViewHolder buildViewHoldervh = new BuildViewHolder(v);
        return buildViewHoldervh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(BuildViewHolder holder, int position) {
        holder.buildName.setText(builds.get(position).getName());
        //holder.pointsUsed.setText(builds.get(position).getPointsUsed() + "");
        //holder.primaryWeapon.setText(builds.get(position).getPrimaryWeapon());
    }

    @Override
    public int getItemCount() {
        return builds.size();
    }




}
