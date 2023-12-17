package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.BookDto;
import com.globaroman.bookshopproject.dto.BookSearchParameters;
import com.globaroman.bookshopproject.dto.CreateBookRequestDto;
import com.globaroman.bookshopproject.exception.EntityNotFoundException;
import com.globaroman.bookshopproject.mapper.BookMapper;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.specification.book.BookSpecificationBuilder;
import com.globaroman.bookshopproject.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
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
                () -> new EntityNotFoundException("No found book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void update(Long id, CreateBookRequestDto bookDto) {
        Book bookFromDB = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No found book by id: " + id));
        bookMapper.updateBookFromDto(bookFromDB, bookDto);
        bookRepository.save(bookFromDB);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);

        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
