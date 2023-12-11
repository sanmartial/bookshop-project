package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
