package main.java.state;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import main.java.Memory;

public class StateFileWriter {

    private Memory memory;
    private String reservationPath;
    private String workSpacePath;

    public StateFileWriter(String reservationPath, String workSpacePath, Memory memory) {
        this.reservationPath = reservationPath;
        this.workSpacePath = workSpacePath;
        this.memory = memory;
    }

    public void writeState() {
        try (ObjectOutputStream reservationStream = new ObjectOutputStream(new FileOutputStream(reservationPath));
             ObjectOutputStream workSpaceStream = new ObjectOutputStream(new FileOutputStream(workSpacePath))) {

            reservationStream.writeObject(memory.getReservationList());
            workSpaceStream.writeObject(memory.getWorkspaceList());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("State was saved by class loaded with " + this.getClass().getClassLoader());
    }
}
