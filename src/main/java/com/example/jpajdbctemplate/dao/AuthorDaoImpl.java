package com.example.jpajdbctemplate.dao;

import com.example.jpajdbctemplate.entities.Author;
import com.example.jpajdbctemplate.mapper.AuthorMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Author findById(Long id) {
        return jdbcTemplate.queryForObject("select * from author where id = ?", getRowMapper(), id);
    }

    @Override
    public Author findByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("select * from author where first_name = ? and last_name = ?", getRowMapper(), firstName, lastName);
    }

    @Override
    public Author save(Author author) {
        jdbcTemplate.update("insert into author (first_name, last_name) values (?, ?)", author.getFirstName(), author.getLastName());
        Long id = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        return findById(id);
    }

    @Override
    public Author update(Author author) {
        jdbcTemplate.update("update author set first_name = ?, last_name = ? where id = ?", author.getFirstName(), author.getLastName(), author.getId());
        return findById(author.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from author where id = ?", id);
    }

    // because the mapRow code is not thread safe we do this workaround by creating a new instance each time
    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
