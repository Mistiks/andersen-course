package service;

import entity.WorkSpace;
import service.api.IWorkSpaceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkSpaceServiceJDBC implements IWorkSpaceService {

    private final Connection connection;

    public WorkSpaceServiceJDBC(Connection connection) {
        this.connection = connection;
    }

    public int addNewWorkspace(WorkSpace space) {
        int affectedRowAmount = 0;

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO workspace VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, space.getId());
            statement.setString(2, space.getType());
            statement.setInt(3, space.getPrice());
            statement.setBoolean(4, space.getAvailability());
            affectedRowAmount = statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return affectedRowAmount;
    }

    public Optional<WorkSpace> getWorkSpaceById(int id) {
        Optional<WorkSpace> workSpace = Optional.empty();
        WorkSpace currentSpace;
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement("SELECT id, type, price, availability " +
                "FROM workspace WHERE id = ?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                currentSpace = new WorkSpace(resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getInt("price"),
                        resultSet.getBoolean("availability"));
                workSpace = Optional.of(currentSpace);
            }
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return workSpace;
    }

    public List<WorkSpace> getAllWorkSpaces() {
        String allWorkSpacesStatement = "SELECT workspace.id, workspace.type, " +
                "workspace.price, workspace.availability from workspace";

        return getWorkSpaces(allWorkSpacesStatement);
    }

    private List<WorkSpace> getWorkSpaces(String query) {
        List<WorkSpace> workSpaceList = new ArrayList<>();
        WorkSpace currentSpace;
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                currentSpace = new WorkSpace(resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getInt("price"),
                        resultSet.getBoolean("availability"));
                workSpaceList.add(currentSpace);
            }
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return workSpaceList;
    }

    public int updateWorkSpace(WorkSpace space) {
        int affectedRowAmount = 0;

        try (PreparedStatement statement = connection.prepareStatement("UPDATE workspace SET type = ?, price = ?, " +
                "availability = ? WHERE id = ?")) {
            statement.setString(1, space.getType());
            statement.setInt(2, space.getPrice());
            statement.setBoolean(3, space.getAvailability());
            statement.setInt(4, space.getId());
            affectedRowAmount = statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return affectedRowAmount;
    }

    public int deleteWorkspace(int id) {
        int affectedRowAmount = 0;

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM workspace WHERE id = ?")) {
            statement.setInt(1, id);
            affectedRowAmount = statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.format("SQL State: %s\n%s", exception.getSQLState(), exception.getMessage());
        }

        return affectedRowAmount;
    }
}
