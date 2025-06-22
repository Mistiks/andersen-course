package logic;

import entity.Reservation;
import entity.WorkSpace;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataReaderTest {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Test
    void givenNewWorkSpace_whenReceivingInput_thenSuccess() {
        WorkSpace resultSpace;
        WorkSpace testWorkSpace = new WorkSpace(1, "open space", 10, true);
        String input = testWorkSpace.getId() + "\n" + testWorkSpace.getType() + "\n" + testWorkSpace.getPrice() + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        resultSpace = reader.getNewSpace();

        assertEquals(resultSpace.getId(), testWorkSpace.getId());
        assertEquals(resultSpace.getType(), testWorkSpace.getType());
        assertEquals(resultSpace.getPrice(), testWorkSpace.getPrice());
        assertEquals(resultSpace.getAvailability(), testWorkSpace.getAvailability());
    }

    @Test
    void givenNewWorkSpace_whenReceivingIncorrectInput_thenSkipErrorsThenSuccess() {
        WorkSpace resultSpace;
        WorkSpace testWorkSpace = new WorkSpace(1, "open space", 10, true);
        String input = "qk" + "\n" + testWorkSpace.getId() + "\n" + testWorkSpace.getType() + "\n"
                + "4d" + "\n" + testWorkSpace.getPrice() + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        resultSpace = reader.getNewSpace();

        assertEquals(resultSpace.getId(), testWorkSpace.getId());
        assertEquals(resultSpace.getType(), testWorkSpace.getType());
        assertEquals(resultSpace.getPrice(), testWorkSpace.getPrice());
        assertEquals(resultSpace.getAvailability(), testWorkSpace.getAvailability());
    }

    @Test
    void givenUpdatedWorkSpace_whenReceivingInput_thenSuccess() {
        WorkSpace resultSpace;
        WorkSpace testWorkSpace = new WorkSpace(1, "open space", 10, false);
        String input = testWorkSpace.getId() + "\n" + testWorkSpace.getType() + "\n" + testWorkSpace.getPrice() + "\n"
                + testWorkSpace.getAvailability() + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        resultSpace = reader.getUpdatedSpace();

        assertEquals(resultSpace.getId(), testWorkSpace.getId());
        assertEquals(resultSpace.getType(), testWorkSpace.getType());
        assertEquals(resultSpace.getPrice(), testWorkSpace.getPrice());
        assertEquals(resultSpace.getAvailability(), testWorkSpace.getAvailability());
    }

    @Test
    void givenUpdatedWorkSpace_whenReceivingIncorrectInput_thenSkipErrorsThenSuccess() {
        WorkSpace resultSpace;
        WorkSpace testWorkSpace = new WorkSpace(1, "open space", 10, false);
        String input = "qk" + "\n" + testWorkSpace.getId() + "\n" + testWorkSpace.getType() + "\n"
                + "4d" + "\n" + testWorkSpace.getPrice() + "\n"
                + "wer" + "\n" + testWorkSpace.getAvailability() + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        resultSpace = reader.getUpdatedSpace();

        assertEquals(resultSpace.getId(), testWorkSpace.getId());
        assertEquals(resultSpace.getType(), testWorkSpace.getType());
        assertEquals(resultSpace.getPrice(), testWorkSpace.getPrice());
        assertEquals(resultSpace.getAvailability(), testWorkSpace.getAvailability());
    }

    @Test
    void givenNewReservation_whenReceivingInput_thenSuccess() {
        Reservation reservation;
        Reservation testReservation = new Reservation(1, 2,"Client",
                LocalDate.of(2000, 1, 1), LocalTime.MIDNIGHT, LocalTime.NOON);
        String input = testReservation.getId() + "\n" + testReservation.getSpaceId() + "\n"
                + testReservation.getClientName() + "\n" + testReservation.getDate().format(dateFormatter) + "\n"
                + testReservation.getTimeStart().format(timeFormatter) + "\n"
                + testReservation.getTimeEnd().format(timeFormatter) + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        reservation = reader.getNewReservation();

        assertEquals(reservation.getId(), testReservation.getId());
        assertEquals(reservation.getSpaceId(), testReservation.getSpaceId());
        assertEquals(reservation.getClientName(), testReservation.getClientName());
        assertEquals(reservation.getDate(), testReservation.getDate());
        assertEquals(reservation.getTimeStart(), testReservation.getTimeStart());
        assertEquals(reservation.getTimeEnd(), testReservation.getTimeEnd());
    }

    @Test
    void givenNewReservation_whenReceivingIncorrectInput_thenSkipErrorsThenSuccess() {
        Reservation reservation;
        Reservation testReservation = new Reservation(1, 2,"Client",
                LocalDate.of(2000, 1, 1), LocalTime.MIDNIGHT, LocalTime.NOON);
        String input = "qk" + "\n" + testReservation.getId() + "\n"
                + "vjd45" + "\n" + testReservation.getSpaceId() + "\n"
                + testReservation.getClientName() + "\n"
                + "45.fde" + "\n" + testReservation.getDate().format(dateFormatter) + "\n"
                + "gj45" + "\n" + testReservation.getTimeStart().format(timeFormatter) + "\n"
                + "123:456" + "\n" + testReservation.getTimeEnd().format(timeFormatter) + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        reservation = reader.getNewReservation();

        assertEquals(reservation.getId(), testReservation.getId());
        assertEquals(reservation.getSpaceId(), testReservation.getSpaceId());
        assertEquals(reservation.getClientName(), testReservation.getClientName());
        assertEquals(reservation.getDate(), testReservation.getDate());
        assertEquals(reservation.getTimeStart(), testReservation.getTimeStart());
        assertEquals(reservation.getTimeEnd(), testReservation.getTimeEnd());
    }
}
