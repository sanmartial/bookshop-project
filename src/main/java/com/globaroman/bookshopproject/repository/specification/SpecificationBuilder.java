package com.globaroman.bookshopproject.repository.specification;

import com.globaroman.bookshopproject.dto.book.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters searchParameter);
}
