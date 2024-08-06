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

    @Override
    public Author findById(Long id) {
        return null;
    }

    @Override
    public Author findByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author save(Author author) {
        return null;
    }

    @Override
    public Author update(Author author) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }
}
