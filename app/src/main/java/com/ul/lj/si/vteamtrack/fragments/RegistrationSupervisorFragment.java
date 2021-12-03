package com.ul.lj.si.vteamtrack.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.Login;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.RegisterActivity;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.Fee;
import entities.FeeMonth;
import entities.Team;
import entities.User;
import viewModels.TeamModel;
import viewModels.UserModel;

public class RegistrationSupervisorFragment extends Fragment {
    private EditText teamName;
    private EditText name;
    private EditText surname;
    private EditText supervisorEmail;
    private EditText playerEmail;
    private EditText password;
    private EditText phone;
    private EditText repeatPass;
    private Button register;
    private String errorText;
    private TeamModel teamModel;
    private UserModel userModel;
    private User supervisorUser;
    private User playerUser;
    private String successText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.registration_supervisor, container, false);
        teamName = view.findViewById(R.id.registration_team_supervisor);
        name = view.findViewById(R.id.registration_name_supervisor);
        surname = view.findViewById(R.id.registration_surname_supervisor);
        supervisorEmail = view.findViewById(R.id.registration_email_supervisor_main);
        playerEmail = view.findViewById(R.id.registration_email_supervisor_player);
        password = view.findViewById(R.id.registration_password_supervisor);
        repeatPass = view.findViewById(R.id.registration_password_repeat_supervisor);
        phone = view.findViewById(R.id.registration_phone_supervisor);
        register = view.findViewById(R.id.btn_register_supervisor);

        teamModel = new ViewModelProvider((RegisterActivity) getActivity()).get(TeamModel.class);
        userModel = new ViewModelProvider((RegisterActivity) getActivity()).get(UserModel.class);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    if(creditentials()){
                        String firstName = name.getText().toString();
                        String lastname =  surname.getText().toString();
                        String teamNameDb = teamName.getText().toString();
                        String supervisorEmailDb = supervisorEmail.getText().toString();
                        String playerEmailDb = playerEmail.getText().toString();
                        String pass = password.getText().toString();
                        String phoneDb = phone.getText().toString();

                        Team teamWithId = teamModel.getTeam(teamNameDb);
                        User user = new User(null,teamWithId.getId(),firstName, lastname,null, teamNameDb, supervisorEmailDb,
                                playerEmailDb,pass, "supervisor",phoneDb,false,null);

                            User createdUser = userModel.createUser(user);

                        successText="Your registration request has been sent.";
                        }

                        Toast.makeText(getActivity().getApplicationContext(),
                                successText, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorText, Toast.LENGTH_LONG).show();
                    }
                }
            });

        return view;
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean dataValidation(){
        boolean returnVaue=true;
        if(isEmpty(teamName)){
            teamName.setError("Name of the team can not be empty");
            returnVaue=false;
        }
        if(isEmpty(password)){
            password.setError("Password can not be empty");
            returnVaue=false;
        }
        if(isEmpty(repeatPass) || !repeatPass.getText().toString().equals(password.getText().toString())){
            repeatPass.setError("Passwords do not match");
            returnVaue=false;
        }
        if(isEmpty(supervisorEmail)){
            supervisorEmail.setError("Email can not be empty");
            returnVaue=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(supervisorEmail.getText()).matches()){
            supervisorEmail.setError("Please enter a valid email");
            returnVaue=false;
        }
        if(isEmpty(playerEmail)){
            playerEmail.setError("Email can not be empty");
            returnVaue=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(playerEmail.getText()).matches()){
            playerEmail.setError("Please enter a valid email");
            returnVaue=false;
        }
        if(isEmpty(name)){
            name.setError("Name can not be empty");
            returnVaue=false;
        }
        if(isEmpty(surname)){
            surname.setError("Surname can not be empty");
            returnVaue=false;
        }
        if(isEmpty(phone)){
            phone.setError("Phone number can not be empty");
            returnVaue=false;
        }

        return returnVaue;
    }


    boolean creditentials(){
        Team team = teamModel.getTeam(teamName.getText().toString());

            if(team==null){
                errorText="A team under that name is not registered.";
                return false;

            }
            supervisorUser = userModel.checkUserCred(supervisorEmail.getText().toString(),teamName.getText().toString());
            if(supervisorUser!=null){
                if(supervisorUser.isRegistrationConfirmed()) {
                    errorText = "A user with that email already exists.";
                }
                else {
                    errorText="A registration request with that email already exists.";
                }
                return false;
            }

        playerUser = userModel.checkUserCred(playerEmail.getText().toString(),teamName.getText().toString());
        if(playerEmail == null){
            errorText = "A player with that email does not exists.";
            return false;
        }
       return true;

    }
}
