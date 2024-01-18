package com.globaroman.bookshopproject.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.globaroman.bookshopproject.dto.category.CreateCategoryRequestDto;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Category;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new category")
    @Transactional
    @Rollback
    void createCategory_ValidCreateCategoryRequestDto_ShouldReturnCategoryDto() throws Exception {
        CreateCategoryRequestDto requestDto = createTestCreateCategoryRequestDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Fiction"))
                .andExpect(jsonPath("$.description").value("Fictional Books"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete category from Db by categoryId")
    @Transactional
    @Rollback
    void deleteCategory_DeleteCategoryByID_shouldDeleteCategoryFromDB() throws Exception {
        Category category = createTestCategory();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update category from Db by categoryId")
    @Transactional
    @Rollback
    void updateCategory_WhenValidInput_ShouldUpdateExistCategory() throws Exception {
        CreateCategoryRequestDto requestDto = createTestCreateCategoryRequestDto();
        Category category = createTestCategory();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/categories/{id}", category.getId())
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Fiction"))
                .andExpect(jsonPath("$.description").value("Fictional Books"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get all categories")
    void getAll_GivenAllCategoriesFromDB_ShouldReturnAllCategories() throws Exception {
        MvcResult resultActions = mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get categories by id")
    @Transactional
    @Rollback
    void getCategoryById_GetCategoryByExistId_ShouldReturnExistCategoryDto() throws Exception {
        Category category = createTestCategory();

        mockMvc.perform(get("/api/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Category"))
                .andExpect(jsonPath("$.description").value("Category Description"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get all books by category id")
    @Transactional
    @Rollback
    void getBooksByCategoryId() throws Exception {
        Category category = createTestCategory();
        createTestBookTemplateWithCategory(category);

        mockMvc.perform(get("/api/categories/{id}/books", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    private Category createTestCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Category Description");
        return categoryRepository.save(category);
    }

    private CreateCategoryRequestDto createTestCreateCategoryRequestDto() {
        return new CreateCategoryRequestDto("Fiction", "Fictional Books");
    }

    private Book createTestBookTemplateWithCategory(Category category) {

        Book book = new Book();
        book.setTitle("Existing Book");
        book.setAuthor("Existing Author");
        book.setIsbn("9-781-34119-1");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Existing Description");
        book.setCoverImage("existing_cover.jpg");
        book.getCategories().add(category);
        return bookRepository.save(book);
    }
}
