package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;
import main.java.entity.Reservation;
import main.java.entity.WorkSpace;

public class DataReader {

    private Scanner scanner;
    private String spaceId = "Enter workspace id: ";
    private String spaceType = "Enter workspace type: ";
    private String spacePrice = "Enter workspace price: ";
    private String spaceAvailability = "Enter workspace availability: ";
    private String reservationName = "Enter client name: ";
    private String reservationDate = "Enter reservation date: ";
    private String reservationStart = "Enter reservation start time: ";
    private String reservationEnd = "Enter reservation end time: ";
    private String reservationId = "Enter reservation id: ";

    public DataReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public WorkSpace getNewSpace() {
        int id = getInt(spaceId, scanner);
        String type = getString(spaceType, scanner);
        int price = getInt(spacePrice, scanner);

        return new WorkSpace(id, type, price, true);
    }

    public Reservation getNewReservation() {
        int id = getInt(spaceId, scanner);
        String name = getString(reservationName, scanner);
        String date = getString(reservationDate, scanner);
        String timeStart = getString(reservationStart, scanner);
        String timeEnd = getString(reservationEnd, scanner);

        return new Reservation(id, name, date, timeStart, timeEnd);
    }

    public WorkSpace getUpdatedSpace() {
        int id = getInt(spaceId, scanner);
        String type = getString(spaceType, scanner);
        int price = getInt(spacePrice, scanner);
        boolean isAvailable = getBoolean(spaceAvailability, scanner);

        return new WorkSpace(id, type, price, isAvailable);
    }

    public int getSpaceIdForDeletion() {
        return getInt(spaceId, scanner);
    }

    public int getReservationIdForDeletion() {
        return getInt(reservationId, scanner);
    }

    private int getInt(String message, Scanner scanner) {
        int input = 0;
        boolean correctInput = false;

        System.out.print(message);

        while (!correctInput) {
            try {
                correctInput = true;
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException exception) {
                correctInput = false;
                System.out.print(message);
            }
        }

        return input;
    }

    private String getString(String message, Scanner scanner) {
        System.out.print(message);

        return scanner.nextLine();
    }

    private boolean getBoolean(String message, Scanner scanner) {
        boolean input = false;
        boolean correctInput = false;

        System.out.print(message);

        while (!correctInput) {
            try {
                correctInput = true;
                input = scanner.nextBoolean();
            } catch (InputMismatchException exception) {
                correctInput = false;
                System.out.print(message);
            }
        }

        return input;
    }
}
