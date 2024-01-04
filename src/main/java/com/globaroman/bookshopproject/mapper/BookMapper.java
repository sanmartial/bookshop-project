package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.book.BookDto;
import com.globaroman.bookshopproject.dto.book.BookDtoWithoutCategoryIds;
import com.globaroman.bookshopproject.dto.book.CreateBookRequestDto;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateBookFromDto(
            @MappingTarget Book book,
            CreateBookRequestDto bookRequestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategories(
            CreateBookRequestDto requestDto,
            @MappingTarget Book book) {
        Set<Category> categories = requestDto
                .getCategories().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    @AfterMapping
    default void setCategoryId(
            @MappingTarget BookDto bookDto,
            Book book) {
        List<Long> categoriyIds = book.getCategories()
                .stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categoriyIds);
    }

    @Named("bookFromId")
    default Long bookFromId(Book book) {
        return Optional.ofNullable(book)
                .map(Book::getId)
                .orElse(null);
    }

    @Named("getTitleFromBook")
    default String getTitleFromBook(Book book) {
        return book != null ? book.getTitle() : null;
    }
}
