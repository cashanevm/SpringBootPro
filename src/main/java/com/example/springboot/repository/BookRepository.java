package com.example.springboot.repository;

import com.example.springboot.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findByTitle(String title);
}
