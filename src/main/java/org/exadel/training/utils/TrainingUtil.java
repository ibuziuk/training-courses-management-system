package org.exadel.training.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TrainingUtil {
    String [] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    public String DateToString(Timestamp date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(date.getTime()));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + " " + getMonth(month) + " " + year;
    }

    private String getMonth(int num){
        return months[num - 1];
    }
}
