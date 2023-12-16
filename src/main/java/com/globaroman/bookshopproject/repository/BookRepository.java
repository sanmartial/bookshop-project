package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
