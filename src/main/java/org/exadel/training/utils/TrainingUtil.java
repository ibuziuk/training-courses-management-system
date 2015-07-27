package org.exadel.training.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public final class TrainingUtil {
    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    public static String DateToString(Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(day)
          .append(' ')
          .append(getMonthName(month))
          .append(' ')
          .append(year);
        return sb.toString();
    }

    private static String getMonthName(int num) {
        return months[num];
    }
}
