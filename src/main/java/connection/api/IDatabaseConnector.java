package connection.api;

import java.sql.Connection;

public interface IDatabaseConnector {

    Connection getConnection();
    void closeConnection();
}
