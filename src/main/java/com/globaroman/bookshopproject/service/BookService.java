package com.globaroman.bookshopproject.service;

import com.globaroman.bookshopproject.dto.BookDto;
import com.globaroman.bookshopproject.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getById(Long id);

    void deleteById(Long id);

    void update(Long id, CreateBookRequestDto bookDto);
}
