package com.ul.lj.si.vteamtrack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.Training;
import entities.User;
import viewModels.GameModel;
import viewModels.TrainingModel;

public class GameAttendanceAdapter extends ArrayAdapter<User> {

    private List<User> users;
    private Context context;
    private Game game;
    private GameModel gameModel;


    public GameAttendanceAdapter( Context context, Game game, ArrayList<User> users) {
        super(context, 0, users);
        this.context= context;
        this.game = game;
        gameModel = new ViewModelProvider((FragmentActivity) context).get(GameModel.class);

    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_game_attendance, parent, false);
        }
        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.game_attendance_user_name);
        TextView userSurname = (TextView) convertView.findViewById(R.id.game_attendance_user_surname);
        CheckBox attendance = convertView.findViewById(R.id.checkBox_game_attendance);
        boolean checked = game.attendancy.contains(user.id);
        attendance.setChecked(checked);
        // Populate the data into the template view using the data object
        userName.setText(user.firstName);
        userSurname.setText(user.lastName);

        if(PreferenceData.getUserRole(getContext().getApplicationContext()).equals("trainer")){
            attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        game.attendancy.add(user.id);

                    }
                    else {
                        int index = game.attendancy.indexOf(user.id);
                        game.attendancy.remove(index);
                    }
                    gameModel.updateGame(game);
                }
            });
        }
        else {
            attendance.setEnabled(false);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
