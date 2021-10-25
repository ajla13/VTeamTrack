package com.ul.lj.si.vteamtrack;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import org.mindrot.jbcrypt.BCrypt;

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
    EditText surname;
    Team team;
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
        View view = inflater.inflate(R.layout.registration, container, false);
        email=(EditText) view.findViewById(R.id.registration_email);
        password=(EditText) view.findViewById(R.id.registration_password);
        passRepeat=(EditText) view.findViewById(R.id.registration_password_repeat);
        name=(EditText) view.findViewById(R.id.registration_name);
        teamName=(EditText) view.findViewById(R.id.registration_team);
        surname=(EditText) view.findViewById(R.id.registration_surname);
        Button register=(Button) view.findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    if(creditentials()){
                        String pw_hash = BCrypt.hashpw(password.getText().toString(), BCrypt.gensalt());
                        User user = new User();
                        user.firstName=name.getText().toString();
                        user.lastName=surname.getText().toString();
                        user.email=email.getText().toString();
                        user.password=pw_hash;
                        user.teamName=teamName.getText().toString();
                        if(registrationType=="trainer"){
                            user.userRole="trainer";

                        }
                        else {
                            user.userRole="player";

                        }
                        User createdUser = userModel.createUser(user);
                        if(registrationType.equals("trainer")){

                            team = new Team();
                            team.name=teamName.getText().toString();
                            team.userId =createdUser.id;
                            teamModel.createTeam(team);
                        }
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Registration successfull", Toast.LENGTH_LONG).show();
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
            System.out.println(team);
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
            errorText="The entered email already exists.";
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
        return returnVaue;
    }
}
