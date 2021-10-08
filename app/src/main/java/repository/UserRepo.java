package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dao.UserDao;
import database.UserDatabase;
import entities.User;

public class UserRepo {
    private UserDao userDao;
    private MutableLiveData<List<User>> allUsers;


    @Inject
    public UserRepo(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers = (MutableLiveData<List<User>>) userDao.getAll();
    }

    public MutableLiveData<List<User>> getAllUser() {
        return allUsers;
    }

    public void insert(User user) {
        UserDatabase.executor.execute(() -> {
            userDao.insert(user);
        });
    }
}
