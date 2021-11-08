package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.typeConverters.Converters;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "game", foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "id",
        childColumns = "teamId",
        onDelete = CASCADE))
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;
    private Date time;
    private String location;
    private String oponent;
    private String teamName;
    private int teamId;

    @TypeConverters(Converters.class)
    private List<Integer> attendance;

    public String getLocation() {
        return location;
    }

    public Date getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getId() {
        return id;
    }

    public String getOponent() {
        return oponent;
    }

    public List<Integer> getAttendance() {
        return attendance;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAttendance(List<Integer> attendance) {
        this.attendance = attendance;
    }

    public void setOponent(String oponent) {
        this.oponent = oponent;
    }

    public Game(int teamId, Date date, Date time, String location, String teamName, List<Integer> attendance, String oponent){

        this.date=date;
        this.teamId = teamId;
        this.time=time;
        this.location=location;
        this.teamName=teamName;
        this.attendance = attendance;
        this.oponent=oponent;
    }

    public static Game[] populateGame() throws ParseException {

        return new Game[] {
                new Game( 1,new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00"), "Hall B", "Coast VT", new ArrayList<Integer>(), "Leading VT"),
                new Game( 1,new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00"), "Hall B", "Coast VT", new ArrayList<Integer>(), "Mist VT"),
                new Game( 1,new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),new SimpleDateFormat("hh:mm").parse("15:00"), "Hall A", "Coast VT",
                        new ArrayList<Integer>(), "North Team"),
                new Game(1,new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),new SimpleDateFormat("hh:mm").parse("15:00"), "Hall A", "Coast VT",
                        new ArrayList<Integer>(), "Leading VT")
        };
    }

}

