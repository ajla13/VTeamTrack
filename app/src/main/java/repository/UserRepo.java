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
    private LiveData<List<User>> allUsers;

    @Inject
    public UserRepo(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers=userDao.getAll();

    }

    public LiveData<List<User>> getAllUser() {
        if(allUsers==null) {
            UserDatabase.executor.execute(() -> {
                allUsers = userDao.getAll();
            });
        }
        return allUsers;
    }


    public void insert(User user) {
        UserDatabase.executor.execute(() -> {
            userDao.insert(user);
        });
    }
}
