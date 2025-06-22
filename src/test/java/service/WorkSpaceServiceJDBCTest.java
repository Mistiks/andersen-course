package service;

import entity.WorkSpace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkSpaceServiceJDBCTest {

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @InjectMocks
    WorkSpaceServiceJDBC workSpaceService;


    @Test
    void givenNewWorkSpace_whenAdded_thenSuccess() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(connection.prepareStatement("INSERT INTO workspace VALUES (?, ?, ?, ?)")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, workSpaceService.addNewWorkspace(workSpace));
    }

    @Test
    void givenExistingWorkSpace_whenAdded_thenFail() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(connection.prepareStatement("INSERT INTO workspace VALUES (?, ?, ?, ?)")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertEquals(0, workSpaceService.addNewWorkspace(workSpace));
    }

    @Test
    void givenValidWorkSpaceId_whenRetrieved_thenSuccess() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        Optional<WorkSpace> receivedWorkSpace;

        when(connection.prepareStatement("SELECT id, type, price, availability FROM workspace WHERE id = ?")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("type")).thenReturn("open space");
        when(resultSet.getInt("price")).thenReturn(10);
        when(resultSet.getBoolean("availability")).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        receivedWorkSpace = workSpaceService.getWorkSpaceById(1);

        assertTrue(receivedWorkSpace.isPresent());
        assertEquals(Optional.of(workSpace).get().getId(), receivedWorkSpace.get().getId());
        assertEquals(Optional.of(workSpace).get().getType(), receivedWorkSpace.get().getType());
        assertEquals(Optional.of(workSpace).get().getPrice(), receivedWorkSpace.get().getPrice());
        assertEquals(Optional.of(workSpace).get().getAvailability(), receivedWorkSpace.get().getAvailability());
    }

    @Test
    void givenInvalidWorkSpaceId_whenRetrieved_thenEmpty() throws SQLException {
        Optional<WorkSpace> receivedWorkSpace;

        when(connection.prepareStatement("SELECT id, type, price, availability FROM workspace WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        receivedWorkSpace = workSpaceService.getWorkSpaceById(2);

        assertTrue(receivedWorkSpace.isEmpty());
    }

    @Test
    void whenRetrieveAllWorkSpaces_thenSuccess() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);
        List<WorkSpace> workSpaceList = List.of(workSpace);

        when(connection.prepareStatement("SELECT workspace.id, workspace.type, workspace.price, " +
                "workspace.availability from workspace")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("type")).thenReturn("open space");
        when(resultSet.getInt("price")).thenReturn(10);
        when(resultSet.getBoolean("availability")).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        assertIterableEquals(workSpaceList, workSpaceService.getAllWorkSpaces());
    }

    @Test
    void givenValidUpdatedWorkSpace_whenUpdated_thenSuccess() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(connection.prepareStatement("UPDATE workspace SET type = ?, price = ?, " +
                "availability = ? WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, workSpaceService.updateWorkSpace(workSpace));
    }

    @Test
    void givenInvalidUpdatedWorkSpace_whenUpdated_thenFail() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(connection.prepareStatement("UPDATE workspace SET type = ?, price = ?, " +
                "availability = ? WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertEquals(0, workSpaceService.updateWorkSpace(workSpace));
    }

    @Test
    void givenValidWorkSpaceId_whenDeleted_thenSuccess() throws SQLException {
        when(connection.prepareStatement("DELETE FROM workspace WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, workSpaceService.deleteWorkspace(1));
    }

    @Test
    void givenInvalidWorkSpaceId_whenDeleted_thenFail() throws SQLException {
        when(connection.prepareStatement("DELETE FROM workspace WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertEquals(0, workSpaceService.deleteWorkspace(1));
    }
}
