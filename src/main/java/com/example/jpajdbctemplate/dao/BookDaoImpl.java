package com.example.jpajdbctemplate.dao;

import com.example.jpajdbctemplate.entities.Book;
import com.example.jpajdbctemplate.mapper.BookMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Book findById(Long id) {
        return jdbcTemplate.queryForObject("select * from book where id = ?", getRowMapper(), id);
    }

    @Override
    public Book findByTitle(String title) {
        return jdbcTemplate.queryForObject("select * from book where title = ?", getRowMapper(), title);
    }

    @Override
    public Book save(Book book) {
        jdbcTemplate.update("insert into book (title, isbn, publisher, author_id) values (?, ?, ?, ?)",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId());
        Long id = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        return findById(id);
    }

    @Override
    public Book update(Book book) {
        jdbcTemplate.update("update book set title = ?, isbn = ?, publisher = ?, author_id = ? where id = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());
        return findById(book.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }

    // because the mapRow code is not thread safe we do this workaround by creating a new instance each time
    private RowMapper<Book> getRowMapper() {
        return new BookMapper();
    }
}
