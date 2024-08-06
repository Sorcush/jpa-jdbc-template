package com.example.jpajdbctemplate.dao;

import com.example.jpajdbctemplate.entities.Author;
import com.example.jpajdbctemplate.entities.Book;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {
    private DataSource dataSource;

    @Override
    public Book findById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);

            return getBook(preparedStatement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Book findByTitle(String title) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE title = ?")
        ) {
            preparedStatement.setString(1, title);

            return getBook(preparedStatement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Book save(Book book) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)")
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setLong(4, book.getAuthorId());
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
    public Book update(Book book) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?")
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setLong(4, book.getAuthorId());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return findById(book.getId());

    }

    @Override
    public void delete(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
    private Book getBook(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorId(resultSet.getLong("author_id"));

                return book;
            }
        }
        return null;
    }
}
