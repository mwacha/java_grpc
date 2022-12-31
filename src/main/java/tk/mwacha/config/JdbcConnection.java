package tk.mwacha.config;

import java.sql.*;

public final class JdbcConnection {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(JavaApplicationProperties.readProperties().getProperty("driver"));

        return DriverManager
                .getConnection(
                        JavaApplicationProperties.readProperties().getProperty("url"),
                        JavaApplicationProperties.readProperties().getProperty("user"),
                        JavaApplicationProperties.readProperties().getProperty("password"));
    }

    public static boolean releaseConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();

        return connection.isClosed();
    }

    public static boolean releasePrepareStatement(PreparedStatement prepareStatement) throws SQLException {
        if (prepareStatement != null)
            prepareStatement.close();

        return prepareStatement.isClosed();
    }

    public static boolean releaseResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null)
            resultSet.close();

        return resultSet.isClosed();
    }
}
