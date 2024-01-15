package com.globaroman.bookshopproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaroman.bookshopproject.dto.book.BookSearchParameters;
import com.globaroman.bookshopproject.dto.book.CreateBookRequestDto;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Category;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new book")
    @Transactional
    @Rollback
    void createBook_ValidCreateRequestBookDto() throws Exception {
        //Given
        CreateBookRequestDto requestDto = createTestCreateBookRequestDtoTemplate();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author").value("Author 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.isbn").value("9-781-34119-5"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(29.99)));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get all books")
    @Transactional
    @Rollback
    void getAll_GivenBookFromDB_ShouldReturnAllBooks() throws Exception {
        //Given
        Book book = createTestBookTemplateWithCategory();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get book by book id")
    @Transactional
    @Rollback
    void getBookById_GetBookByExistId_ShouldReturnExistBookDto() throws Exception {
        //Given
        Book book = createTestBookTemplateWithCategory();

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Existing Book"))
                .andExpect(jsonPath("$.author").value("Existing Author"))
                .andExpect(jsonPath("$.description").value("Existing Description"))
                .andExpect(jsonPath("$.isbn").value("9-781-34119-1"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(19.99)));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get all books which correspond search request")
    @Transactional
    @Rollback
    void search_getAllBooksWhichCorrSearch_ShouldReturnListBookDto() throws Exception {
        //Given
        createTestBookTemplateWithCategory();
        BookSearchParameters bookSearchParameters =
                new BookSearchParameters(new String[]{"Existing Book"}, null, null);

        //When
       mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search")
                        .param("title", bookSearchParameters.title())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete book from Db by bookId")
    @Transactional
    @Rollback
    void delete_DeleteBookById_ShouldDeleteBookFromDb() throws Exception {
        //Given
        Book book = createTestBookTemplateWithCategory();
        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update exist book  by bookId")
    @Transactional
    @Rollback
    void update_WhenValidInput_BookUpdated() throws Exception {
        //Given
        CreateBookRequestDto requestBook = createTestCreateBookRequestDtoTemplate();
        Book book = createTestBookTemplateWithCategory();

        //When
        String requestContent = objectMapper.writeValueAsString(requestBook);
       mockMvc.perform(put("/api/books/{id}", book.getId())
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author").value("Author 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.isbn").value("9-781-34119-5"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(29.99)));
    }

    private Category createTestCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Category Description");
        return categoryRepository.save(category);
    }

    private Book createTestBookTemplateWithCategory() {
        Category category = createTestCategory();
        Book book = new Book();
        book.setTitle("Existing Book");
        book.getCategories().add(category);
        book.setAuthor("Existing Author");
        book.setIsbn("9-781-34119-1");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Existing Description");
        book.setCoverImage("existing_cover.jpg");
        return bookRepository.save(book);
    }

    private CreateBookRequestDto createTestCreateBookRequestDtoTemplate() {
        Category category = createTestCategory();

        CreateBookRequestDto requestBook = CreateBookRequestDto.builder()
                .price(BigDecimal.valueOf(29.99))
                .description("Description 1")
                .title("Book 1")
                .author("Author 1")
                .isbn("9-781-34119-5")
                .coverImage("cover1.jpg")
                .categories(Set.of(category.getId()))
                .build();
        return requestBook;
    }
}
