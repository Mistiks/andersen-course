package state;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import entity.Reservation;
import logic.Memory;

public class StateFileWriter {

    private Memory<Reservation> memory;
    private String reservationPath;
    private String workSpacePath;

    public StateFileWriter(String reservationPath, String workSpacePath, Memory<Reservation> memory) {
        this.reservationPath = reservationPath;
        this.workSpacePath = workSpacePath;
        this.memory = memory;
    }

    public void writeState() {
        try (ObjectOutputStream reservationStream = new ObjectOutputStream(new FileOutputStream(reservationPath));
             ObjectOutputStream workSpaceStream = new ObjectOutputStream(new FileOutputStream(workSpacePath))) {

            reservationStream.writeObject(memory.getReservationList());
            workSpaceStream.writeObject(memory.getWorkSpaceMap());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
