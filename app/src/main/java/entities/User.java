package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RenameTable;

@Entity(tableName = "user")
public class User {


    @PrimaryKey(autoGenerate = true)
    public int id;

    public String firstName;

    public String lastName;
}
