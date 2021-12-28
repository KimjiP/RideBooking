# RideBooking
A command line-based ride sharing/booking java application that creates rides and searches for booked rides

See the screenshot below for a sample of its output. ">" indicates user input while "=>" indicates program output.

<img width="364" alt="Capture1" src="https://user-images.githubusercontent.com/22336263/147584861-ddf68315-07ec-4c1b-92cb-b4aff2b9df72.PNG">

In this program, the names of the cities are saved in a List, therefore valid bookings are made only when those pre-saved city names are used.

## Commands

1. C FromCity ToCity date NumberOfSeats

--> Creates a new ride from FromCity to ToCity on the date specifided and number of seats required

2. R date

--> Creates a return ride for the previously created ride (i.e. FromCity and ToCity are reversed, and number of seats are the same)

3. S FromCity ToCity FromDate ToDate MinimumSeatNumber

--> searches for rides between 2 specified cities within a specified date range, and a minimum required number of seats. All parameters are optional.
