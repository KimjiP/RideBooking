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
import java.util.Optional;

public class Ride {

    private String fromCity;
    private String toCity;
    private Calendar date;
    private int numFreeSeat;

    public static Ride createRide(String command) throws Exception {
        String[] data = command.split(" ");
        String fromCity = data[0];
        String toCity = data[1];
        Calendar date;
        Optional<Calendar> opDate = RideValidation.getDate(data[2]);
        if (opDate.isPresent()) {
            date = opDate.get();
        } else {
            throw new Exception(RideValidation.DATE_INVALID);
        }
        int numSeat;
        try {
            numSeat = Integer.parseInt(data[3]);
        } catch (NumberFormatException e) {
            throw new Exception(RideValidation.FREE_SEAT_INVALID);
        }
        return new Ride(fromCity, toCity, date, numSeat);
    }

    public Ride(String fromCity, String toCity, Calendar date, int numSeat) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
        this.numFreeSeat = numSeat;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", fromCity, toCity, getDateStr(), numFreeSeat);
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateStr() {
        return RideValidation.DATE_FORMAT.format(date.getTime());
    }

    public int getNumFreeSeat() {
        return numFreeSeat;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setNumFreeSeat(int numFreeSeat) {
        this.numFreeSeat = numFreeSeat;
    }

}