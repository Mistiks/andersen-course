package main.java;

import java.util.Scanner;

public class Main {

    private static final String WELCOME_MESSAGE = "Welcome to the application!";
    private static final int MAIN_MENU_OPTION_AMOUNT = 3;
    private static final int ADMIN_MENU_OPTION_AMOUNT = 6;
    private static final int CUSTOMER_MENU_OPTION_AMOUNT = 5;

    public static void main(String[] args) {
        int option;
        Scanner scanner = new Scanner(System.in);
        MenuSelector selector = new MenuSelector(scanner);
        Memory memory = new Memory();
        DataReader reader = new DataReader(scanner);

        System.out.println(WELCOME_MESSAGE);

        option = selector.chooseMainMenuOperation();

        while (option != MAIN_MENU_OPTION_AMOUNT) {
            if (option == 1) {
                processAdminAction(selector, reader, memory);
            } else if (option == 2) {
                processUserAction(selector, reader, memory);
            }

            option = selector.chooseMainMenuOperation();
        }
    }

    private static void processAdminAction(MenuSelector selector, DataReader reader, Memory memory) {
        int option = selector.chooseAdminMenuOperation();

        while (option != ADMIN_MENU_OPTION_AMOUNT) {
            switch (option) {
                case 1 -> memory.addWorkSpace(reader.getNewSpace());
                case 2 -> memory.deleteWorkSpace(reader.getSpaceIdForDeletion());
                case 3 -> System.out.print(memory.getAllWorkSpaces());
                case 4 -> memory.updateWorkSpace(reader.getUpdatedSpace());
                case 5 -> System.out.print(memory.getAllReservations());
            }

            option = selector.chooseAdminMenuOperation();
        }
    }

    private static void processUserAction(MenuSelector selector, DataReader reader, Memory memory) {
        int option = selector.chooseCustomerMenuOperation();

        while (option != CUSTOMER_MENU_OPTION_AMOUNT) {
            switch (option) {
                case 1 -> System.out.print(memory.getAvailableWorkSpaces());
                case 2 -> memory.addReservation(reader.getNewReservation());
                case 3 -> System.out.print(memory.getAllReservations());
                case 4 -> memory.deleteReservation(reader.getReservationIdForDeletion());
            }

            option = selector.chooseCustomerMenuOperation();
        }
    }
}
