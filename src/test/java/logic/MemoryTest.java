package logic;

import entity.Reservation;
import entity.WorkSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {

    private Memory<Reservation> memory;

    @BeforeEach
    void prepareMemoryInstance() {
        memory = new Memory<>();
    }

    @Test
    void givenWorkSpaceWithNewId_whenAdded_thenSuccess() {
        WorkSpace testWorkSpace = new WorkSpace(1, "open space", 10, true);

        memory.addWorkSpace(testWorkSpace);
        assertEquals(1, memory.getWorkSpaceMap().size());
    }

    @Test
    void givenWorkSpaceWithSameId_whenAdded_doNothing() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        WorkSpace workSpaceWithSameId = new WorkSpace(1, "open space", 20, true);

        memory.addWorkSpace(workSpace);
        memory.addWorkSpace(workSpaceWithSameId);
        assertNotEquals(memory.getWorkSpaceMap().get(workSpace.getId()).getPrice(), workSpaceWithSameId.getPrice());
    }

    @Test
    void givenValidReservation_whenAdded_thenSuccess() {
        Reservation reservation = new Reservation(1, "Client", "10.01", "15:00", "16:00");
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        memory.addWorkSpace(workSpace);
        memory.addReservation(reservation);

        assertEquals(1, memory.getReservationList().size());
        assertFalse(memory.getWorkSpaceMap().get(1).getAvailability());
    }

    @Test
    void givenValidReservationWithoutWorkSpace_whenAdded_doNothing() {
        Reservation reservation = new Reservation(1, "Client", "10.01", "15:00", "16:00");

        memory.addReservation(reservation);
        assertEquals(0, memory.getReservationList().size());
    }

    @Test
    void givenValidWorkSpaceId_whenDeleted_thenSuccess() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        memory.addWorkSpace(workSpace);
        memory.deleteWorkSpace(workSpace.getId());

        assertEquals(0, memory.getWorkSpaceMap().size());
    }

    @Test
    void givenInvalidWorkSpaceId_whenDeleted_doNothing() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        memory.addWorkSpace(workSpace);
        memory.deleteWorkSpace(2);

        assertEquals(1, memory.getWorkSpaceMap().size());
    }

    @Test
    void givenValidReservationId_whenDeleted_thenSuccess() {
        Reservation reservation = new Reservation(1, "Client", "10.01", "15:00", "16:00");
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        memory.addWorkSpace(workSpace);
        memory.addReservation(reservation);
        memory.deleteReservation(reservation.getId());

        assertEquals(0, memory.getReservationList().size());
        assertTrue(memory.getWorkSpaceMap().get(1).getAvailability());
    }

    @Test
    void givenInvalidReservationId_whenDeleted_doNothing() {
        Reservation reservation = new Reservation(1, "Client", "10.01", "15:00", "16:00");
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        memory.addWorkSpace(workSpace);
        memory.addReservation(reservation);
        memory.deleteReservation(2);

        assertEquals(1, memory.getReservationList().size());
    }

    @Test
    void givenValidWorkSpace_whenUpdated_thenSuccess() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        WorkSpace updatedWorkSpace = new WorkSpace(1, "closed space", 20, true);

        memory.addWorkSpace(workSpace);
        memory.updateWorkSpace(updatedWorkSpace);

        assertEquals(updatedWorkSpace.getType(), memory.getWorkSpaceMap().get(1).getType());
        assertEquals(updatedWorkSpace.getPrice(), memory.getWorkSpaceMap().get(1).getPrice());
    }

    @Test
    void givenInvalidWorkSpace_whenUpdated_doNothing() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        WorkSpace updatedWorkSpace = new WorkSpace(2, "closed space", 20, true);

        memory.addWorkSpace(workSpace);
        memory.updateWorkSpace(updatedWorkSpace);

        assertNotEquals(updatedWorkSpace.getType(), memory.getWorkSpaceMap().get(1).getType());
        assertNotEquals(updatedWorkSpace.getPrice(), memory.getWorkSpaceMap().get(1).getPrice());
    }
}
