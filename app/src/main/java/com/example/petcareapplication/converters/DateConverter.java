package com.example.petcareapplication.converters;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }

    public static String diffTime(Date reminderDate) {
        Date dateNow = new Date();
        long diffInMilliseconds = reminderDate.getTime() - dateNow.getTime();
        long diffInDays = Math.abs(TimeUnit.MILLISECONDS.toDays(diffInMilliseconds));
        long diffInHours = Math.abs(TimeUnit.MILLISECONDS.toHours
                (Math.abs(diffInMilliseconds) - 86400000 * Math.abs(diffInDays)));
        long diffInMinutes = Math.abs(TimeUnit.MILLISECONDS.toMinutes
                (Math.abs(diffInMilliseconds) - 86400000 * Math.abs(diffInDays)
                        - 3600000 * diffInHours));

        String timerStart;
        if (diffInMilliseconds <= 0) {
            timerStart = "Прошло";
        } else {
            timerStart = "Осталось";
        }

        String diffInDaysString;
        if (Math.abs(diffInDays) >= 1) {
            diffInDaysString = " " + diffInDays + " " + declensionOfDays(diffInDays);
        } else {
            diffInDaysString = "";
        }

        String diffInHoursString;
        if (Math.abs(diffInHours) >= 1) {
            diffInHoursString = " " + diffInHours + " " + declensionOfHours(diffInHours);
        } else {
            diffInHoursString = "";
        }

        String diffInMinutesString;
        if (Math.abs(diffInMinutes) >= 1) {
            diffInMinutesString = " " + diffInMinutes + " " + declensionOfMinutes(diffInMinutes);
        } else {
            diffInMinutesString = "";
        }

        return timerStart + diffInDaysString + diffInHoursString + diffInMinutesString;
    }

    private static String declensionOfMinutes(long minutes) {
        if (minutes % 10 == 1 && minutes != 11) {
            return "минута";
        } else if ((minutes % 10 >= 2 && minutes % 10 <= 4) && (minutes < 10 || minutes > 20)) {
            return "минуты";
        } else {
            return "минут";
        }
    }

    private static String declensionOfHours(long hours) {
        if (hours % 10 == 1 && hours != 11) {
            return "час";
        } else if ((hours % 10 >= 2 && hours % 10 <= 4) && (hours < 10 || hours > 20)) {
            return "часа";
        } else {
            return "часов";
        }
    }

    private static String declensionOfDays(long days) {
        if (days % 10 == 1 && days != 11) {
            return "день";
        } else if ((days % 10 >= 2 && days % 10 <= 4) && (days < 10 || days > 20)) {
            return "дня";
        } else {
            return "дней";
        }
    }
}