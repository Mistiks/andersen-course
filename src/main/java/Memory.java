package main.java;

import main.java.entity.Reservation;
import main.java.entity.WorkSpace;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Memory {

    private List<Reservation> reservationList = new ArrayList<>();
    private List<WorkSpace> workspaceList = new ArrayList<>();

    public void addWorkSpace(WorkSpace space) {
        workspaceList.add(space);
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public List<WorkSpace> getWorkspaceList() {
        return workspaceList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;

        if (!this.reservationList.isEmpty()) {
            Reservation.setCounter(this.reservationList.get(reservationList.size() - 1).getId());
        }
    }

    public void setWorkspaceList(List<WorkSpace> workspaceList) {
        this.workspaceList = workspaceList;
    }

    private Optional<WorkSpace> getWorkSpaceById(int spaceId) {
        return workspaceList.stream().filter(i -> i.getId() == spaceId).findFirst();
    }

    private Optional<Reservation> getReservationById(int reservationId) {
        return reservationList.stream().filter(i -> i.getId() == reservationId).findFirst();
    }

    public void deleteWorkSpace(int spaceId) {
        Optional<WorkSpace> space = getWorkSpaceById(spaceId);

        space.ifPresent(workSpace -> workspaceList.remove(workSpace));
        reservationList.removeIf(reservation -> reservation.getSpaceId() == spaceId);
    }

    public void deleteReservation(int reservationId) {
        Optional<Reservation> reservation = getReservationById(reservationId);
        Optional<WorkSpace> workSpace;
        WorkSpace space;

        if (reservation.isPresent()) {
            workSpace = getWorkSpaceById(reservation.get().getSpaceId());
            reservationList.remove(reservation.get());

            if (workSpace.isPresent()) {
                space = workSpace.get();
                space.setAvailability(true);
                updateWorkSpace(space);
            }
        }
    }

    public String getAllWorkSpaces() {
        StringBuilder workSpaceView = new StringBuilder();

        for (WorkSpace space : workspaceList) {
            workSpaceView.append(space.toString()).append("\n");
        }

        if (workSpaceView.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workSpaceView.toString();
    }

    public String getAvailableWorkSpaces() {
        StringBuilder workSpaceView = new StringBuilder();
        List<WorkSpace> availableSpaces = workspaceList.stream().filter(WorkSpace::getAvailability).toList();

        for (WorkSpace space : availableSpaces) {
            workSpaceView.append(space.toString()).append("\n");
        }

        if (workSpaceView.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workSpaceView.toString();
    }

    public String getAllReservations() {
        StringBuilder reservationsView = new StringBuilder();

        for (Reservation reservation : reservationList) {
            reservationsView.append(reservation.toString()).append("\n");
        }

        if (reservationsView.isEmpty()) {
            return "Reservations not found!\n";
        }

        return reservationsView.toString();
    }

    public void addReservation(Reservation reservation) {
        Optional<WorkSpace> space = getWorkSpaceById(reservation.getSpaceId());
        WorkSpace updatedSpace;

        if (space.isPresent()) {
            reservationList.add(reservation);
            updatedSpace = space.get();
            updatedSpace.setAvailability(false);
            updateWorkSpace(updatedSpace);
        }
    }

    public void updateWorkSpace(WorkSpace updatedSpace) {
        for (WorkSpace space : workspaceList) {
            if (space.getId() == updatedSpace.getId()) {
                space.setType(updatedSpace.getType());
                space.setPrice(updatedSpace.getPrice());
                space.setAvailability(updatedSpace.getAvailability());
            }
        }
    }
}
