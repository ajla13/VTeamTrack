package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import entities.User;
import repository.UserRepo;

public class UserModel extends AndroidViewModel {

    private UserRepo userRepo ;
    public List<User> userList,temp;
    private LiveData<List<User>> users;


    public UserModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepo(application);
        users= userRepo.getAllUsers();
        userList=getUserList();
    }

    public LiveData<List<User>> getUsers(){
        if(users==null){
            users= userRepo.getAllUsers();
        }
        return users;
    }


    public List<User> getUserList() {
        if (userList== null) {
            userList= userRepo.getUserList();
        }
        return userList;
    }


    public User createUser(User user) {
        userRepo.insert(user);
        return user;
    }

}
