package tk.mwacha.repositories;

import tk.mwacha.config.JdbcConnection;
import tk.mwacha.grpc.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryRepository {

    public Category.Builder create(final Category category) throws SQLException, ClassNotFoundException {
        final var sql = "insert into Category (id, name, description) values (?, ?, ?)";
        UUID id = null;

        final Connection conn = JdbcConnection.getConnection();
        var pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);


        try {

            pstmt.setObject(1, category.getId(), Types.OTHER);
            pstmt.setString(2, category.getName());
            pstmt.setString(3, category.getDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getObject("id", java.util.UUID.class);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            JdbcConnection.releaseConnection(conn);
            JdbcConnection.releasePrepareStatement(pstmt);
        }

        return this.findById(id);

    }

    public List<Category.Builder> list() throws SQLException, ClassNotFoundException {
        var sql = "SELECT * FROM Category";
        final List list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                final var category = Category.newBuilder();
                category.setId(resultSet.getString("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));

                list.add(category);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcConnection.releaseConnection(connection);
            JdbcConnection.releasePrepareStatement(preparedStatement);
            JdbcConnection.releaseResultSet(resultSet);
        }

        return list;
    }

    public Category.Builder findById(final UUID id) throws SQLException, ClassNotFoundException {
        var sql = "SELECT * FROM Category where id = ?::uuid";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, String.valueOf(id));

            resultSet = preparedStatement.executeQuery();

            final var category = Category.newBuilder();

            if (resultSet.next()) {
                category.setId(resultSet.getString("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));

            }
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JdbcConnection.releaseConnection(connection);
            JdbcConnection.releasePrepareStatement(preparedStatement);
            JdbcConnection.releaseResultSet(resultSet);
        }
    }

    public int deleteById(final UUID id) throws SQLException, ClassNotFoundException {
        var sql = "DELETE FROM Category where id = ?::uuid";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(id));

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcConnection.releaseConnection(connection);
            JdbcConnection.releasePrepareStatement(preparedStatement);
        }
    }
}
