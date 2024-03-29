package com.ul.lj.si.vteamtrack.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import viewModels.UserModel;

public class HomeFragment extends Fragment{

    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("supervisor")){
            tabLayout.removeTabAt(0);
            ft.replace(R.id.simpleFrameLayout, new TrainingsListFragment());
        }
        else {
            ft.replace(R.id.simpleFrameLayout, new PlayersListFragment());
        }
        tabLayout.getTabAt(0).select();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("supervisor")){
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Fragment fragment=null;
                    switch (tab.getPosition()) {
                        case 0:
                            fragment = new TrainingsListFragment();
                            break;
                        case 1:
                            fragment = new GamesListFragment();
                            break;
                    }

                    FragmentManager fm = getChildFragmentManager();
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
        else {
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

                    FragmentManager fm = getChildFragmentManager();
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

        return view;
    }


}