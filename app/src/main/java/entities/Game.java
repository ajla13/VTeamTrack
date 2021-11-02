package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.Converters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "game")
public class Game {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String time;
    public String location;
    public String oponent;
    public String teamName;

    @TypeConverters(Converters.class)
    public List<Integer> attendancy;


    public Game(){}
    public Game(String date, String time, String location, String teamName, List<Integer> attendancy, String oponent){

        this.date=date;
        this.time=time;
        this.location=location;
        this.teamName=teamName;
        this.attendancy=attendancy;
        this.oponent=oponent;
    }

    public static Game[] populateGame() {
        return new Game[] {
                new Game( "03/11/2021", "15:00", "Hall B", "Coast VT", new ArrayList<Integer>(), "Leading VT"),
                new Game( "12/11/2021", "17:00", "Hall B", "Coast VT", new ArrayList<Integer>(), "Mist VT"),
                new Game( "24/11/2021", "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(), "North Team"),
                new Game( "20/10/2021", "17:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(), "Leading VT")
        };
    }

}

