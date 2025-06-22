package connection;

import exception.DatabaseConnectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JDBCConnectorTest {

    @Test
    void givenCorrectParameters_whenConnected_thenSuccess() {
        assertDoesNotThrow(() -> new JDBCConnector("jdbc:postgresql://127.0.0.1:5432/edu",
                "postgres", "password").getConnection().close());
    }

    @Test
    void givenIncorrectParameters_whenConnected_thenFail() {
        JDBCConnector connector = new JDBCConnector("jdbc/edu", "user", "pass");

        assertThrows(DatabaseConnectionException.class, connector::getConnection);
    }
}
