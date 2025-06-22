package logic;

import entity.Reservation;
import entity.WorkSpace;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class DataReader {

    private Scanner scanner;
    private String spaceId = "Enter workspace id: ";
    private String spaceType = "Enter workspace type: ";
    private String spacePrice = "Enter workspace price: ";
    private String spaceAvailability = "Enter workspace availability: ";
    private String reservationName = "Enter client name: ";
    private String reservationDate = "Enter reservation date in a dd-MM-yyyy format: ";
    private String reservationStart = "Enter reservation start time in a HH:mm format: ";
    private String reservationEnd = "Enter reservation end time in a HH:mm format: ";
    private String reservationId = "Enter reservation id: ";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

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
        int id = getInt(reservationId, scanner);
        int workSpaceId = getInt(spaceId, scanner);
        String name = getString(reservationName, scanner);
        LocalDate date = getDate(reservationDate, scanner);
        LocalTime timeStart = getTime(reservationStart, scanner);
        LocalTime timeEnd = getTime(reservationEnd, scanner);

        return new Reservation(id, workSpaceId, name, date, timeStart, timeEnd);
    }

    public WorkSpace getUpdatedSpace() {
        int id = getInt(spaceId, scanner);
        String type = getString(spaceType, scanner);
        int price = getInt(spacePrice, scanner);
        boolean isAvailable = getBoolean(spaceAvailability, scanner);

        return new WorkSpace(id, type, price, isAvailable);
    }

    public int getWorkSpaceIdForDeletion() {
        return getInt(spaceId, scanner);
    }

    public int getReservationIdForDeletion() {
        return getInt(reservationId, scanner);
    }

    private int getInt(String message, Scanner scanner) {
        int input;
        System.out.print(message);

        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print(message);
        }

        input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    private String getString(String message, Scanner scanner) {
        System.out.print(message);

        return scanner.nextLine();
    }

    private boolean getBoolean(String message, Scanner scanner) {
        boolean input;
        System.out.print(message);

        while (!scanner.hasNextBoolean()) {
            scanner.next();
            System.out.print(message);
        }

        input = scanner.nextBoolean();
        scanner.nextLine();

        return input;
    }

    private LocalDate getDate(String message, Scanner scanner) {
        LocalDate input = LocalDate.now();
        boolean correctInput = false;

        System.out.print(message);

        while (!correctInput) {
            try {
                correctInput = true;
                input = LocalDate.parse(scanner.nextLine(), dateFormatter);
            } catch (DateTimeParseException exception) {
                correctInput = false;
                System.out.print(message);
            }
        }

        return input;
    }

    private LocalTime getTime(String message, Scanner scanner) {
        LocalTime input = LocalTime.now();
        boolean correctInput = false;

        System.out.print(message);

        while (!correctInput) {
            try {
                correctInput = true;
                input = LocalTime.parse(scanner.nextLine(), timeFormatter);
            } catch (DateTimeParseException exception) {
                correctInput = false;
                System.out.print(message);
            }
        }

        return input;
    }
}
