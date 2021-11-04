package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.typeConverters.Converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "game")
public class Game {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public Date date;
    public String time;
    public String location;
    public String oponent;
    public String teamName;

    @TypeConverters(Converters.class)
    public List<Integer> attendancy;


    public Game(){}
    public Game(Date date, String time, String location, String teamName, List<Integer> attendancy, String oponent){

        this.date=date;
        this.time=time;
        this.location=location;
        this.teamName=teamName;
        this.attendancy=attendancy;
        this.oponent=oponent;
    }

    public static Game[] populateGame() throws ParseException {
        return new Game[] {
                new Game( new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "15:00", "Hall B", "Coast VT", new ArrayList<Integer>(), "Leading VT"),
                new Game( new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "17:00", "Hall B", "Coast VT", new ArrayList<Integer>(), "Mist VT"),
                new Game( new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(), "North Team"),
                new Game( new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"17:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(), "Leading VT")
        };
    }

}

