package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.Training;
import entities.User;
import viewModels.GameModel;
import viewModels.TrainingModel;

public class GameAttendanceAdapter extends RecyclerView.Adapter<GameAttendanceAdapter.ViewHolder> {

    private List<User> users;
    private Activity activity;
    private Game game;
    private GameModel gameModel;



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView userSurname;
        private CheckBox attendance;

        public ViewHolder(View itemView) {

            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.game_attendance_user_name);
            userSurname = (TextView) itemView.findViewById(R.id.game_attendance_user_surname);
            attendance = itemView.findViewById(R.id.checkBox_game_attendance);

        }

    }
    public GameAttendanceAdapter( List<User> users, Activity activity, Game game) {

        this.activity = activity;
        this.game = game;
        this.users=users;
        gameModel = new ViewModelProvider((FragmentActivity) activity).get(GameModel.class);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        gameModel = new ViewModelProvider((FragmentActivity) activity).get(GameModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_game_attendance, parent, false);

        // Return a new holder instance
        GameAttendanceAdapter.ViewHolder viewHolder = new GameAttendanceAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         User user = users.get(position);
         TextView userName = holder.userName;
         TextView userSurname = holder.userSurname;
         CheckBox attendance = holder.attendance;

         userName.setText(user.getFirstName());
         userSurname.setText(user.getLastName());
         boolean checked = game.getAttendance().contains(user.getId());
         attendance.setChecked(checked);

        if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("trainer")){
            attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        game.getAttendance().add(user.getId());

                    }
                    else {
                        int index = game.getAttendance().indexOf(user.getId());
                        game.getAttendance().remove(index);
                    }
                    gameModel.updateGame(game);
                }
            });
        }
        else {
            attendance.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
