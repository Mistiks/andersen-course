package logic;

import entity.Reservation;
import entity.WorkSpace;
import service.api.IReservationService;
import service.api.IWorkSpaceService;

import java.util.*;
import java.util.stream.Collectors;

public class ActionHandler {

    private final IWorkSpaceService workSpaceService;
    private final IReservationService reservationService;

    public ActionHandler(IWorkSpaceService workSpaceService, IReservationService reservationService) {
        this.workSpaceService = workSpaceService;
        this.reservationService = reservationService;
    }

    public int addNewWorkspace(WorkSpace space) {
        return workSpaceService.addNewWorkspace(space);
    }

    public int addReservation(Reservation reservation) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(reservation.getSpaceId());

        if (workSpace.isPresent()) {
            int status = reservationService.addNewReservation(reservation);

            if (status == 1) {
                workSpace.get().setAvailability(false);
                workSpaceService.updateWorkSpace(workSpace.get());
            }

            return status;
        }

        return 0;
    }

    public int deleteWorkSpace(int spaceId) {
        reservationService.deleteReservationByWorkSpaceId(spaceId);
        return workSpaceService.deleteWorkspace(spaceId);
    }

    public int deleteReservation(int reservationId) {
        Optional<Reservation> reservation = reservationService.getReservationById(reservationId);

        if (reservation.isEmpty()) {
            return 0;
        }

        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(reservation.get().getSpaceId());

        if (workSpace.isPresent()) {
            workSpace.get().setAvailability(true);
            workSpaceService.updateWorkSpace(workSpace.get());
        }

        return reservationService.deleteReservation(reservationId);
    }

    public String getAllWorkSpaces() {
        List<WorkSpace> workSpaceList = workSpaceService.getAllWorkSpaces();
        String workspacesData = workSpaceList
                .stream()
                .sorted(Comparator.comparing(WorkSpace::getId))
                .map(WorkSpace::toString)
                .collect(Collectors.joining("\n", "", "\n"));

        if (workspacesData.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workspacesData;
    }

    public String getAvailableWorkSpaces() {
        List<WorkSpace> workSpaceList = workSpaceService.getAllWorkSpaces();
        String workspacesData = workSpaceList
                .stream()
                .filter(WorkSpace::getAvailability)
                .sorted(Comparator.comparing(WorkSpace::getId))
                .map(WorkSpace::toString)
                .collect(Collectors.joining("\n", "", "\n"));

        if (workspacesData.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workspacesData;
    }

    public String getAllReservations() {
        List<Reservation> reservationList = reservationService.getAllReservations();
        String reservationsData = reservationList.stream()
                .map(Reservation::toString)
                .collect(Collectors.joining("\n", "", "\n"));

        if (reservationsData.isEmpty()) {
            return "Reservations not found!\n";
        }

        return reservationsData;
    }

    public int updateWorkSpace(WorkSpace updatedSpace) {
        return workSpaceService.updateWorkSpace(updatedSpace);
    }
}
