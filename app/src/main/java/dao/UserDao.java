package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import entities.Team;
import entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE teamName LIKE :teamName AND userRole LIKE :userRole")
    LiveData<List<User>> getPlayers(String teamName, String userRole);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT * FROM user WHERE id LIKE :userId")
    User getUser(int userId);

    @Query("SELECT * FROM user WHERE email LIKE :userEmail AND teamName LIKE :teamName")
    User checkUserCred(String userEmail, String teamName);

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE firstname LIKE :first AND " +
            "lastname LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);


}
