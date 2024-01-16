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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verifies  the correct return BookDto")
    void getById_WithValidBookId_ShouldReturnBookDto() {
        //Given
        Long bookId = 1L;
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book()));
        Mockito.when(bookMapper.toDto(Mockito.any())).thenReturn(new BookDto());

        //When
        BookDto bookDto = bookService.getById(Mockito.anyLong());

        //Then
        Assertions.assertNotNull(bookDto);
    }

    @Test
    @DisplayName("Verifies  the correct return EntityNotFoundException when bookId not exist")
    void getById_WithNotExistBookId_ShouldReturnEntityNotFoundException() {
        //Given
        Long bookId = 100L;
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        //When
        Exception exception = Assertions.assertThrows(EntityNotFoundCustomException.class,
                () -> bookService.getById(bookId));

        //Then
        String expected = "No found book by id: " + bookId;
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verifies  the correct return ")
    void save_SaveBook_ReturnBookDtoAndRightFields() {
        //Given
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title("Title A")
                .price(new BigDecimal(15.05)).build();
        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setPrice(requestDto.getPrice());
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(book.getPrice());

        Mockito.when(bookMapper.toModel(requestDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        //When
        BookDto bookDtoExp = bookService.save(requestDto);
        String expTitle = bookDtoExp.getTitle();
        BigDecimal expPrice = bookDtoExp.getPrice();

        //Then
        Assertions.assertNotNull(bookDtoExp);
        Assertions.assertEquals(expPrice, requestDto.getPrice());
        Assertions.assertEquals(expTitle, requestDto.getTitle());
    }

    @Test
    @DisplayName("Verifies findAll(pageable) the correct return List<BookDto> ")
    void findAll_GetListBookDto_ReturnListBookDtoTrue() {
        //Given
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> mockPage = new PageImpl<>(Arrays.asList(new Book(), new Book()), pageable, 2);

        Mockito.when(bookRepository.findAll(pageable)).thenReturn(mockPage);
        List<BookDto> mockBookDtoList = Arrays.asList(new BookDto(), new BookDto());
        Mockito.when(bookMapper.toDto(Mockito.any(Book.class))).thenReturn(new BookDto());

        //When
        List<BookDto> result = bookService.findAll(pageable);

        //Then
        Assertions.assertEquals(mockBookDtoList, result);
        Mockito.verify(bookRepository).findAll(pageable);
        Mockito.verify(bookMapper, Mockito
                .times(mockPage.getContent().size())).toDto(Mockito.any(Book.class));
    }

    @Test
    @DisplayName("Verifies findAll(pageable) the correct return List<BookDto> ")
    void findAll_GetListBookDto_ReturnEmptyList() {
        //Given
        Pageable pageable = Pageable.unpaged();
        Mockito.lenient().when(bookRepository.findAll(pageable)).thenReturn(Page.empty());
        Mockito.lenient().when(bookMapper.toDto(Mockito.any())).thenReturn(new BookDto());

        //When
        List<BookDto> result = bookService.findAll(pageable);

        //Then
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Verifies deleteById method is called with correct argument")
    void deleteById_ExistingBookId_DeleteOk() {
        //Given
        Long bookId = 1L;

        //When
        bookService.deleteById(bookId);

        //Then
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Update book with valid ID and bookDto")
    void updateBook_ValidIdAndDto_SuccessfullyUpdated() {
        // Given
        Long bookId = 1L;
        Set<Long> categoryIds = Set.of(1L, 2L);
        CreateBookRequestDto requestDto = createBookDtoWithCategories(categoryIds);

        Book existingBook = new Book();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        Set<Category> categoriesFromDb = categoryIds.stream()
                .map(categoryId -> {
                    Category category = new Category();
                    category.setId(categoryId);
                    return category;
                })
                .collect(Collectors.toSet());
        Mockito.when(categoryRepository.findById(Mockito.any())).thenAnswer(invocation -> {
            Long categoryId = invocation.getArgument(0);
            return Optional.ofNullable(categoriesFromDb.stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst()
                    .orElse(null));
        });

        // When
        bookService.update(bookId, requestDto);

        // Then
        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
        Mockito.verify(categoryRepository,
                Mockito.times(categoryIds.size())).findById(Mockito.any());
        Mockito.verify(bookRepository, Mockito.times(1)).save(existingBook);
    }

    private CreateBookRequestDto createBookDtoWithCategories(Set<Long> categoryIds) {
        CreateBookRequestDto bookDto = CreateBookRequestDto.builder().build();
        bookDto.setCategories(categoryIds);
        return bookDto;
    }

    @Test
    @DisplayName("Verifies update() when bookId isn't "
            + "existing book should return EntityNotFoundException")
    void updateBook_NonExistingBookId_ReturnEntityNotFoundCustomException() {
        // Given
        Long nonExistingBookId = 999L;
        CreateBookRequestDto updateBookDto = CreateBookRequestDto.builder().build();

        // When
        Mockito.when(bookRepository.findById(nonExistingBookId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundCustomException.class,
                () -> bookService.update(nonExistingBookId, updateBookDto));
    }

    @Test
    @DisplayName("Search for books with valid search parameters")
    void searchBooks_ValidSearchParameters_ReturnsListOfBookDto() {
        // Given
        List<Book> books = new ArrayList<>();
        BookSearchParameters searchParameters = new BookSearchParameters(
                new String[]{"Title"}, null, null
        );
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        Mockito.lenient().when(bookRepository
                .findAll(Mockito.eq(bookSpecification))).thenReturn(books);
        Mockito.lenient().when(bookMapper.toDto(Mockito.any())).thenReturn(new BookDto());

        // When
        List<BookDto> result = bookService.search(searchParameters);

        // Then
        Mockito.verify(bookRepository, Mockito.times(1)).findAll(Mockito.eq(bookSpecification));
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Verifies findAllByCategoryId that should return List<BookDtoWithoutCategoryIds>")
    void findAllByCategoryId_LongAndPageable_ReturnsPageOfBookWithoutCategoryId() {
        //Given
        Long categoryId = 1L;
        Pageable pageable = Pageable.unpaged();
        List<Book> booksPage = Collections.emptyList();

        //When
        Mockito.lenient().when(bookRepository.findAllByCategoryId(categoryId, pageable))
                .thenReturn(booksPage);
        Mockito.lenient().when(bookMapper.toDtoWithoutCategories(Mockito.any()))
                .thenReturn(new BookDtoWithoutCategoryIds());

        //Then
        List<BookDtoWithoutCategoryIds> result =
                bookService.findAllByCategoryId(categoryId, pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(bookRepository, Mockito.times(1)).findAllByCategoryId(categoryId, pageable);
        Mockito.verify(bookMapper, Mockito.times(0)).toDto(Mockito.any());
    }
}
