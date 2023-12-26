package com.globaroman.bookshopproject.controller;

import com.globaroman.bookshopproject.dto.BookDto;
import com.globaroman.bookshopproject.dto.BookSearchParameters;
import com.globaroman.bookshopproject.dto.CreateBookRequestDto;
import com.globaroman.bookshopproject.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "endpoint for bookstore management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all available books by page")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get books by id", description = "Get the book by id")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by parameters",
            description = "Search books by title, author, isbn")
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        return bookService.search(bookSearchParameters);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(hidden = true)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(hidden = true)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(hidden = true)
    public void update(@PathVariable Long id, @RequestBody CreateBookRequestDto bookDto) {
        bookService.update(id, bookDto);
    }
}
