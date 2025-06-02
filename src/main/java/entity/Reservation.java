package main.java.entity;

import java.io.Serializable;

public class Reservation implements Serializable {

    private static int counter = 0;
    private int id;
    private int spaceId;
    private String clientName;
    private String date;
    private String timeStart;
    private String timeEnd;

    public Reservation(int spaceId, String clientName, String date, String timeStart, String timeEnd) {
        id = ++counter;
        this.spaceId = spaceId;
        this.clientName = clientName;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return id;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public static void setCounter(int counter) {
        Reservation.counter = counter;
    }

    @Override
    public String toString() {
        return String.format("Reservation №%d of workspace with id %d by %s on %s. Start: %s. End: %s",
                id, spaceId, clientName, date, timeStart, timeEnd);
    }
}
