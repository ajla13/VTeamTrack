package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game")
public class Game {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String time;
    public String location;
    public String oponent;
    public String teamName;
}

