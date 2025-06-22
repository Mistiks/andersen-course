package service;

import entity.WorkSpace;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class WorkSpaceServiceJDBCTest {

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @InjectMocks
    WorkSpaceServiceJDBC workSpaceService;

    @Test
    void givenNewWorkSpace_whenAdded_thenSuccess() throws SQLException {
        WorkSpace workSpace = new WorkSpace(1, "open space", 10, true);

        when(connection.prepareStatement(String.format("INSERT INTO workspace VALUES (%d, %s, %d, %b)",
                workSpace.getId(), workSpace.getType(), workSpace.getPrice(), workSpace.getAvailability()))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(1, workSpaceService.addNewWorkspace(workSpace));
    }
}
