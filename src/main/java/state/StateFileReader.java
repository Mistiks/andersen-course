package main.java.state;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import main.java.Memory;
import main.java.entity.Reservation;
import main.java.entity.WorkSpace;

public class StateFileReader {

    private boolean isStateRestored = true;
    private Memory memory;
    private String reservationPath;
    private String workSpacePath;

    public StateFileReader(String reservationPath, String workSpacePath, Memory memory) {
        this.reservationPath = reservationPath;
        this.workSpacePath = workSpacePath;
        this.memory = memory;
    }

    public boolean isStateRestored() {
        return isStateRestored;
    }

    public void readState() {
        readReservationState();
        readWorkSpaceState();
    }
    
    private void readReservationState() {
        try (ObjectInputStream reservationStream = new ObjectInputStream(new FileInputStream(reservationPath))) {
            memory.setReservationList((List<Reservation>) reservationStream.readObject());
        } catch (EOFException | FileNotFoundException fileException) {
            System.out.println("No reservations in memory!");
            this.isStateRestored = false;
        }  catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void readWorkSpaceState() {
        try (ObjectInputStream workSpaceStream = new ObjectInputStream(new FileInputStream(workSpacePath))) {
            memory.setWorkSpaceMap((Map<Integer, WorkSpace>) workSpaceStream.readObject());
        } catch (EOFException | FileNotFoundException fileException) {
            System.out.println("No workspaces in memory!");
            this.isStateRestored = false;
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
