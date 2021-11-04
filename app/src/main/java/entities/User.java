package entities;

import android.provider.ContactsContract;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.RenameTable;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public Date dateOfBirth;

    public boolean registrationConfirmed;

    public User(){}
    public User( String firstName, String lastName, Date dateOfBirth, String teamName, String email, String password, String userRole, String phoneNumber, boolean registrationConfirmed) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.registrationConfirmed = registrationConfirmed;

    }
    public static User[] populateData() throws ParseException {
        return new User[] {
                new User("Jane", "Doe", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT", "jane.doe@mail.com", "janedoe", "player", "00386725643", true),
                new User("John", "Doe", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT","john.doe@mail.com", "johndoe", "player", "00386526781", true ),
                new User("Emily", "Hart", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "emily.hart@mail.com", "emilyhart", "player", "00389782826", true ),
                new User("Leona", "Martin", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT","leona.martin@mail.com", "leonamartin", "player", "00386543123", true ),
                new User("Marin", "Samson",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "marin.samson@mail.com", "marinsamson", "player", "00389724563", true ),
                new User("Kendall", "Murphy",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT","kendall.murphy@mail.com", "kendallmurphy", "admin", "00389825634", true ),
                new User("Samuel", "Philips",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT","samuel.philips@mail.com", "samuelphilips", "admin", "00389176254", true ),
                new User("Daniel", "Fanton", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT","daniel.fanton@mail.com", "danielfanton", "player", "00389165245", false ),
                new User("Mirel", "Jackobs",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "mirel.jackbos@mail.com", "mireljackobs", "player", "00389172564", false ),
                new User("Dana", "North",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT","dana.north@mail.com", "dananorth", "player", "00389726561", false ),
                new User("Elsa", "Clarity",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "elsa.clarity@mail.com", "elsaclarity", "trainer", "00387123678", true ),
                new User("Mark", "Hampton",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Leading VT", "mark.hampton@mail.com", "markhampton", "trainer", "00387762543", true )

        };
    }
}
