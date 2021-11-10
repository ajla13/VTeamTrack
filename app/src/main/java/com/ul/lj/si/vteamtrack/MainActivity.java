package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import entities.Comment;
import io.socket.client.IO;
import io.socket.client.Socket;
import viewModels.CommentModel;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mSocket.connect();

        int currentUserId = PreferenceData.getLoggedInUser(getApplicationContext());

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigatin_view);
        NavController navController = Navigation.findNavController(this,R.id.nav_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.profileFragment, R.id.postsFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);


        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.profileFragment:
                        Bundle bundle = new Bundle();
                        bundle.putInt("userId", currentUserId);
                        navController.navigate(R.id.profileFragment,bundle);
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

    }
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String userRole=PreferenceData.getUserRole(getApplicationContext());
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
