package com.ul.lj.si.vteamtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, new PlayersListFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment=null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new PlayersListFragment();
                        break;
                    case 1:
                        fragment = new TrainingsListFragment();
                        break;
                    case 2:
                        fragment = new GamesListFragment();
                        break;
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}