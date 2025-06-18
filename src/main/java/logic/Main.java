package logic;

import entity.Reservation;
import state.StateFileReader;
import state.StateFileWriter;

import java.util.Scanner;

public class Main {

    private static final String WELCOME_MESSAGE = "Welcome to the application!";
    private static final int MAIN_MENU_OPTION_AMOUNT = 3;
    private static final int ADMIN_MENU_OPTION_AMOUNT = 6;
    private static final int CUSTOMER_MENU_OPTION_AMOUNT = 5;
    private static final String WORKSPACE_PATH = "src/main/resources/workspace.ser";
    private static final String RESERVATION_PATH = "src/main/resources/reservation.ser";
    private static final String STATE_LOAD_MESSAGE = "Program state wasn't retrieved. Start with empty memory!";

    public static void main(String[] args) {
        int option;
        Scanner scanner = new Scanner(System.in);
        MenuSelector selector = new MenuSelector(scanner);
        Memory<Reservation> memory = new Memory<>();
        DataReader reader = new DataReader(scanner);
        StateFileWriter fileWriter = new StateFileWriter(RESERVATION_PATH, WORKSPACE_PATH, memory);
        StateFileReader fileReader = new StateFileReader(RESERVATION_PATH, WORKSPACE_PATH, memory);

        fileReader.readState();
        System.out.println(WELCOME_MESSAGE);

        if (!fileReader.isStateRestored()) {
            memory.clear();
            System.out.println(STATE_LOAD_MESSAGE);
        }

        option = selector.chooseMainMenuOperation();

        while (option != MAIN_MENU_OPTION_AMOUNT) {
            if (option == 1) {
                processAdminAction(selector, reader, memory);
            } else if (option == 2) {
                processUserAction(selector, reader, memory);
            }

            option = selector.chooseMainMenuOperation();
        }

        fileWriter.writeState();
        scanner.close();
    }

    private static void processAdminAction(MenuSelector selector, DataReader reader, Memory<Reservation> memory) {
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

    private static void processUserAction(MenuSelector selector, DataReader reader, Memory<Reservation> memory) {
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
