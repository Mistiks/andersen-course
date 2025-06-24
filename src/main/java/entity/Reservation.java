package entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    private int id;

    @Transient
    private int spaceId;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private WorkSpace workSpace;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "reservation_date")
    private LocalDate date;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @Transient
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Transient
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Reservation() {}

    public Reservation(int id, int spaceId, String clientName, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this.id = id;
        this.spaceId = spaceId;
        this.clientName = clientName;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public Reservation(int id, WorkSpace workSpace, String clientName, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this.id = id;
        this.clientName = clientName;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.workSpace = workSpace;
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

    public WorkSpace getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(WorkSpace workSpace) {
        this.workSpace = workSpace;
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
