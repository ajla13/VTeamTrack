package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.MainActivity;
import com.ul.lj.si.vteamtrack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Fee;
import entities.Game;
import entities.User;
import viewModels.FeeModel;
import viewModels.GameModel;
import viewModels.TrainingModel;
import viewModels.UserModel;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private Activity activity;

    private List<User> users;

    private UserModel userModel;
    private FeeModel feeModel;
    SimpleDateFormat sdf;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView userSurname;
        private TextView email;
        private TextView phone;
        private TextView dateOfBirth;
        private ImageButton expand;
        private Button accept;
        private Button decline;
        private LinearLayout toggleLayout;

        public ViewHolder(View itemView) {

            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.item_req_name);
            userSurname = (TextView) itemView.findViewById(R.id.item_req_surname);
            email = (TextView) itemView.findViewById(R.id.item_req_email);
            phone = (TextView) itemView.findViewById(R.id.item_req_phone);
            dateOfBirth = (TextView) itemView.findViewById(R.id.item_req_dateOfBirth);
            expand = (ImageButton) itemView.findViewById(R.id.btn_expand_req);
            accept = (Button) itemView.findViewById(R.id.btn_req_accept);
            decline = (Button) itemView.findViewById(R.id.btn_req_decline);
            toggleLayout = itemView.findViewById(R.id.item_req_secondlayout);

        }
    }

    public RequestsAdapter(List<User> users, Activity activity) {
        this.activity = activity;
        this.users = users;
    }

    @NonNull
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        userModel = new ViewModelProvider((FragmentActivity) activity).get(UserModel.class);
        feeModel = new ViewModelProvider((FragmentActivity) activity).get(FeeModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.request_item, parent, false);

        // Return a new holder instance
        RequestsAdapter.ViewHolder viewHolder = new RequestsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder holder, int position) {

        User user = users.get(position);
        TextView userName = holder.userName;
        TextView userSurname  = holder.userSurname;
        TextView email = holder.email;
        TextView phone = holder.phone;
        TextView dateOfBirth = holder.dateOfBirth;
        Button accept = holder.accept;
        Button decline = holder.decline;
        ImageButton expand = holder.expand;
        LinearLayout toggleLayout = holder.toggleLayout;

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(user.getDateOfBirth().getTime());
        dateOfBirth.setText(sdf.format(utilDate));

        userName.setText(user.getFirstName());
        userSurname.setText(user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setRegistrationConfirmed(true);
                try {
                    Fee userFee = new Fee("january", user.getId(), false, user.getTeamName(), "10", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                userModel.update(user);
                Toast.makeText(activity.getApplicationContext(),
                        "User registration accepted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModel.deleteUser(user);
                Toast.makeText(activity.getApplicationContext(),
                        "User registration declined", Toast.LENGTH_LONG).show();

            }
        });
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float deg = expand.getRotation() + 180F;
                expand.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

                if(toggleLayout.getVisibility()==View.GONE){
                    toggleLayout.setVisibility(View.VISIBLE);
                    dateOfBirth.setVisibility((dateOfBirth.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    email.setVisibility((email.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    phone.setVisibility((phone.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                }
                else {
                    dateOfBirth.setVisibility((dateOfBirth.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    email.setVisibility((email.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    phone.setVisibility((phone.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                    toggleLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
