package com.ul.lj.si.vteamtrack;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ul.lj.si.vteamtrack.fragments.PublicGamesFragment;
import com.ul.lj.si.vteamtrack.fragments.PublicTrainingsFragment;
import com.ul.lj.si.vteamtrack.fragments.RegistrationInitialFragment;

public class TeamsActivity extends AppCompatActivity {

    String viewType;
    Fragment fragment;
    FragmentManager fm;
    private String source;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams_main);

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        source=this.getIntent().getStringExtra("source");

        if(source.equals("trainings")){
            fragment= new PublicTrainingsFragment();

        }
        else if(source.equals("games")){
            fragment= new PublicGamesFragment();

        }
        ft.replace(R.id.fragment_container_view_teams, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }
}
