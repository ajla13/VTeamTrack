package com.ul.lj.si.vteamtrack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ul.lj.si.vteamtrack.R;

import java.util.ArrayList;

import entities.Team;

public class SpinnerAdapter extends ArrayAdapter<Team> {
    public SpinnerAdapter(Context context, ArrayList<Team> teams) {
        super(context, 0, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
        }
        // Lookup view for data population
        TextView teamName = (TextView) convertView.findViewById(R.id.item_spinner_team);
        // Populate the data into the template view using the data object
        teamName.setText(team.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}