package org.exadel.training.utils;

import org.exadel.training.model.RegularLesson;
import org.exadel.training.model.Training;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public final class TrainingUtil {
    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    public static String DateToString(Timestamp date) {
        if(date == null){
            return "";
        }
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

    public static String DateAndTimeToString(Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        StringBuilder sb = new StringBuilder();
        sb.append(hour)
          .append(':')
          .append(getMinutes(minutes))
          .append("  ")
          .append(day)
          .append(' ')
          .append(getMonthName(month))
          .append(' ')
          .append(year);
        return sb.toString();
    }

    private static String getMonthName(int num) {
        return months[num];
    }

    private static String getMinutes(int minutes) {
        if (minutes < 10) {
            StringBuilder sb = new StringBuilder();
            return sb.append(0).append(minutes).toString();
        }
        return String.valueOf(minutes);
    }

    public static Comparator<Training> getTrainingComparatorByData() {
        return (obj1, obj2) -> obj1.getDate().compareTo(obj2.getDate());
    }

    public static Comparator<RegularLesson> getRegularLessonComparatorByData() {
        return (obj1, obj2) -> obj1.getDate().compareTo(obj2.getDate());
    }
}
