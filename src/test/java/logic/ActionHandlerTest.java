package logic;

import entity.Reservation;
import entity.WorkSpace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.api.IReservationService;
import service.api.IWorkSpaceService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionHandlerTest {

    @Mock
    IWorkSpaceService workSpaceService;

    @Mock
    IReservationService reservationService;

    @InjectMocks
    private ActionHandler actionHandler;

    @Test
    void givenNewWorkSpace_whenAdded_thenSuccess() {
        WorkSpace testWorkSpace = new WorkSpace(1, "open space", 10, true);

        when(workSpaceService.addNewWorkspace(testWorkSpace)).thenReturn(1);

        assertEquals(1, actionHandler.addNewWorkspace(testWorkSpace));
    }

    @Test
    void givenWorkSpaceWithSameId_whenAdded_doNothing() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        WorkSpace workSpaceWithSameId = new WorkSpace(1, "open space", 20, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(workSpaceService.addNewWorkspace(workSpaceWithSameId)).thenReturn(0);

        actionHandler.addNewWorkspace(workSpace);
        assertEquals(0, actionHandler.addNewWorkspace(workSpaceWithSameId));
    }

    @Test
    void givenValidReservation_whenAdded_thenSuccess() {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(workSpaceService.getWorkSpaceById(reservation.getSpaceId())).thenReturn(Optional.of(workSpace));
        when(reservationService.addNewReservation(reservation)).thenReturn(1);

        actionHandler.addNewWorkspace(workSpace);
        assertEquals(1, actionHandler.addReservation(reservation));
    }

    @Test
    void givenValidReservationWithoutWorkSpace_whenAdded_doNothing() {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));

        assertEquals(0, actionHandler.addReservation(reservation));
    }

    @Test
    void givenValidWorkSpaceId_whenDeleted_thenSuccess() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(workSpaceService.deleteWorkspace(workSpace.getId())).thenReturn(1);

        actionHandler.addNewWorkspace(workSpace);

        assertEquals(1, actionHandler.deleteWorkSpace(workSpace.getId()));
    }

    @Test
    void givenInvalidWorkSpaceId_whenDeleted_doNothing() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(workSpaceService.deleteWorkspace(2)).thenReturn(0);

        actionHandler.addNewWorkspace(workSpace);

        assertEquals(0, actionHandler.deleteWorkSpace(2));
    }

    @Test
    void givenValidReservationId_whenDeleted_thenSuccess() {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(reservationService.getReservationById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(reservationService.deleteReservation(reservation.getId())).thenReturn(1);

        actionHandler.addNewWorkspace(workSpace);
        actionHandler.addReservation(reservation);

        assertEquals(1, actionHandler.deleteReservation(reservation.getId()));
    }

    @Test
    void givenInvalidReservationId_whenDeleted_doNothing() {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);

        actionHandler.addNewWorkspace(workSpace);
        actionHandler.addReservation(reservation);

        assertEquals(0, actionHandler.deleteReservation(2));
    }

    @Test
    void givenValidWorkSpace_whenUpdated_thenSuccess() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        WorkSpace updatedWorkSpace = new WorkSpace(1, "closed space", 20, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(workSpaceService.updateWorkSpace(updatedWorkSpace)).thenReturn(1);

        actionHandler.addNewWorkspace(workSpace);

        assertEquals(1, actionHandler.updateWorkSpace(updatedWorkSpace));
    }

    @Test
    void givenInvalidWorkSpace_whenUpdated_doNothing() {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        WorkSpace updatedWorkSpace = new WorkSpace(2, "closed space", 20, true);

        when(workSpaceService.addNewWorkspace(workSpace)).thenReturn(1);
        when(workSpaceService.updateWorkSpace(updatedWorkSpace)).thenReturn(0);

        actionHandler.addNewWorkspace(workSpace);

        assertEquals(0, actionHandler.updateWorkSpace(updatedWorkSpace));
    }
}
