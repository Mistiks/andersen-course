package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reservation {

    private final int id;
    private int spaceId;
    private String clientName;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Reservation(int id, int spaceId, String clientName, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this.id = id;
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

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return String.format("Reservation №%d of workspace with id %d by %s on %s. Start: %s. End: %s",
                id, spaceId, clientName,
                dateFormatter.format(date), timeFormatter.format(timeStart), timeFormatter.format(timeEnd));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reservation that)) return false;
        return getId() == that.getId() && getSpaceId() == that.getSpaceId()
                && Objects.equals(getClientName(), that.getClientName())
                && Objects.equals(getDate(), that.getDate()) && Objects.equals(getTimeStart(), that.getTimeStart())
                && Objects.equals(getTimeEnd(), that.getTimeEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSpaceId(), getClientName(), getDate(),
                getTimeStart(), getTimeEnd(), dateFormatter, timeFormatter);
    }
}
