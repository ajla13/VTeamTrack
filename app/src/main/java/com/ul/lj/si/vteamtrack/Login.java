package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import dao.AnswerDao;
import dao.CommentDao;
import dao.FeeDao;
import dao.GamesDao;
import dao.PostDao;
import dao.SurveyDao;
import dao.TeamDao;
import dao.TrainingDao;
import dao.UserDao;
import database.AppDatabase;
import entities.Answer;
import entities.Comment;
import entities.Fee;
import entities.Game;
import entities.Post;
import entities.Survey;
import entities.Team;
import entities.Training;
import entities.User;
import viewModels.TeamModel;
import viewModels.UserModel;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText teamName;
    TeamModel teamModel;
    UserModel userModel;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AppDatabase db = AppDatabase.getInstance(getApplication());
        UserDao userDao = db.userDao();
        TeamDao teamDao = db.teamDao();
        TrainingDao trainingDao = db.trainingDao();
        GamesDao gamesDao = db.gameDao();
        PostDao postDao = db.postDao();
        SurveyDao surveyDao = db.surveyDao();
        AnswerDao answerDao = db.answerDao();
        CommentDao commentDao = db.commentDao();
        FeeDao feeDao = db.feeDao();

        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        userModel = new ViewModelProvider(this).get(UserModel.class);


        email=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.login_password);
        teamName=(EditText) findViewById(R.id.login_team);
        Button login=(Button) findViewById(R.id.btn_login);
        Button dbButton= findViewById(R.id.btn_prepopulate);

        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamDao.insertAll(Team.populateTeam());

                try {
                    userDao.insertAll(User.populateData());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    trainingDao.insertAll(Training.populateTraining());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    gamesDao.insertAll(Game.populateGame());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    postDao.insertAll(Post.populatePost());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    commentDao.insertAll(Comment.populateComment());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                surveyDao.insertAll(Survey.populateSurvey());
                answerDao.insertAll(Answer.populateAnswer());

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if(dataValidation()){
                       if(!creditentials()){
                           Toast.makeText(getApplicationContext(),
                                   "The entered creditentials are incorrect", Toast.LENGTH_LONG).show();
                       }
                       else {

                           if(user.isRegistrationConfirmed()) {

                               if (BCrypt.checkpw(password.getText().toString(), user.getPassword())) {
                                   PreferenceData.setUserLoggedInStatus(getApplicationContext(), true);
                                   PreferenceData.setLoggedInUserEmail(getApplicationContext(), email.getText().toString());
                                   PreferenceData.setLoggedInUser(getApplicationContext(), user.getId());
                                   PreferenceData.setLoggedInUserRole(getApplicationContext(), user.getUserRole());
                                   PreferenceData.setTeam(getApplicationContext(), teamName.getText().toString());
                                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                   startActivity(intent);
                                   finish();

                               } else {
                                   Toast.makeText(getApplicationContext(),
                                           "The entered creditentials are incorrect", Toast.LENGTH_LONG).show();
                               }
                           }
                           else {

                               Toast.makeText(getApplicationContext(),
                                       "Your registration has not been confirmed yet", Toast.LENGTH_LONG).show();
                           }

                       }
                   }
            }
        });

    }



    boolean creditentials(){

        user = userModel.checkUserCred(email.getText().toString(),teamName.getText().toString());
        if(user == null){
            return false;
        }

        return true;
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
        return returnVaue;
    }

    public void switchToRegistration(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
