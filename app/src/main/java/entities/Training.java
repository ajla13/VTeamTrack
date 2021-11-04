package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.typeConverters.Converters;
import com.ul.lj.si.vteamtrack.typeConverters.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "training")
public class Training {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @TypeConverters(DateConverter.class)
    public Date date;
    public String time;
    public String location;
    public String teamName;

    @TypeConverters(Converters.class)
    public List<Integer> attendancy;

    public Training(){}
    public Training( Date date, String time, String location, String teamName, List<Integer> attendancy){

        this.date=date;
        this.time=time;
        this.location=location;
        this.teamName=teamName;
        this.attendancy=attendancy;
    }

    public static Training[] populateTraining() throws ParseException {
        return new Training[] {
                new Training( new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "14:00", "Hall A", "Coast VT", new ArrayList<Integer>()),
                new Training( new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2021"), "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>()),
                new Training( new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"), "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>()),
                new Training( new SimpleDateFormat("dd/MM/yyyy").parse("25/04/2021"), "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>())
        };
    }

}
