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
    private LiveData<List<User>> unconfirmedUsers;
    private UserDao userDao;
    private User user;
    String teamName;

    @Inject
    public UserRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        allUsers = userDao.getPlayers(teamName,"player", true);
        unconfirmedUsers = userDao.usersByregistration(false, "player", teamName);
        userList=getUserList();
    }

    public LiveData<List<User>> getPlayers() {
        if(allUsers==null) {
            AppDatabase.executor.execute(() -> {
                allUsers= userDao.getPlayers(teamName,"player", true);
                System.out.println("users in repo are null "+ allUsers);
            });
        }
        System.out.println("users in repo "+ allUsers);
        return allUsers;
    }
    public LiveData<List<User>> getUnconfirmedUsers() {
        if(unconfirmedUsers==null) {
            AppDatabase.executor.execute(() -> {
                unconfirmedUsers = userDao.usersByregistration(false, "player", teamName);
            });
        }
        return unconfirmedUsers;
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
    public void delete(User user) {
        userDao.delete(user);

    }
    public void update(User user) {
       userDao.update(user);

    }
    public User getUser(int id) {
        return userDao.getUser(id);
    }
}
