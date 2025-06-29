package service;

import entity.Reservation;
import entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import service.api.IReservationService;
import service.api.IWorkSpaceService;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class ReservationServiceHibernate implements IReservationService {

    private final IWorkSpaceService workSpaceService;
    private final EntityManager entityManager;

    public ReservationServiceHibernate(IWorkSpaceService workSpaceService) {
        this.workSpaceService = workSpaceService;
        entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public int addNewReservation(Reservation reservation) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(reservation.getSpaceId());
        EntityTransaction transaction;

        if (workSpace.isEmpty()) {
            return 0;
        }

        reservation = new Reservation(reservation.getId(), workSpace.get(), reservation.getClientName(),
                reservation.getDate(), reservation.getTimeStart(), reservation.getTimeEnd());

        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(reservation);
        transaction.commit();

        return 1;
    }

    @Override
    public Optional<Reservation> getReservationById(int id) {
        Reservation reservation = entityManager.find(Reservation.class, id);

        if (reservation != null) {
            return Optional.of(reservation);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return entityManager.createQuery("SELECT e FROM Reservation e", Reservation.class).getResultList();
    }

    @Override
    public int deleteReservation(int id) {
        Optional<Reservation> reservation = getReservationById(id);

        if (reservation.isPresent()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(reservation.get());

            entityManager.flush();
            transaction.commit();
            return 1;
        }

        return 0;
    }

    @Override
    public int deleteReservationByWorkSpaceId(int spaceId) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(spaceId);
        List<Reservation> reservationList;
        EntityTransaction transaction;

        if (workSpace.isEmpty()) {
            return 0;
        }

        reservationList = getAllReservationsByWorkSpaceId(spaceId);

        transaction = entityManager.getTransaction();
        transaction.begin();

        for (Reservation reservation : reservationList) {
            entityManager.remove(reservation);
        }

        entityManager.flush();
        transaction.commit();

        return reservationList.size();
    }

    private List<Reservation> getAllReservationsByWorkSpaceId(int spaceId) {
        return entityManager.createQuery("SELECT e FROM Reservation e WHERE e.space_id = :id", Reservation.class)
                .setParameter("id", spaceId)
                .getResultList();
    }
}
