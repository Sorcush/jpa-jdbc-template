package com.example.jpajdbctemplate.repositories;

import com.example.jpajdbctemplate.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
