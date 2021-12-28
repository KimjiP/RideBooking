/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kimji
 */

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {

    private static final Pattern SEARCH_PATTERN
            = Pattern.compile("[a-zA-Z]+( [a-zA-Z]+)?( \\d{4}[-|/]\\d{1,2}[-|/]\\d{1,2})?( \\d{4}[-|/]\\d{1,2}[-|/]\\d{1,2})?( [1-9][0-9]*)?$");

    public static Map<String, Ride> search(Map<String, Ride> rides, String searchStr, RideValidation rideValidation) throws Exception {
        Matcher matcher = SEARCH_PATTERN.matcher(searchStr);
        if (!matcher.matches()) {
            return null;
        }
        int currentTerm = 0;

        String[] searchStrArr = searchStr.split(" ");
        String fromCity = searchStrArr[currentTerm];
        if (!rideValidation.city(fromCity)) {
            throw new Exception(RideValidation.FROM_CITY_INVALID);
        }
        Map<String, Ride> ridesRet = new HashMap<>(rides);

        String toCity = searchStrArr[++currentTerm];
        if (rideValidation.date(toCity)) {//not exist to-city
            ridesRet = filterMatchLocation(ridesRet, fromCity);
        } else {
            if (!rideValidation.city(toCity)) {
                throw new Exception(RideValidation.TO_CITY_INVALID);
            }
            currentTerm++;
            ridesRet = filterMatchLocation(ridesRet, fromCity, toCity);
        }
        if (currentTerm >= searchStrArr.length) {
            return ridesRet;
        }

        String fromDateStr = searchStrArr[currentTerm];
        if (!rideValidation.date(fromDateStr)) {
            throw new Exception(RideValidation.FROM_CITY_INVALID);
        }
        Optional<Calendar> opFromDate = RideValidation.getDate(fromDateStr);
        Calendar fromDate = opFromDate.get();
        currentTerm++;
        if (currentTerm >= searchStrArr.length) {
            return filterMatchDate(ridesRet, fromDate);
        }
        String toDateStr = searchStrArr[currentTerm];
        Optional<Calendar> opToDate = RideValidation.getDate(toDateStr);
        if (opToDate.isPresent()) {
            currentTerm++;
            Calendar toDate = opToDate.get();
            ridesRet = filterMatchDate(ridesRet, fromDate, toDate);
        }
        if (currentTerm == searchStrArr.length - 1) {//exist minimum-free-seats
            int minimumFreeSeats = Integer.parseInt(searchStrArr[currentTerm]);
            ridesRet = filterMinimumFreeSeats(ridesRet, minimumFreeSeats);
        }
        return ridesRet;
    }

    private static Map<String, Ride> filterMatchDate(Map<String, Ride> ridesRet, Calendar fromDate, Calendar toDate) {
        Map<String, Ride> ret = new HashMap<>();
        for (Map.Entry<String, Ride> entry : ridesRet.entrySet()) {
            String key = entry.getKey();
            Ride value = entry.getValue();
            if (isMatchDate(value.getDate(), fromDate, toDate)) {
                ret.put(key, value);
            }
        }
        return ret;
    }

    private static Map<String, Ride> filterMatchDate(Map<String, Ride> ridesRet, Calendar currentDate) {
        Map<String, Ride> ret = new HashMap<>();
        for (Map.Entry<String, Ride> entry : ridesRet.entrySet()) {
            String key = entry.getKey();
            Ride value = entry.getValue();
            if (isMatchDate(currentDate, value.getDate())) {
                ret.put(key, value);
            }
        }
        return ret;
    }

    private static Map<String, Ride> filterMatchLocation(Map<String, Ride> ridesRet, String currentFromCity) {
        Map<String, Ride> ret = new HashMap<>();
        for (Map.Entry<String, Ride> entry : ridesRet.entrySet()) {
            String key = entry.getKey();
            Ride value = entry.getValue();
            if (isMatchLocation(currentFromCity, value.getFromCity())) {
                ret.put(key, value);
            }
        }
        return ret;
    }

    private static Map<String, Ride> filterMatchLocation(Map<String, Ride> ridesRet, String currentFromCity, String currentToCity) {
        Map<String, Ride> ret = new HashMap<>();
        for (Map.Entry<String, Ride> entry : ridesRet.entrySet()) {
            String key = entry.getKey();
            Ride value = entry.getValue();
            if (isMatchLocation(currentFromCity, currentToCity, value.getFromCity(), value.getToCity())) {
                ret.put(key, value);
            }
        }
        return ret;
    }

    private static Map<String, Ride> filterMinimumFreeSeats(Map<String, Ride> ridesRet, int minimumFreeSeats) {
        Map<String, Ride> ret = new HashMap<>();
        for (Map.Entry<String, Ride> entry : ridesRet.entrySet()) {
            String key = entry.getKey();
            Ride value = entry.getValue();
            if (isMinimumFreeSeats(value.getNumFreeSeat(), minimumFreeSeats)) {
                ret.put(key, value);
            }
        }
        return ret;
    }

    private static boolean isMatchDate(Calendar currentDate, Calendar fromDate, Calendar toDate) {
        return currentDate.after(fromDate) && currentDate.before(toDate)
                || isMatchDate(currentDate, fromDate) || isMatchDate(currentDate, toDate);
    }

    private static boolean isMatchDate(Calendar currentDate, Calendar fromDate) {
        return currentDate.get(Calendar.YEAR) == fromDate.get(Calendar.YEAR)
                && currentDate.get(Calendar.MONTH) == fromDate.get(Calendar.MONTH)
                && currentDate.get(Calendar.DAY_OF_MONTH) == fromDate.get(Calendar.DAY_OF_MONTH);
    }

    private static boolean isMatchLocation(String currentCity, String fromCity) {
        return currentCity.equals(fromCity);
    }

    private static boolean isMatchLocation(String currentFromCity, String currentToCity, String fromCity, String toCity) {
        return currentFromCity.equals(fromCity) && currentToCity.equals(toCity);
    }

    private static boolean isMinimumFreeSeats(int numFreeSeats, int minimumFreeSeats) {
        return numFreeSeats >= minimumFreeSeats;
    }
}