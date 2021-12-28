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
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataCenter {

    private List<String> cities;
    private RideValidation rideValidation;
    private Map<String, Ride> rides;
    private Ride lastRides;

    public DataCenter(List<String> cities, Map<String, Ride> rides) {
        this.cities = cities;
        this.rides = rides;
        rideValidation = new RideValidation(cities);
    }

    public boolean addRide(String rideStr) {
        try {
            Ride ride = Ride.createRide(rideStr);
            return addRide(ride);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[addRide] " + rideStr + ", " + e.getMessage());
        }
        return false;
    }

    public boolean addReturnTripRide(String returnTripDate) {
        try {
            if (!rideValidation.date(returnTripDate)) {
                throw new Exception(RideValidation.DATE_INVALID);
            }
            Optional<Calendar> date = RideValidation.getDate(returnTripDate);
            Ride returnTripRide = new Ride(lastRides.getToCity(), lastRides.getFromCity(), date.get(), lastRides.getNumFreeSeat());
            addRide(returnTripRide);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[addReturnTripRide] " + returnTripDate + ", " + e.getMessage());

        }
        return false;
    }

    public boolean addRide(Ride ride) {
        try {
            String keyRide = String.format("%s %s %s", ride.getFromCity(), ride.getToCity(), ride.getDateStr());
            rides.put(keyRide, ride);
            lastRides = ride;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getCities() {
        return cities;
    }

    public Map<String, Ride> getRides() {
        return rides;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public void setRides(Map<String, Ride> rides) {
        this.rides = rides;
    }

    public RideValidation getRideValidation() {
        return rideValidation;
    }

    public Ride getLastRides() {
        return lastRides;
    }

}

