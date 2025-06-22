package service.api;

import entity.Reservation;
import java.util.List;
import java.util.Optional;

public interface IReservationService {

    int addNewReservation(Reservation reservation);
    Optional<Reservation> getReservationById(int id);
    List<Reservation> getAllReservations();
    int deleteReservation(int id);
    int deleteReservationByWorkSpaceId(int spaceId);
}
