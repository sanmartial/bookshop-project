package com.globaroman.bookshopproject.service;

import com.globaroman.bookshopproject.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
