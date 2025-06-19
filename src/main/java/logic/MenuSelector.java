package logic;

import java.util.InputMismatchException;
import java.util.Scanner;
import exception.InvalidOptionException;

public class MenuSelector {
    Scanner scanner;
    private static final String CHOOSE_OPTION = "Choose option: ";
    private static final String[] MAIN_MENU = {"1 - Admin Login", "2 - User Login", "3 - Exit"};
    private static final String[] ADMIN_MENU = {"1 - Add new space", "2 - Remove space",
            "3 - View all spaces", "4 - Update space", "5 - View all reservations", "6 - Exit"};
    private static final String[] CUSTOMER_MENU = {"1 - Browse available spaces",
            "2 - Make a reservation", "3 - View my reservations",
            "4 - Cancel reservation", "5 - Exit"};

    public MenuSelector(Scanner scanner) {
        this.scanner = scanner;
    }

    private int chooseOperation(String[] options) {
        int option = 0;
        boolean correctInput = false;

        printMenu(options);

        while (!correctInput) {
            try {
                correctInput = true;
                option = scanner.nextInt();
                validateOption(option, options);
            } catch (InputMismatchException exception) {
                correctInput = false;
                System.out.println("Please enter value between 1 and " + options.length);
                System.out.print(CHOOSE_OPTION);

            } catch (InvalidOptionException exception) {
                correctInput = false;
                System.out.println(exception.getMessage());
                System.out.print(CHOOSE_OPTION);
            }
        }

        return option;
    }

    private void validateOption(int option, String[] options) throws InvalidOptionException {
        if (option <= 0 || option > options.length) {
            throw new InvalidOptionException("Value is out of range! " +
                    "Please enter value between 1 and " + options.length);
        }
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
