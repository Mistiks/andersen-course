package state;

import entity.Reservation;
import logic.Memory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateFileWriterTest {

    private static final String WORKSPACE_PATH = "src/test/resources/workspace_write.ser";
    private static final String RESERVATION_PATH = "src/test/resources/reservation_write.ser";
    private StateFileWriter writer;
    private File workspaceFile = new File(WORKSPACE_PATH);
    private File reservationFile = new File(RESERVATION_PATH);

    @Mock
    private Memory<Reservation> memory;

    @Test
    void givenData_whenCreatingFiles_thenSuccess() {
        writer = new StateFileWriter(RESERVATION_PATH, WORKSPACE_PATH, memory);
        when(memory.getReservationList()).thenReturn(new ArrayList<>());
        when(memory.getWorkSpaceMap()).thenReturn(new HashMap<>());

        writer.writeState();

        assertTrue(workspaceFile.exists());
        assertTrue(reservationFile.exists());
    }

    @AfterAll
    static void cleanUp() {
        try {
            Files.deleteIfExists(Path.of(WORKSPACE_PATH));
            Files.deleteIfExists(Path.of(RESERVATION_PATH));
        } catch (IOException e) {
            System.err.println("Error during file deletion!");
        }
    }
}
