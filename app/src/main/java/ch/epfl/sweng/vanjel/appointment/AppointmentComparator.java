package ch.epfl.sweng.vanjel.appointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


/**
 * @author Aslam CADER
 * @reviewer
 */
public class AppointmentComparator implements Comparator<Appointment> {

    private SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
    DateFormat hourFormatter = new SimpleDateFormat("HH:mm");

    // compare depending date
    @Override
    public int compare(Appointment o1, Appointment o2) {
        try {
            Date o1Date = formatter.parse(o1.getDay());
            Date o2Date = formatter.parse(o2.getDay());

            int comparator = o1Date.compareTo(o2Date);

            if(comparator == 0) {
                // we need to compare hour
                Date o1Hour = hourFormatter.parse(o1.getHour());
                Date o2Hour = hourFormatter.parse(o2.getHour());

                return o1Hour.compareTo(o2Hour);
            }

            return comparator;


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
