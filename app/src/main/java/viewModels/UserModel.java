package viewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.app.Activity;

import java.util.List;

import entities.User;
import repository.UserRepo;

public class UserModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private UserRepo userRepo ;

    public UserModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepo(application);
        users=userRepo.getAllUser();

    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = userRepo.getAllUser();
            System.out.println(users);
        }
        return users;
    }

    public User createUser(User user) {
        userRepo.insert(user);
        return user;
    }

}
