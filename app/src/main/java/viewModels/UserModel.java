package viewModels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import entities.User;
import repository.UserRepo;

public class UserModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private UserRepo userRepo ;

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            loadUsers();
        }
        return users;
    }

    public User createUser(User user) {
        System.out.println(user);
      //  userRepo = new UserRepo(Context.getApplicationContext());
        //userRepo.insert(user);
        return user;
    }
    private void loadUsers() {
        users = userRepo.getAllUser();
    }
}
