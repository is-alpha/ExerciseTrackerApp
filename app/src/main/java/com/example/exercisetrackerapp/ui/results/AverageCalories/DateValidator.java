package com.example.exercisetrackerapp.ui.results.AverageCalories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator {

    //Hoy?
    public boolean isThisDateToday(String dateToValidate, String dateFormat) {

        return false;
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

    //Año?
    public boolean isThisDateWithin1YearRange(String dateToValidate, String dateFormat) {

        return false;
    }

}
