package com.example.jpajdbctemplate.mapper;

import com.example.jpajdbctemplate.entities.Author;
import com.example.jpajdbctemplate.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorMapper implements RowMapper<Author> {

    private static final Logger log = LoggerFactory.getLogger(AuthorMapper.class);

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        try {
            if (rs.getString("isbn") != null) {
                List<Book> books = new ArrayList<>();
                books.add(mapBook(rs));
                author.setBooks(books);
            }

            while (rs.next()) {
                author.getBooks().add(mapBook(rs));
            }
        } catch (SQLException e) {
            log.info("null entries");
        }

        return author;
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("book_id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublisher(rs.getString("publisher"));
        book.setAuthorId(rs.getLong("id"));

        return book;
    }
}
