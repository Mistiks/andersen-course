package service;

import entity.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceJDBCTest {

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @InjectMocks
    ReservationServiceJDBC reservationService;

    @Test
    void givenNewReservation_whenAdded_thenSuccess() throws SQLException {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));

        when(connection.prepareStatement("INSERT INTO reservation VALUES (?, ?, ?, ?, ?, ?)")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, reservationService.addNewReservation(reservation));
    }

    @Test
    void givenExistingReservation_whenAdded_thenFail() throws SQLException {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));

        when(connection.prepareStatement("INSERT INTO reservation VALUES (?, ?, ?, ?, ?, ?)")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertEquals(0, reservationService.addNewReservation(reservation));
    }

    @Test
    void givenValidReservationId_whenRetrieved_thenSuccess() throws SQLException {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));
        Optional<Reservation> receivedReservation;

        when(connection.prepareStatement("SELECT id, space_id, client_name, " +
                "reservation_date, time_start, time_end FROM reservation WHERE id = ?")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(reservation.getId());
        when(resultSet.getInt("space_id")).thenReturn(reservation.getSpaceId());
        when(resultSet.getString("client_name")).thenReturn(reservation.getClientName());
        when(resultSet.getObject("reservation_date", LocalDate.class)).thenReturn(reservation.getDate());
        when(resultSet.getObject("time_start", LocalTime.class)).thenReturn(reservation.getTimeStart());
        when(resultSet.getObject("time_end", LocalTime.class)).thenReturn(reservation.getTimeEnd());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        receivedReservation = reservationService.getReservationById(1);

        assertTrue(receivedReservation.isPresent());
    }

    @Test
    void givenInValidReservationId_whenRetrieved_thenEmpty() throws SQLException {
        Optional<Reservation> receivedReservation;

        when(connection.prepareStatement("SELECT id, space_id, client_name, " +
                "reservation_date, time_start, time_end FROM reservation WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        receivedReservation = reservationService.getReservationById(1);

        assertTrue(receivedReservation.isEmpty());
    }

    @Test
    void whenRetrievedAllReservations_thenSuccess() throws SQLException {
        Reservation reservation = new Reservation(1, 1, "Client",
                LocalDate.of(2020, 1, 1), LocalTime.NOON, LocalTime.NOON.plusHours(1));
        List<Reservation> reservationList = List.of(reservation);

        when(connection.prepareStatement("SELECT id, space_id, client_name, " +
                "reservation_date, time_start, time_end FROM reservation")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(reservation.getId());
        when(resultSet.getInt("space_id")).thenReturn(reservation.getSpaceId());
        when(resultSet.getString("client_name")).thenReturn(reservation.getClientName());
        when(resultSet.getObject("reservation_date", LocalDate.class)).thenReturn(reservation.getDate());
        when(resultSet.getObject("time_start", LocalTime.class)).thenReturn(reservation.getTimeStart());
        when(resultSet.getObject("time_end", LocalTime.class)).thenReturn(reservation.getTimeEnd());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        assertIterableEquals(reservationList, reservationService.getAllReservations());
    }

    @Test
    void givenValidReservationId_whenDeleted_thenSuccess() throws SQLException {
        when(connection.prepareStatement("DELETE FROM reservation WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, reservationService.deleteReservation(1));
    }

    @Test
    void givenInValidReservationId_whenDeleted_thenFail() throws SQLException {
        when(connection.prepareStatement("DELETE FROM reservation WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertEquals(0, reservationService.deleteReservation(1));
    }

    @Test
    void givenValidWorkSpaceId_whenDeleted_thenSuccess() throws SQLException {
        when(connection.prepareStatement("DELETE FROM reservation WHERE space_id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, reservationService.deleteReservationByWorkSpaceId(1));
    }

    @Test
    void givenInvalidWorkSpaceId_whenDeleted_thenFail() throws SQLException {
        when(connection.prepareStatement("DELETE FROM reservation WHERE space_id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertEquals(0, reservationService.deleteReservationByWorkSpaceId(1));
    }
}
