package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import database.AppDatabase;
import entities.User;
import repository.UserRepo;

public class UserModel extends AndroidViewModel {

    private UserRepo userRepo ;
    public List<User> userList,temp;
    private LiveData<List<User>> users;
    private LiveData<List<User>> unconfirmedUsers;

    public UserModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepo(application);
        users = userRepo.getPlayers();
        userList=getUserList();
        unconfirmedUsers=userRepo.getUnconfirmedUsers();
    }

    public LiveData<List<User>> getPlayers(){
        if(users==null){
            users= userRepo.getPlayers();
        }
        return users;
    }
    public LiveData<List<User>> getUnconfirmedUsers(){
        if(unconfirmedUsers==null){
            unconfirmedUsers= userRepo.getUnconfirmedUsers();
        }
        return unconfirmedUsers;
    }

    public List<User> getUserList() {
        if (userList== null) {
            userList= userRepo.getUserList();
        }
        return userList;
    }

    public User checkUserCred(String email, String teamName){
         return userRepo.checkUserCred(email,teamName);
    }

    public User createUser(User user) {
        userRepo.insert(user);
        return user;
    }
    public User getUser(int id) {
        return userRepo.getUser(id);
    }

}
