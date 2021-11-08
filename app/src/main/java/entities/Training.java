package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.typeConverters.Converters;
import com.ul.lj.si.vteamtrack.typeConverters.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "training", foreignKeys = @ForeignKey(entity = Team.class,
                parentColumns = "id",
                childColumns = "teamId",
                onDelete = CASCADE))
public class Training {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(DateConverter.class)
    private Date date;
    private Date time;
    private String location;
    private String teamName;
    private int teamId;

    @TypeConverters(Converters.class)
    private List<Integer> attendancy;

    public int getId() {
        return id;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public List<Integer> getAttendancy() {
        return attendancy;
    }

    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setAttendancy(List<Integer> attendancy) {
        this.attendancy = attendancy;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Training( int teamId,Date date, Date time, String location, String teamName, List<Integer> attendancy){

        this.date=date;
        this.teamId = teamId;
        this.time=time;
        this.location=location;
        this.teamName=teamName;
        this.attendancy=attendancy;
    }

    public static Training[] populateTraining() throws ParseException {
        return new Training[] {
                new Training( 1,new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("14:00"), "Hall A", "Coast VT", new ArrayList<Integer>()),
                new Training( 1,new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2021"), new SimpleDateFormat("hh:mm").parse("14:00"), "Hall A", "Coast VT",
                        new ArrayList<Integer>()),
                new Training( 1,new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"), new SimpleDateFormat("hh:mm").parse("14:00"), "Hall A", "Coast VT",
                        new ArrayList<Integer>()),
                new Training( 1,new SimpleDateFormat("dd/MM/yyyy").parse("25/04/2021"), new SimpleDateFormat("hh:mm").parse("14:00"), "Hall A", "Coast VT",
                        new ArrayList<Integer>())
        };
    }

}
