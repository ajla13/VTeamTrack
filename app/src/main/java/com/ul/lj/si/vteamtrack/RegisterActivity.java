package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.mindrot.jbcrypt.BCrypt;

import entities.Team;
import entities.User;
import viewModels.TeamModel;
import viewModels.UserModel;


public class RegisterActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText teamName;
    EditText name;
    EditText surname;
    TeamModel teamModel;
    UserModel userModel;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        Team team = new Team();
        team.name="Zok";
        User user = new User();
        user.firstName="TrainerTest";
        user.lastName="TrainerTest";
        user.email="trainer@email.com";
        user.userRole="trainer";
        String pw_hash = BCrypt.hashpw("test", BCrypt.gensalt());
        user.password=pw_hash;
        user.teamName="Zok";
        User tr=userModel.createUser(user);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        team.userId = tr.id;
        teamModel.createTeam(team);

        email=(EditText) findViewById(R.id.registration_email);
        password=(EditText) findViewById(R.id.registration_password);
        name=(EditText) findViewById(R.id.registration_name);
        teamName=(EditText) findViewById(R.id.registration_team);
        surname=(EditText) findViewById(R.id.registration_surname);
        Button register=(Button) findViewById(R.id.btn_register);

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
                        user.userRole="player";
                        user.password=pw_hash;
                        user.teamName=teamName.getText().toString();
                        userModel.createUser(user);
                        Toast.makeText(getApplicationContext(),
                                "Registration successfull", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "The entered email already exists.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    boolean creditentials(){

        user = userModel.checkUserCred(email.getText().toString(),teamName.getText().toString());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(user == null){
            return true;
        }
        return false;
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
