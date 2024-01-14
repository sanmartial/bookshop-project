package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Category;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("Test Category");
        category.setDescription("Category Description");
        categoryRepository.save(category);

        Book newBook = new Book();
        newBook.setTitle("Book 1");
        newBook.setAuthor("Author 1");
        newBook.setIsbn("9-781-34119-2");
        newBook.setPrice(BigDecimal.valueOf(29.99));
        newBook.setDescription("Description 1");
        newBook.setCoverImage("cover1.jpg");
        newBook.getCategories().add(category);
        bookRepository.save(newBook);
    }

    @Test
    @DisplayName("Find all book by categoryId")
    @Sql(scripts = "classpath:database/scripts/remove-books-from-fb-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_WithExistsCategory_ReturnListBooks() {
        Long categoryId = category.getId();
        Pageable pageable = Pageable.unpaged();

        List<Book> books = bookRepository.findAllByCategoryId(categoryId, pageable);

        int expected = 1;
        int actual = books.size();
        Assertions.assertEquals(expected, actual);
    }
}
