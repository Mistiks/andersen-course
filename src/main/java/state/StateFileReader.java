package state;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import logic.Memory;
import entity.Reservation;
import entity.WorkSpace;

public class StateFileReader {

    private boolean isStateRestored = true;
    private Memory<Reservation> memory;
    private String reservationPath;
    private String workSpacePath;

    public StateFileReader(String reservationPath, String workSpacePath, Memory<Reservation> memory) {
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
            this.isStateRestored = false;
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
            this.isStateRestored = false;
        }
    }
}
