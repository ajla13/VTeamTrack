package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import dao.UserDao;
import database.AppDatabase;
import entities.User;

public class UserRepo {
    public List<User> userList;
    private LiveData<List<User>> allUsers;
    private UserDao userDao;


    @Inject
    public UserRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers=userDao.getAll();
        userList=getUserList();
    }

    public LiveData<List<User>> getAllUsers() {
        if(allUsers==null) {
            AppDatabase.executor.execute(() -> {
                allUsers= userDao.getAll();
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

    public void insert(User user) {
        AppDatabase.executor.execute(() -> {
            userDao.insert(user);
        });
    }
}
