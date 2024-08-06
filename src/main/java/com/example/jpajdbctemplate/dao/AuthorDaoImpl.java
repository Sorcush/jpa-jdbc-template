package com.example.jpajdbctemplate.dao;

import com.example.jpajdbctemplate.entities.Author;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
@AllArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {
    private final DataSource dataSource;

    @Override
    public Author findById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);

            return getAuthor(preparedStatement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Author findByName(String firstName, String lastName) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?")
        ) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            return getAuthor(preparedStatement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Author save(Author author) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, last_name) VALUES (?, ?)")
        ) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    return findById(id);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Author update(Author author) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?")
        ) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return findById(author.getId());
    }

    @Override
    public void delete(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM author WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private Author getAuthor(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));

                return author;
            }
        }
        return null;
    }
}
