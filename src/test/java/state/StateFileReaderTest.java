package state;

import entity.Reservation;
import logic.Memory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class StateFileReaderTest {

    private static final String WORKSPACE_PATH = "src/test/resources/workspace_read.ser";
    private static final String RESERVATION_PATH = "src/test/resources/reservation_read.ser";
    private StateFileReader reader;

    @Mock
    private Memory<Reservation> memory;

    @Test
    void givenCorrectFiles_whenExecuted_thenSuccess() {
        reader = new StateFileReader(RESERVATION_PATH, WORKSPACE_PATH, memory);
        doNothing().when(memory).setReservationList(anyList());
        doNothing().when(memory).setWorkSpaceMap(anyMap());

        reader.readState();

        assertTrue(reader.isStateRestored());
    }

    @Test
    void givenIncorrectFiles_whenExecuted_thenNoLoad() {
        reader = new StateFileReader(RESERVATION_PATH + "a", WORKSPACE_PATH + "a", memory);
        reader.readState();

        assertFalse(reader.isStateRestored());
    }
}
