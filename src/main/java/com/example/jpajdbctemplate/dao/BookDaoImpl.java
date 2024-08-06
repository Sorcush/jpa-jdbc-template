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
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        return null;
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

}
