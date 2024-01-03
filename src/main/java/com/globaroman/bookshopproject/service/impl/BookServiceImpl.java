package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.book.BookDto;
import com.globaroman.bookshopproject.dto.book.BookDtoWithoutCategoryIds;
import com.globaroman.bookshopproject.dto.book.BookSearchParameters;
import com.globaroman.bookshopproject.dto.book.CreateBookRequestDto;
import com.globaroman.bookshopproject.exception.EntityNotFoundCustomException;
import com.globaroman.bookshopproject.mapper.BookMapper;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Category;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import com.globaroman.bookshopproject.repository.specification.book.BookSpecificationBuilder;
import com.globaroman.bookshopproject.service.BookService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {

        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("No found book by id: " + id)
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
                () -> new EntityNotFoundCustomException("No found book by id: " + id));
        Set<Category> categories = bookDto.getCategories()
                .stream()
                .map(categoryId -> categoryRepository.findById(categoryId).get())
                .collect(Collectors.toSet());
        bookFromDB.setCategories(categories);
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

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return bookRepository.findAllByCategoryId(categoryId, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
