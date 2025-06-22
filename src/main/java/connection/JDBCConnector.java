package connection;

import connection.api.IDatabaseConnector;
import exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector implements IDatabaseConnector {

    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public JDBCConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException(exception);
        }

        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException exception) {
            throw new DatabaseConnectionException(exception);
        }
    }
}
