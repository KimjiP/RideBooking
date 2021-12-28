/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kimji
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class RideValidation {

    private final List<String> cities;
    public static final String FROM_CITY_INVALID = "From City invalid";
    public static final String TO_CITY_INVALID = "To City invalid";
    public static final String DATE_INVALID = "Date invalid format";
    public static final String FROM_DATE_INVALID = "From Date invalidformat";
    public static final String TO_DATE_INVALID = "To Date is invalid format";
    public static final String FREE_SEAT_INVALID = "Free seat invalid format";
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");//DATE_FORMAT.format(date);
    private static final Pattern DATE_FORMAT_PATTERN = Pattern.compile("\\d{4}[-|/]\\d{1,2}[-|/]\\d{1,2}$");

    public RideValidation(List<String> cities) {
        this.cities = cities;
    }

    public boolean city(String city) {
        return cities.contains(city);
    }

    public boolean date(String date) {
        return isDateFormat(date);
    }

    public static Optional<Calendar> getDate(String dateStr) {
        try {
            Date date = DATE_FORMAT.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return Optional.of(cal);
        } catch (ParseException e) {
        }
        return Optional.empty();
    }

    public static boolean isDateFormat(String dateStr) {
        return DATE_FORMAT_PATTERN.matcher(dateStr).matches();
    }
}
