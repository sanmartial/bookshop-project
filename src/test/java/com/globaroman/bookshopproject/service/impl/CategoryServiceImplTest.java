package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.category.CategoryDto;
import com.globaroman.bookshopproject.dto.category.CreateCategoryRequestDto;
import com.globaroman.bookshopproject.mapper.CategoryMapper;
import com.globaroman.bookshopproject.model.Category;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verifies  the correct return saved Category")
    void save_saveCategory_ShouldReturnCategoryDto() {
        //Given
        CreateCategoryRequestDto requestDto = createTestCreateCategoryRequestDto();
        Category category = new Category();

        Mockito.when(categoryMapper.toModel(requestDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(new CategoryDto());

        //When
        CategoryDto categoryDto = categoryService.save(requestDto);

        //Then
        Assertions.assertNotNull(categoryDto);
        EqualsBuilder.reflectionEquals(requestDto, categoryDto, "id");
    }

    @Test
    @DisplayName("Verifies findAll(pageable) the correct return List<CategoryDto> ")
    void findAll_GetListCategoryDto_ReturnListCategoryDtoTrue() {
        //Given
        Pageable pageable = PageRequest.of(0, 5);
        Page<Category> mockPage =
                new PageImpl<>(Arrays.asList(new Category(), new Category()), pageable, 2);

        Mockito.when(categoryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(mockPage);
        List<CategoryDto> mockCategoryList = Arrays.asList(new CategoryDto(), new CategoryDto());
        Mockito.when(categoryMapper.toDto(Mockito.any(Category.class)))
                .thenReturn(new CategoryDto());

        //When
        List<CategoryDto> result = categoryService.findAll(pageable);

        //Then
        Assertions.assertEquals(mockCategoryList, result);
        Mockito.verify(categoryRepository).findAll(pageable);
        Mockito.verify(categoryMapper, Mockito.times(mockPage.getContent()
                .size())).toDto(Mockito.any(Category.class));
    }

    @Test
    @DisplayName("Verifies the correct return CategoryDto by categoryId")
    void getById_WithValidId_ShouldReturnCategoryDto() {
        //Given
        Long categoryId = 1L;
        Mockito.when(categoryRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Category()));
        Mockito.when(categoryMapper.toDto(Mockito.any())).thenReturn(new CategoryDto());

        //When
        CategoryDto categoryDto = categoryService.getById(categoryId);

        //Then
        Assertions.assertNotNull(categoryDto);
    }

    @Test
    @DisplayName("Update category with valid ID and CategoryDto")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void update_ValidIdAndDto_SuccessfullyUpdated() {
        // Given
        Long categoryId = 1L;
        CreateCategoryRequestDto updateCategoryDto = createTestCreateCategoryRequestDto();

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);

        Mockito.when(categoryRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(existingCategory));
        Mockito.doNothing().when(categoryMapper)
                .updateCategoryFromDto(existingCategory, updateCategoryDto);
        Mockito.when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
        Mockito.when(categoryMapper
                .toDto(Mockito.any(Category.class))).thenReturn(new CategoryDto());

        //When
        CategoryDto update = categoryService.update(existingCategory.getId(), updateCategoryDto);

        //Then
        Assertions.assertNotNull(update);

        Mockito.verify(categoryRepository, Mockito.times(1)).findById(categoryId);
        Mockito.verify(categoryMapper, Mockito.times(1))
                .updateCategoryFromDto(Mockito.any(Category.class), Mockito.eq(updateCategoryDto));
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }

    @Test
    @DisplayName("Update Non-Existing Category and Throws RuntimeException")
    void updateNonExistingCategory_UpdateNonExisting_ThrowsRuntimeException() {
        Long nonExistingCategoryId = 999L;
        CreateCategoryRequestDto updateCategoryDto = createTestCreateCategoryRequestDto();
        Mockito.when(categoryRepository.findById(nonExistingCategoryId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class,
                () -> categoryService.update(nonExistingCategoryId, updateCategoryDto));
    }

    @Test
    @DisplayName("Delete Existing Category by ID")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteById_DeleteExistingCategory_ShouldSuccessDelete() {
        //Given
        Long categoryId = 1L;

        //When
        categoryService.deleteById(categoryId);

        //Then
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(Mockito.eq(categoryId));
    }

    private CreateCategoryRequestDto createTestCreateCategoryRequestDto() {
        return new CreateCategoryRequestDto("Test Category", "Category Description");
    }
}
