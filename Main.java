/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kimji
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

   public static String[] dates = {"2021-12-01", "2021-12-02", "2021-12-03", "2021-12-04", "2021-12-05"};

    public static void main(String[] args) {
        List<String> cities = Arrays.asList("London", "Manchester", "Birmingham", "Leeds", "Glasgow");

        DataCenter dataCenter = new DataCenter(cities, new HashMap<>());
        //boolean hasCcommand = false;

        // scanner for input
        Scanner s = new Scanner(System.in);
        String command;
        System.out.println("Enter command: ");

        do {
            System.out.print("> ");
            command = s.nextLine();
            commandProcessor(dataCenter, command);

        } while (!command.equalsIgnoreCase("exit"));
    }

    private static void commandProcessor(DataCenter dataCenter, String command) {
        //System.out.println("> " + command);
        String[] data = command.split(" ");
        String cmd = (data[0].charAt(0) + "").toLowerCase();
        String commandData = command.substring(2, command.length());
        if (cmd.equals("c")) {
            dataCenter.addRide(commandData);
        } else if (cmd.equals("s")) {
            try {
                System.out.println("=> ");
                Map<String, Ride> search = Search.search(dataCenter.getRides(), commandData, dataCenter.getRideValidation());
                if (search == null) {
                    return;
                }
                for (Map.Entry<String, Ride> entry : search.entrySet()) {
                    System.out.println(entry.getValue());

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        } else if (cmd.equals("r")) {
            dataCenter.addReturnTripRide(commandData);
        }
    }
}
