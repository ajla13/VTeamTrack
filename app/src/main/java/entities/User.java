package entities;

import android.provider.ContactsContract;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.RenameTable;

@Entity(tableName = "user")
public class User {


    @PrimaryKey(autoGenerate = true)
    public int id;

    public String firstName;

    public String lastName;
    public String teamName;
    public String email;

    public String password;

    public String userRole;

    public String phoneNumber;

    public String dateOfBirth;

    public boolean registrationConfirmed;
}
