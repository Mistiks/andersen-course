package service;

import entity.Reservation;
import entity.WorkSpace;
import service.api.IReservationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationServiceJDBC implements IReservationService {

    private final Connection connection;

    public ReservationServiceJDBC(Connection connection) {
        this.connection = connection;
    }

    public int addNewReservation(Reservation reservation) {
        int affectedRowAmount = 0;

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO reservation VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, reservation.getId());
            statement.setInt(2, reservation.getSpaceId());
            statement.setString(3, reservation.getClientName());
            statement.setObject(4, reservation.getDate());
            statement.setObject(5, reservation.getTimeStart());
            statement.setObject(6, reservation.getTimeEnd());
            affectedRowAmount = statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return affectedRowAmount;
    }

    public Optional<Reservation> getReservationById(int id) {
        Optional<Reservation> reservation = Optional.empty();
        Reservation currentReservation;
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement("SELECT id, space_id, client_name, " +
                "reservation_date, time_start, time_end FROM reservation WHERE id = ?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                currentReservation = new Reservation(resultSet.getInt("id"),
                        resultSet.getInt("space_id"),
                        resultSet.getString("client_name"),
                        resultSet.getObject("reservation_date", LocalDate.class),
                        resultSet.getObject("time_start", LocalTime.class),
                        resultSet.getObject("time_end", LocalTime.class));
                reservation = Optional.of(currentReservation);
            }
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return reservation;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        Reservation currentReservation;
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement("SELECT id, space_id, client_name, " +
                "reservation_date, time_start, time_end FROM reservation")) {
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                currentReservation = new Reservation(resultSet.getInt("id"), 
                        resultSet.getInt("space_id"),
                        resultSet.getString("client_name"),
                        resultSet.getObject("reservation_date", LocalDate.class),
                        resultSet.getObject("time_start", LocalTime.class),
                        resultSet.getObject("time_end", LocalTime.class));
                reservationList.add(currentReservation);
            }
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return reservationList;
    }

    public int deleteReservation(int id) {
        int affectedRowAmount = 0;

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM reservation WHERE id = ?")) {
            statement.setInt(1, id);
            affectedRowAmount = statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return affectedRowAmount;
    }

    public int deleteReservationByWorkSpaceId(int spaceId) {
        int affectedRowAmount = 0;

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM reservation WHERE space_id = ?")) {
            statement.setInt(1, spaceId);
            affectedRowAmount = statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return affectedRowAmount;
    }
}
