package service;

import entity.Reservation;
import service.api.IReservationService;

import java.util.List;
import java.util.Optional;

public class ReservationServiceHibernate implements IReservationService {

    @Override
    public int addNewReservation(Reservation reservation) {
        return 0;
    }

    @Override
    public Optional<Reservation> getReservationById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return List.of();
    }

    @Override
    public int deleteReservation(int id) {
        return 0;
    }

    @Override
    public int deleteReservationByWorkSpaceId(int spaceId) {
        return 0;
    }
}
