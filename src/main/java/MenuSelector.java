package main.java;

import java.util.Scanner;
import java.util.InputMismatchException;

public class MenuSelector {
    Scanner scanner;
    private static final String CHOOSE_OPTION = "Choose option: ";
    private static final String[] MAIN_MENU = {"1 - Admin Login", "2 - User Login", "3 - Exit"};
    private static final String[] ADMIN_MENU = {"1 - Add new space", "2 - Remove space",
            "3 - View all spaces", "4 - Update space", "5 - View all reservations", "6 - Exit"};
    private static final String[] CUSTOMER_MENU = {"1 - Browse available spaces", "2 - Make a reservation",
            "3 - View my reservations", "4 - Cancel reservation", "5 - Exit"};

    public MenuSelector(Scanner scanner) {
        this.scanner = scanner;
    }

    private int chooseOperation(String[] options) {
        int option = 0;

        printMenu(options);

        while (option <= 0 || option > options.length) {
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Please enter value between 1 and " + options.length);
                System.out.print(CHOOSE_OPTION);
            }
        }

        return option;
    }

    public int chooseMainMenuOperation() {
        return chooseOperation(MAIN_MENU);
    }

    public int chooseAdminMenuOperation() {
        return chooseOperation(ADMIN_MENU);
    }

    public int chooseCustomerMenuOperation() {
        return chooseOperation(CUSTOMER_MENU);
    }

    public void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }

        System.out.print(CHOOSE_OPTION);
    }
}
