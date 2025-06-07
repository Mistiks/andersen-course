package main.java;

import main.java.entity.Reservation;
import main.java.entity.WorkSpace;
import java.util.*;

public class Memory<T extends Reservation> {

    private List<T> reservationList = new ArrayList<>();
    private Map<Integer, WorkSpace> workSpaceMap = new TreeMap<>();

    public void addWorkSpace(WorkSpace space) {
        if (workSpaceMap.containsKey(space.getId())) {
            System.out.println("Workspace with this id already exists. Operation was terminated!");
        } else {
            workSpaceMap.put(space.getId(), space);
        }
    }

    public void addReservation(T reservation) {
        Optional<WorkSpace> space = getWorkSpaceById(reservation.getSpaceId());
        WorkSpace updatedSpace;

        if (space.isPresent()) {
            reservationList.add(reservation);
            updatedSpace = space.get();
            updatedSpace.setAvailability(false);
            updateWorkSpace(updatedSpace);
        }
    }

    public List<T> getReservationList() {
        return reservationList;
    }

    public Map<Integer, WorkSpace> getWorkSpaceMap() {
        return workSpaceMap;
    }

    public void setReservationList(List<T> reservationList) {
        this.reservationList = reservationList;

        if (!this.reservationList.isEmpty()) {
            Reservation.setCounter(this.reservationList.get(reservationList.size() - 1).getId());
        }
    }

    public void setWorkSpaceMap(Map<Integer, WorkSpace> workSpaceMap) {
        this.workSpaceMap = workSpaceMap;
    }

    public void clear() {
        reservationList.clear();
        workSpaceMap.clear();
    }

    private Optional<WorkSpace> getWorkSpaceById(int spaceId) {
        return Optional.ofNullable(workSpaceMap.get(spaceId));
    }

    private Optional<T> getReservationById(int reservationId) {
        return reservationList.stream().filter(i -> i.getId() == reservationId).findFirst();
    }

    public void deleteWorkSpace(int spaceId) {
        Optional<WorkSpace> space = getWorkSpaceById(spaceId);

        space.ifPresent(workSpace -> workSpaceMap.remove(workSpace.getId()));
        reservationList.removeIf(reservation -> reservation.getSpaceId() == spaceId);
    }

    public void deleteReservation(int reservationId) {
        Optional<T> reservation = getReservationById(reservationId);
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

        for (var space : workSpaceMap.entrySet()) {
            workSpaceView.append(space.getValue().toString()).append("\n");
        }

        if (workSpaceView.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workSpaceView.toString();
    }

    public String getAvailableWorkSpaces() {
        StringBuilder workSpaceView = new StringBuilder();

        for (var space : workSpaceMap.entrySet()) {
            if (space.getValue().getAvailability()) {
                workSpaceView.append(space.getValue()).append("\n");
            }
        }

        if (workSpaceView.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workSpaceView.toString();
    }

    public String getAllReservations() {
        StringBuilder reservationsView = new StringBuilder();

        for (T reservation : reservationList) {
            reservationsView.append(reservation.toString()).append("\n");
        }

        if (reservationsView.isEmpty()) {
            return "Reservations not found!\n";
        }

        return reservationsView.toString();
    }

    public void updateWorkSpace(WorkSpace updatedSpace) {
        if (workSpaceMap.containsKey(updatedSpace.getId())) {
            workSpaceMap.put(updatedSpace.getId(), new WorkSpace(updatedSpace.getId(), updatedSpace.getType(),
                    updatedSpace.getPrice(), updatedSpace.getAvailability()));
        } else {
            System.out.println("Workspace with this id doesn't exist. Operation wasn't completed");
        }
    }
}
