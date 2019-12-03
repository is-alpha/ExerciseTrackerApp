package com.example.exercisetrackerapp.ui.results.AverageCalories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateValidator {

    public static int getCountOfDays(String date, String dateFormat) {
        Calendar calendar = null;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date cDate = sdf.parse(date);
            calendar = Calendar.getInstance();
            calendar.setTime(cDate);

            long msDiff = Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis();
            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

            System.out.println("Current time is : " + daysDiff);

            return (int)daysDiff;

        } catch (ParseException e) {

            e.printStackTrace();
            return -1;
        }
    }

    //Semana?
    public boolean isThisDateWithin1WeekRange(String dateToValidate, String dateFormat) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);

            // current date before 1 month
            Calendar currentDateBefore1Month  = Calendar.getInstance();
            currentDateBefore1Month.add(Calendar.WEEK_OF_MONTH, -1);

            if(date.after(currentDateBefore1Month.getTime())) {
                //Date in range
                return true;
            } else {
                return false;
            }

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
    }

    //Mes?
    public boolean isThisDateWithin1MonthRange(String dateToValidate, String dateFormat) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);

            // current date before 1 month
            Calendar currentDateBefore1Month  = Calendar.getInstance();
            currentDateBefore1Month.add(Calendar.MONTH, -1);

            if(date.after(currentDateBefore1Month.getTime())) {
                //Date in range
                return true;
            } else {
                return false;
            }

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
    }

}
