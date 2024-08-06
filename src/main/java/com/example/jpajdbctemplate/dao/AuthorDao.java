package com.example.jpajdbctemplate.dao;

import com.example.jpajdbctemplate.entities.Author;

public interface AuthorDao {
    Author findById(Long id);
    Author findByName(String firstName, String lastName);
    Author save(Author author);
    Author update(Author author);
    void delete(Long id);
}
