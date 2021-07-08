import com.teradata.jdbc.jdbc_4.TDResultSet;
import com.teradata.jdbc.jdk6.JDK6_SQL_ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TeraTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.teradata.jdbc.TeraDriver");
        try (
            Connection connection = DriverManager.getConnection("jdbc:teradata://192.168.35.128/database=dbc", "dbc", "dbc");
            PreparedStatement stmt = connection.prepareStatement("select * from test.timestamp_test");
        ) {
            if (stmt.execute()) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        String name = rs.getString(1);
                        Date timestamp = rs.getTimestamp(2);
                        Date timestampAtUTC = rs.getTimestamp(2, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
                        System.out.printf("%s\t=\t%s\tAt UTC: %s\n", name,
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(timestamp),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(timestampAtUTC)
                        );
                    }

                    System.out.printf("TD: %6s, Cal: %s -> %s%n", "+03:00", "UTC", TDResultSet.internalGetTimestamp(0, "2021-06-28 17:23:50.166023+03:00", Calendar.getInstance(TimeZone.getTimeZone("UTC"))));
                    System.out.printf("TD: %6s, Cal: %s -> %s%n", "+00:00", "UTC", TDResultSet.internalGetTimestamp(0, "2021-06-28 17:23:50.166023+00:00", Calendar.getInstance(TimeZone.getTimeZone("UTC"))));
                    System.out.printf("TD: %6s, Cal: %s -> %s%n", "", "UTC", TDResultSet.internalGetTimestamp(0, "2021-06-28 17:23:50.166023", Calendar.getInstance(TimeZone.getTimeZone("UTC"))));

                    System.out.printf("TD: %6s, Cal: %s -> %s%n", "+03:00", "CEST", TDResultSet.internalGetTimestamp(0, "2021-06-28 17:23:50.166023+03:00", Calendar.getInstance(TimeZone.getTimeZone("CEST"))));
                    System.out.printf("TD: %6s, Cal: %s -> %s%n", "+0:00", "CEST", TDResultSet.internalGetTimestamp(0, "2021-06-28 17:23:50.166023+00:00", Calendar.getInstance(TimeZone.getTimeZone("CEST"))));
                    System.out.printf("TD: %6s, Cal: %s -> %s%n", "", "CEST", TDResultSet.internalGetTimestamp(0, "2021-06-28 17:23:50.166023", Calendar.getInstance(TimeZone.getTimeZone("CEST"))));

                }
            } else {
              System.out.println("No result set");
            }
        }
    }

}
