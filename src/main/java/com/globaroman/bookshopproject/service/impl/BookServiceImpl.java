package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.BookDto;
import com.globaroman.bookshopproject.dto.CreateBookRequestDto;
import com.globaroman.bookshopproject.exception.EntityNotFoundException;
import com.globaroman.bookshopproject.mapper.BookMapper;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        System.out.println(book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {

        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () ->
                        new EntityNotFoundException("No found book by id: " + id)
        );
        return bookMapper.toDto(book);
    }
}
