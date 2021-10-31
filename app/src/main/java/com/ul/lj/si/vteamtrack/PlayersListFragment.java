package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import viewModels.UserModel;


public class PlayersListFragment extends Fragment {
    UserModel userModel;
    ListView listView;
    UsersAdapter userAdapter;


        @Nullable
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.listview, container, false);

            userModel = new ViewModelProvider(this).get(UserModel.class);


            ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getPlayers().getValue();


            listView = (ListView)view.findViewById(R.id.lvUsers);
            listView.setAdapter(userAdapter);


            if (arrayOfUsers != null){

                userAdapter = new UsersAdapter(getActivity().getApplicationContext(), arrayOfUsers);
                listView.setAdapter(userAdapter);
            }else{
                Log.d("gwyd","user list was null");
                userAdapter = new UsersAdapter(getActivity().getApplicationContext(),new ArrayList<User>());
                listView.setAdapter(userAdapter);
            }

            userModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> users) {
                    if (users != null) {
                        userAdapter.setUsers(users);
                        userAdapter.clear();
                        userAdapter.addAll(users);
                    } else {
                        Log.d("gwyd", "no users found in db");
                        userAdapter.setUsers(new ArrayList<User>());
                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    User usr = userAdapter.getItem(i);

                }
            });

            return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
