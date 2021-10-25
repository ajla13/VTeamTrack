package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import javax.inject.Inject;

import dao.UserDao;
import database.AppDatabase;
import entities.User;

public class UserRepo {
    public List<User> userList;
    private LiveData<List<User>> allUsers;
    private UserDao userDao;
    private User user;
    String teamName;

    @Inject
    public UserRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        allUsers = userDao.getPlayers(teamName,"player");
        userList=getUserList();
    }

    public LiveData<List<User>> getPlayers() {
        if(allUsers==null) {
            AppDatabase.executor.execute(() -> {
                allUsers= userDao.getPlayers(teamName,"player");
                System.out.println("users in repo are null "+ allUsers);
            });
        }
        System.out.println("users in repo "+ allUsers);
        return allUsers;
    }

    public List<User> getUserList() {
        if(userList==null) {
            AppDatabase.executor.execute(() -> {
                userList= userDao.getUsers();

            });
        }
        return userList;
    }
    public User checkUserCred(String email, String teamName){
             user = userDao.checkUserCred(email, teamName);
             return user;
    }

    public void insert(User user) {
        userDao.insert(user);

    }
    public User getUser(int id) {
        return userDao.getUser(id);
    }
}
