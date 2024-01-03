package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.category.CategoryDto;
import com.globaroman.bookshopproject.dto.category.CreateCategoryRequestDto;
import com.globaroman.bookshopproject.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromDto(
            @MappingTarget Category categoryFromDB,
            CreateCategoryRequestDto requestDto);
}
