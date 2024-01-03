package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.category.CategoryDto;
import com.globaroman.bookshopproject.dto.category.CreateCategoryRequestDto;
import com.globaroman.bookshopproject.exception.EntityNotFoundCustomException;
import com.globaroman.bookshopproject.mapper.CategoryMapper;
import com.globaroman.bookshopproject.model.Category;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import com.globaroman.bookshopproject.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toModel(categoryDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("No found book by id: " + id)
        );
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category categoryFromDB = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("No found book by id: " + id)
        );
        categoryMapper.updateCategoryFromDto(categoryFromDB, requestDto);
        return categoryMapper.toDto(categoryRepository.save(categoryFromDB));

    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
