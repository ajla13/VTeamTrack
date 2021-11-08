package com.ul.lj.si.vteamtrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.Login;
import com.ul.lj.si.vteamtrack.R;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Team;
import entities.User;

import viewModels.TeamModel;
import viewModels.UserModel;

public class RegistrationFormFragment extends Fragment {
    EditText email;
    EditText password;
    EditText passRepeat;
    EditText teamName;
    EditText name;
    EditText phone;
    EditText surname;
    EditText dateOfBirth;
    Team team;
    String successText;
    UserModel userModel;
    TeamModel teamModel;
    User user;
    String errorText;
    String registrationType;

    public RegistrationFormFragment(String registrationType){
        this.registrationType=registrationType;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        teamModel = new ViewModelProvider(this).get(TeamModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.registration, container, false);
        email=(EditText) view.findViewById(R.id.registration_email);
        password=(EditText) view.findViewById(R.id.registration_password);
        passRepeat=(EditText) view.findViewById(R.id.registration_password_repeat);
        name=(EditText) view.findViewById(R.id.registration_name);
        phone=(EditText) view.findViewById(R.id.registration_phone);
        teamName=(EditText) view.findViewById(R.id.registration_team);
        surname=(EditText) view.findViewById(R.id.registration_surname);
        dateOfBirth=(EditText) view.findViewById(R.id.registration_dateOfBirth);

        Button register=(Button) view.findViewById(R.id.btn_register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    if(creditentials()){
                        Date dOfB= new Date();
                        try {
                            dOfB=new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(registrationType=="trainer"){
                            String firstName = name.getText().toString();
                            String lastname =  surname.getText().toString();
                            String teamNameDb = teamName.getText().toString();
                            String emaileDb = email.getText().toString();
                            String pass = password.getText().toString();
                            String phoneDb = phone.getText().toString();
                            team = new Team(teamNameDb);

                            Team teamReturned = teamModel.createTeam(team);
                            System.out.println("teamID " + teamReturned.getId());
                            User user = new User(teamReturned.getId(),firstName, lastname, dOfB,teamNameDb, emaileDb,pass,
                                    "trainer",phoneDb,true);
                        //    User createdUser = userModel.createUser(user);
                            successText="Registration successful";
                        }
                        else {
                            User user = new User(0,
                                    name.getText().toString(),
                                    surname.getText().toString(),
                                    dOfB,teamName.getText().toString(),email.getText().toString(),password.getText().toString(),
                                    "player",phone.getText().toString(),false);

                            Team teamPlayer = teamModel.getTeam(teamName.getText().toString());
                            user.setTeamId(teamPlayer.getId());
                            userModel.createUser(user);
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
            }
        });
        return view;
    }

    boolean creditentials(){
        Team team = teamModel.getTeam(teamName.getText().toString());
        if(registrationType.equals("trainer")){

            if(team==null){
                return true;
            }
            errorText="A team under that name is already registered.";
            return false;
        }
        else{
            if(team==null){
                errorText="A team under that name is not registered.";
                return false;

            }
            user = userModel.checkUserCred(email.getText().toString(),teamName.getText().toString());
            if(user == null){
                return true;
            }
            if(user.isRegistrationConfirmed()) {
                errorText = "A player with that email already exists.";
            }
            else {
                errorText="A registration request with that email already exists.";
            }
            return false;
        }
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
        if(isEmpty(passRepeat) || !passRepeat.getText().toString().equals(password.getText().toString())){
            passRepeat.setError("Passwords do not match");
            returnVaue=false;
        }
        if(isEmpty(email)){
            email.setError("Email can not be empty");
            returnVaue=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError("Please enter a valid email");
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
        if(isEmpty(dateOfBirth)){
            dateOfBirth.setError("Date of birth can not be empty");
            returnVaue=false;
        }
        return returnVaue;
    }

}
