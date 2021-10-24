package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "training")
public class Training {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String time;
    public String location;

}
