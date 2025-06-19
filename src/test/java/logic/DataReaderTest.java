package logic;

import entity.Reservation;
import entity.WorkSpace;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataReaderTest {

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
    void givenNewReservation_whenReceivingInput_thenSuccess() {
        Reservation reservation;
        Reservation testReservation = new Reservation(1, "Client", "01.01", "10:00", "15:00");
        String input = testReservation.getId() + "\n" + testReservation.getClientName() + "\n" + testReservation.getDate()
                + "\n" + testReservation.getTimeStart() + "\n" + testReservation.getTimeEnd() + "\n";
        DataReader reader = new DataReader(new Scanner(new ByteArrayInputStream(input.getBytes())));

        reservation = reader.getNewReservation();

        assertEquals(reservation.getSpaceId(), testReservation.getSpaceId());
        assertEquals(reservation.getClientName(), testReservation.getClientName());
        assertEquals(reservation.getDate(), testReservation.getDate());
        assertEquals(reservation.getTimeStart(), testReservation.getTimeStart());
        assertEquals(reservation.getTimeEnd(), testReservation.getTimeEnd());
    }
}
