package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.ul.lj.si.vteamtrack.fragments.FeeListFragment;

import entities.Comment;
import entities.Fee;
import entities.FeeMonth;
import entities.Team;
import entities.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import viewModels.CommentModel;
import viewModels.FeeModel;
import viewModels.FeeMonthModel;
import viewModels.TeamModel;
import viewModels.UserModel;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
  private FeeMonthModel feeMonthModel;
  private UserModel userModel;
  private FeeModel feeModel;
  private TeamModel teamModel;
  private Socket mSocket;
  private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        activity=this;

        int currentUserId = PreferenceData.getLoggedInUser(getApplicationContext());
        feeMonthModel = new ViewModelProvider(this).get(FeeMonthModel.class);
        feeModel = new ViewModelProvider(this).get(FeeModel.class);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        User currentUser = userModel.getUser(currentUserId);
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigatin_view);
        NavController navController = Navigation.findNavController(this,R.id.nav_fragment);
        if(currentUser.getUserRole().equals("player")){
            bottomNav.inflateMenu(R.menu.bottom_nav_player);

        }
        else if(currentUser.getUserRole().equals("supervisor")){
            bottomNav.inflateMenu(R.menu.bottom_nav_supervisor);
        }
        else {
            bottomNav.inflateMenu(R.menu.bottom_nav);
        }
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.profileFragment, R.id.postsFragment)
                .build();

        bottomNav.setItemIconTintList(null);
        //this.getSupportActionBar().setTitle("Home");
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();


                switch (id) {
                    case R.id.profileFragment:
                        Bundle bundle = new Bundle();
                        User playerUser;
                        if(currentUser.getUserRole().equals("supervisor")){
                           playerUser=userModel.checkUserCred(currentUser.getPlayerEmail(),
                                   PreferenceData.getTeam(getApplicationContext()));
                           if(playerUser!=null){
                               bundle.putInt("userId", playerUser.getId());
                               navController.navigate(R.id.profileFragment,bundle);
                           }
                        }
                        else {
                            bundle.putInt("userId", currentUserId);
                            navController.navigate(R.id.profileFragment,bundle);
                        }
                        break;
                    case R.id.homeFragment:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.postsFragment:
                        Bundle bundlePosts = new Bundle();
                        bundlePosts.putString("source", "navigation");
                        navController.navigate(R.id.postsFragment, bundlePosts);
                        break;
                    case R.id.settingsFragment:
                        navController.navigate(R.id.settingsFragment);
                        break;
                }
                return true;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fabMain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatInitalActivity.class);
                startActivity(intent);
            }
        });
        String teamName = PreferenceData.getTeam(getApplicationContext());
        Calendar calendar = Calendar.getInstance();
        String currentMonth= new SimpleDateFormat("MMM").format(calendar.getTime());
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyy");
        FeeMonth feeMonth= feeMonthModel.getFeeMonthByMonth(currentMonth);

        if(feeMonth!=null) {
            if (new Date().compareTo(feeMonth.getValidationDate()) > 0) {
                feeMonthModel.delete(feeMonth);

                Calendar cal2 = Calendar.getInstance();
                cal2.add(Calendar.YEAR, 1);

                String validationDateAsString = dateFormat.format(cal2.getTime());

                Date validationDate = new Date();
                try {
                    validationDate = dateFormat.parse(validationDateAsString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Team curentTeam = teamModel.getTeam(teamName);
                FeeMonth newFeeMonth = new FeeMonth(currentMonth,validationDate, curentTeam.getId(), curentTeam.getName());

                feeMonthModel.insert(newFeeMonth);
                FeeMonth feeMonthReturned = feeMonthModel.getFeeMonthByMonth(currentMonth);
                List<User> userList = userModel.getUserList();
                if(userList!=null) {
                    for (User user : userList) {
                        if (user.getUserRole().equals("player") || user.getUserRole().equals("admin")) {
                            Fee userFee = new Fee(currentMonth, user.getId(), false,
                                    teamName, "10", feeMonthReturned.getId());
                            feeModel.insert(userFee);
                        }

                    }
                }

            }
        }
        else {
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.YEAR, 1);

            String validationDateAsString = dateFormat.format(cal2.getTime());

            Date validationDate = new Date();
            try {
                validationDate = dateFormat.parse(validationDateAsString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Team curentTeam = teamModel.getTeam(teamName);
            FeeMonth newFeeMonth = new FeeMonth(currentMonth,validationDate, curentTeam.getId(),
            curentTeam.getName());
            feeMonthModel.insert(newFeeMonth);
            FeeMonth feeMonthReturned = feeMonthModel.getFeeMonthByMonth(currentMonth);
            List<User> userList = userModel.getUserList();
            for( User user: userList){
                if(user.getUserRole().equals("player") || user.getUserRole().equals("admin")){
                    Fee userFee = new Fee(currentMonth,user.getId(),false,
                            teamName,"10",feeMonthReturned.getId());
                    feeModel.insert(userFee);
                }

            }

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String userRole=PreferenceData.getUserRole(getApplicationContext());
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if(userRole.equals("supervisor")){
            menu.getItem(2).setVisible(false);

        }
        if(userRole.equals("trainer")){
            getMenuInflater().inflate(R.menu.trainer_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                PreferenceData.clearLoggedInUser(getApplicationContext());
                PreferenceData.setUserLoggedInStatus(getApplicationContext(), false);
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.item2:
                Intent intentReq = new Intent(getApplicationContext(),Requests.class);
                startActivity(intentReq);
                return true;
            case R.id.item3:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.nav_fragment,new FeeListFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
                return true;
            case R.id.item4:
                Intent intentSurvey = new Intent(getApplicationContext(),SurveyListActivity.class);
                startActivity(intentSurvey);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
