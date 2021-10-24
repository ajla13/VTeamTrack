package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "team")
public class Team {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public int userId;

}
