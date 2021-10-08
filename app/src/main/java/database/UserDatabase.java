package database;

import android.content.Context;
import android.service.autofill.UserData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.UserDao;
import entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static final String DB_NAME="user_db";
    private static UserDatabase databaseInstance;

    public static ExecutorService executor = Executors.newSingleThreadExecutor();


    public static synchronized UserDatabase getInstance(Context context) {
        if(databaseInstance==null){
            databaseInstance= Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class,
                 DB_NAME).fallbackToDestructiveMigration().build();
        }
        return databaseInstance;
    }
}


