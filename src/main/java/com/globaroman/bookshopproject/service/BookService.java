package com.globaroman.bookshopproject.service;

import com.globaroman.bookshopproject.dto.BookDto;
import com.globaroman.bookshopproject.dto.BookSearchParameters;
import com.globaroman.bookshopproject.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    void update(Long id, CreateBookRequestDto bookDto);

    List<BookDto> search(BookSearchParameters bookSearchParameterDto);
}
