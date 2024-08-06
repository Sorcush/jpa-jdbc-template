package com.example.jpajdbctemplate.repositories;

import com.example.jpajdbctemplate.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
